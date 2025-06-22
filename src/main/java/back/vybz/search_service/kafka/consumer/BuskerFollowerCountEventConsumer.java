package back.vybz.search_service.kafka.consumer;

import back.vybz.search_service.busker.domain.BuskerSearchDocument;
import back.vybz.search_service.kafka.event.BuskerFollowerCountEvent;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BuskerFollowerCountEventConsumer {

    private final ElasticsearchClient elasticsearchClient;
    private static final String INDEX_NAME = "create-busker-search";
    private static final String TOPIC = "busker-follower-count";

    @KafkaListener(
            topics = TOPIC,
            groupId = "search-service-group",
            containerFactory = "buskerFollowerCountKafkaListenerContainerFactory"
    )
    public void consumeBuskerFollowerCountEvent(BuskerFollowerCountEvent event) {
        log.info("🔥 Kafka 버스커 팔로워 수 이벤트 수신: {}", event);

        try {
            UpdateRequest<BuskerSearchDocument, BuskerSearchDocument> request = UpdateRequest.of(u -> u
                    .index(INDEX_NAME)
                    .id(event.getBuskerUuid())
                    .doc(BuskerSearchDocument.builder()
                            .followerCount(event.getFollowerCount())
                            .build())
                    .docAsUpsert(false) // insert 금지
            );

            UpdateResponse<BuskerSearchDocument> response = elasticsearchClient.update(request, BuskerSearchDocument.class);
            log.info("✅ ES 업데이트 완료: {}", response.result());

        } catch (ElasticsearchException e) {
            if (Integer.valueOf(404).equals(e.status())) {
                log.warn("⚠️ ES 업데이트 실패: 해당 buskerUuid 없음 → skip (uuid={})", event.getBuskerUuid());
            } else {
                log.error("❌ ES 업데이트 실패", e);
            }
        } catch (Exception e) {
            log.error("❌ ES 업데이트 처리 중 예외 발생", e);
        }
    }
}
