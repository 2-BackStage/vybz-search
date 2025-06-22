package back.vybz.search_service.busker.application.service;

import back.vybz.search_service.busker.domain.BuskerSearchDocument;
import back.vybz.search_service.busker.dto.request.RequestScrollSearchBuskerDto;
import back.vybz.search_service.busker.dto.response.ResponseScrollSearchBuskerDto;
import back.vybz.search_service.common.util.CursorPage;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuskerSearchServiceImpl implements BuskerSearchService {

    private final ElasticsearchClient elasticsearchClient;
    private static final String INDEX_NAME = "create-busker-search";  // ⭐️ ES 인덱스명
    private final int PAGE_DEFAULT_SIZE = 10;

    @Override
    public CursorPage<ResponseScrollSearchBuskerDto> searchBuskers(RequestScrollSearchBuskerDto requestScrollSearchBuskerDto) throws IOException {

        // ✅ Query 생성
        Query query = Query.of(q -> q
                .bool(b -> {
                    BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

                    if (requestScrollSearchBuskerDto.getKeyword() != null && !requestScrollSearchBuskerDto.getKeyword().isEmpty()) {
                        boolQueryBuilder.should(f -> f
                                .match(t -> t
                                        .field("nickname")
                                        .query(requestScrollSearchBuskerDto.getKeyword())
                                )
                        );
                        boolQueryBuilder.minimumShouldMatch("1");
                    }
                    return boolQueryBuilder;
                })
        );

        // ✅ Page size
        int pageSize = requestScrollSearchBuskerDto.getSize() != null ? requestScrollSearchBuskerDto.getSize() : PAGE_DEFAULT_SIZE;

        // ✅ SearchRequest 생성
        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(INDEX_NAME)
                .query(query)
                .size(pageSize + 1) // 커서 페이징
                .sort(s -> s.field(f -> f.field("followerCount").order(SortOrder.Desc)))  // ⭐️ followerCount 정렬
                .sort(s -> s.field(f -> f.field("buskerUuid").order(SortOrder.Desc)));    // ⭐️ 부가 정렬 (cursor 안정성)

        // ✅ search_after 적용
        if (requestScrollSearchBuskerDto.getCursorFollowerCount() != null && requestScrollSearchBuskerDto.getCursorBuskerUuid() != null) {
            searchRequestBuilder.searchAfter(List.of(
                    FieldValue.of(requestScrollSearchBuskerDto.getCursorFollowerCount()),
                    FieldValue.of(requestScrollSearchBuskerDto.getCursorBuskerUuid())
            ));
        }

        // ✅ Elasticsearch 검색 실행
        List<Hit<BuskerSearchDocument>> hits = elasticsearchClient.search(
                searchRequestBuilder.build(),
                BuskerSearchDocument.class
        ).hits().hits();

        // ✅ hasNext 판별
        boolean hasNext = hits.size() > pageSize;

        // ✅ nextCursor 준비
        Integer cursorFollowerCount = null;
        String cursorBuskerUuid = null;
        if (hasNext) {
            Hit<BuskerSearchDocument> lastHit = hits.get(pageSize - 1);
            BuskerSearchDocument doc = lastHit.source();
            cursorFollowerCount = doc.getFollowerCount();
            cursorBuskerUuid = doc.getBuskerUuid();
        }

        // ✅ 결과 변환
        List<ResponseScrollSearchBuskerDto> content = hits.stream()
                .limit(pageSize)
                .map(hit -> {
                    BuskerSearchDocument doc = hit.source();
                    return ResponseScrollSearchBuskerDto.builder()
                            .buskerUuid(doc.getBuskerUuid())
                            .nickname(doc.getNickname())
                            .profileImageUrl(doc.getProfileImageUrl())
                            .followerCount(doc.getFollowerCount())
                            .build();
                })
                .collect(Collectors.toList());

        // ✅ 결과 반환
        return CursorPage.<ResponseScrollSearchBuskerDto>builder()
                .content(content)
                .hasNext(hasNext)
                .nextCursorFollowerCount(cursorFollowerCount)
                .nextCursorBuskerUuid(cursorBuskerUuid)
                .build();
    }
}
