package back.vybz.search_service.feed.application.service;

import back.vybz.search_service.feed.domain.ReelsDocument;
import back.vybz.search_service.feed.dto.request.RequestScrollSearchReelsDto;
import back.vybz.search_service.feed.dto.response.ResponseScrollSearchReelsDto;
import back.vybz.search_service.common.util.CursorPage;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReelsSearchServiceImpl implements ReelsSearchService {

    private final ElasticsearchClient elasticsearchClient;
    private static final String INDEX_NAME = "create-reels-search";
    private static final int PAGE_DEFAULT_SIZE = 12;

    @Override
    public CursorPage<ResponseScrollSearchReelsDto> searchReels(RequestScrollSearchReelsDto request) throws IOException {
        String keyword = request.getKeyword();
        int pageSize = request.getSize() != null ? request.getSize() : PAGE_DEFAULT_SIZE;

        log.info("🔍 [릴스 검색 요청] keyword='{}', size={}, cursorId='{}', cursorCreatedAt={}",
                keyword, pageSize, request.getCursorId(), request.getCursorCreatedAt());

        if (keyword == null || keyword.isBlank()) {
            log.warn("🚫 keyword가 null이거나 비어있음. 검색 수행 안 함.");
            return CursorPage.<ResponseScrollSearchReelsDto>builder()
                    .content(List.of())
                    .hasNext(false)
                    .build();
        }

        // 검색 쿼리 정의
        Query query = Query.of(q -> q
                .bool(b -> b
                        .should(List.of(
                                Query.of(m -> m.match(t -> t.field("content").query(keyword))),
                                Query.of(m -> m.match(t -> t.field("hashTag").query(keyword)))
                        ))
                        .minimumShouldMatch("1")
                )
        );

        // 검색 요청 정의
        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(INDEX_NAME)
                .query(query)
                .size(pageSize + 1)
                .sort(s -> s.field(f -> f.field("createdAt").order(SortOrder.Desc)))
                .sort(s -> s.field(f -> f.field("id.keyword").order(SortOrder.Desc))); // ✅ 수정됨

        // 커서 조건이 있는 경우 search_after 설정
        if (request.getCursorCreatedAt() != null && request.getCursorId() != null && !request.getCursorId().isBlank()) {
            searchRequestBuilder.searchAfter(List.of(
                    FieldValue.of(request.getCursorCreatedAt()),
                    FieldValue.of(request.getCursorId())
            ));
            log.info("▶ search_after 적용됨: createdAt={}, id={}", request.getCursorCreatedAt(), request.getCursorId());
        }

        // 검색 실행
        List<Hit<ReelsDocument>> hits = elasticsearchClient.search(
                searchRequestBuilder.build(),
                ReelsDocument.class
        ).hits().hits();

        log.info("✅ Elasticsearch 검색 완료: 총 {}개 hit", hits.size());

        boolean hasNext = hits.size() > pageSize;

        String nextCursorId = null;
        Long nextCursorCreatedAt = null;

        if (hasNext) {
            ReelsDocument lastDocOnPage = hits.get(pageSize - 1).source();
            nextCursorId = lastDocOnPage.getId();
            nextCursorCreatedAt = lastDocOnPage.getCreatedAt();
            log.info("▶ 다음 커서 정보: nextCursorId={}, nextCursorCreatedAt={}", nextCursorId, nextCursorCreatedAt);
        }

        List<ResponseScrollSearchReelsDto> content = hits.stream()
                .limit(pageSize)
                .map(hit -> {
                    ReelsDocument doc = hit.source();
                    return ResponseScrollSearchReelsDto.builder()
                            .id(doc.getId())
                            .writerUuid(doc.getWriterUuid())
                            .content(doc.getContent())
                            .hashTag(doc.getHashTag())
                            .thumbnailUrl(doc.getThumbnailUrl())
                            .createdAt(doc.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

        log.info("📦 반환할 결과 개수: {}", content.size());

        return CursorPage.<ResponseScrollSearchReelsDto>builder()
                .content(content)
                .hasNext(hasNext)
                .nextCursorId(nextCursorId)
                .nextCursorCreatedAt(nextCursorCreatedAt)
                .build();
    }
}