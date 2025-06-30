package back.vybz.search_service.busker.application.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class BuskerIndexInitializer implements ApplicationRunner {

    private final ElasticsearchClient elasticsearchClient;
    private static final String BUSKER_INDEX_NAME = "create-busker-search";

    /**
     * 이 run 메소드는 Spring Boot 애플리케이션의 모든 준비가 끝난 후,
     * 가장 마지막에 딱 한 번 안전하게 호출되는 것을 보장합니다.
     * @PostConstruct의 경쟁 상태(Race Condition) 문제를 해결합니다.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("✅ [ApplicationRunner] 인덱스 초기화 로직을 시작합니다...");
        try {
            createBuskerSearchIndex();
        } catch (Exception e) {
            log.error("❌ 인덱스 초기화 중 심각한 에러가 발생했습니다.", e);
        }
    }

    public void createBuskerSearchIndex() throws IOException {
        // 인덱스가 이미 존재하는지 확인합니다.
        boolean exists = elasticsearchClient.indices().exists(e -> e.index(BUSKER_INDEX_NAME)).value();
        if (exists) {
            log.info("[ES] Index '{}' already exists. Skipping creation.", BUSKER_INDEX_NAME);
            return;
        }

        log.info("[ES] Index '{}' does not exist. Starting index creation process...", BUSKER_INDEX_NAME);

        // 분석기(Analyzer) 및 토크나이저(Tokenizer) 설정
        JsonObject analysisJson = Json.createObjectBuilder()
                .add("analyzer", Json.createObjectBuilder()
                        .add("edge_ngram_analyzer", Json.createObjectBuilder()
                                .add("type", "custom")
                                .add("tokenizer", "custom_edge_ngram_tokenizer")
                                .add("filter", Json.createArrayBuilder()
                                        .add("lowercase")
                                )
                        )
                        .add("nori_analyzer", Json.createObjectBuilder()
                                .add("type", "custom")
                                .add("tokenizer", "nori_tokenizer")
                                .add("filter", Json.createArrayBuilder()
                                        .add("lowercase")
                                )
                        )
                )
                .add("tokenizer", Json.createObjectBuilder()
                        .add("custom_edge_ngram_tokenizer", Json.createObjectBuilder()
                                .add("type", "edge_ngram")
                                .add("min_gram", 1)
                                .add("max_gram", 20)
                                .add("token_chars", Json.createArrayBuilder()
                                        .add("letter")  // 한글 + 영어 둘 다 포함
                                        .add("digit")
                                )
                        )
                )
                .build();

        // 인덱스 세팅 생성
        String settingsJson = Json.createObjectBuilder()
                .add("number_of_shards", 1)
                .add("number_of_replicas", 1)
                .add("refresh_interval", "1s")
                .add("max_result_window", 10000)
                .add("analysis", analysisJson)
                .build()
                .toString();

        InputStream settingsStream = new ByteArrayInputStream(settingsJson.getBytes(StandardCharsets.UTF_8));

        // 인덱스 생성 요청
        elasticsearchClient.indices().create(c -> c
                .index(BUSKER_INDEX_NAME)
                .settings(s -> s.withJson(settingsStream))
                .mappings(TypeMapping.of(m -> m
                        .properties("buskerUuid", p -> p.keyword(k -> k))
                        .properties("nickname", p -> p.text(t -> t
                                .analyzer("nori_analyzer")
                                .fields("autocomplete", f -> f.text(tt -> tt
                                        .analyzer("edge_ngram_analyzer")
                                        .searchAnalyzer("standard")
                                ))
                                .fields("keyword", f -> f.keyword(k -> k.ignoreAbove(256)))
                        ))
                        .properties("nicknameChosung", p -> p.keyword(k -> k))
                        .properties("profileImageUrl", p -> p.keyword(k -> k))
                        .properties("followerCount", p -> p.integer(i -> i))
                ))
        );

        log.info("✅ [ES] Successfully created index: '{}'", BUSKER_INDEX_NAME);
    }
}
