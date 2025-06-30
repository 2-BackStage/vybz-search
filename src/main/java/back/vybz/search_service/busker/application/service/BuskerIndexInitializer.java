package back.vybz.search_service.busker.application.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import jakarta.annotation.PostConstruct;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuskerIndexInitializer {

    private final ElasticsearchClient elasticsearchClient;
    private static final String BUSKER_INDEX_NAME = "create-busker-search";

    @PostConstruct
    public void init() throws IOException {
        createBuskerSearchIndex();
    }

    public void createBuskerSearchIndex() throws IOException {
        boolean exists = elasticsearchClient.indices().exists(e -> e.index(BUSKER_INDEX_NAME)).value();
        if (exists) {
            log.info("[ES] Index '{}' already exists. Skipping creation.", BUSKER_INDEX_NAME);
            return;
        }

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
                                        .add("letter")  // ✅ 한글 + 영어 둘 다 포함됨!
                                        .add("digit")
                                )
                        )
                )
                .build();

        String settingsJson = Json.createObjectBuilder()
                .add("number_of_shards", 1)
                .add("number_of_replicas", 1)
                .add("refresh_interval", "1s")
                .add("max_result_window", 10000)
                .add("analysis", analysisJson)
                .build()
                .toString();

        InputStream settingsStream = new ByteArrayInputStream(settingsJson.getBytes(StandardCharsets.UTF_8));

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

        log.info("[ES] Created Index: '{}'", BUSKER_INDEX_NAME);
    }
}

