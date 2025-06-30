package back.vybz.search_service.busker.application.service;

import back.vybz.search_service.busker.domain.BuskerSearchDocument;
import back.vybz.search_service.busker.dto.request.RequestScrollSearchBuskerDto;
import back.vybz.search_service.busker.dto.response.ResponseScrollSearchBuskerDto;
import back.vybz.search_service.common.util.ChosungUtils;
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
public class BuskerSearchServiceImpl implements BuskerSearchService {

    private final ElasticsearchClient elasticsearchClient;
    private static final String INDEX_NAME = "create-busker-search";
    private final int PAGE_DEFAULT_SIZE = 10;

    @Override
    public CursorPage<ResponseScrollSearchBuskerDto> searchBuskers(RequestScrollSearchBuskerDto dto) throws IOException {
        String keyword = dto.getKeyword();
        log.debug("🔍 [검색 요청] keyword='{}'", keyword);

        Query query;
        if (keyword != null && !keyword.isBlank()) {
            boolean isChosung = ChosungUtils.isChosung(keyword);
            log.debug("🔠 초성 여부 = {}", isChosung);

            if (isChosung) {
                query = Query.of(q -> q.term(t -> t.field("nicknameChosung").value(keyword)));
                log.debug("📘 Query: TERM 검색 on nicknameChosung = {}", keyword);
            } else {
                query = Query.of(q -> q.match(m -> m.field("nickname.autocomplete").query(keyword)));
                log.debug("📗 Query: MATCH 검색 on nickname.autocomplete = {}", keyword);
            }
        } else {
            query = Query.of(q -> q.matchNone(m -> m));
            log.debug("⚠️ keyword가 없어서 matchNone 실행됨");
        }

        int pageSize = dto.getSize() != null ? dto.getSize() : PAGE_DEFAULT_SIZE;

        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                .index(INDEX_NAME)
                .query(query)
                .size(pageSize + 1)
                .sort(s -> s.field(f -> f.field("followerCount").order(SortOrder.Desc)))
                .sort(s -> s.field(f -> f.field("buskerUuid").order(SortOrder.Desc)));

        if (dto.getCursorFollowerCount() != null &&
                dto.getCursorBuskerUuid() != null &&
                !dto.getCursorBuskerUuid().isBlank() &&
                !"string".equalsIgnoreCase(dto.getCursorBuskerUuid())) {

            searchRequestBuilder.searchAfter(List.of(
                    FieldValue.of(dto.getCursorFollowerCount()),
                    FieldValue.of(dto.getCursorBuskerUuid())
            ));
            log.debug("➡️ search_after 적용: followerCount = {}, buskerUuid = {}",
                    dto.getCursorFollowerCount(), dto.getCursorBuskerUuid());
        } else {
            log.debug("⏭️ search_after 미적용: 첫 페이지 요청 또는 잘못된 커서");
        }

        List<Hit<BuskerSearchDocument>> hits = elasticsearchClient.search(
                searchRequestBuilder.build(),
                BuskerSearchDocument.class
        ).hits().hits();

        boolean hasNext = hits.size() > pageSize;

        Integer cursorFollowerCount = null;
        String cursorBuskerUuid = null;
        if (hasNext) {
            Hit<BuskerSearchDocument> lastHit = hits.get(pageSize - 1);
            BuskerSearchDocument doc = lastHit.source();
            cursorFollowerCount = doc.getFollowerCount();
            cursorBuskerUuid = doc.getBuskerUuid();
            log.debug("📍 nextCursor = [followerCount: {}, buskerUuid: {}]",
                    cursorFollowerCount, cursorBuskerUuid);
        }

        List<ResponseScrollSearchBuskerDto> content = hits.stream()
                .limit(pageSize)
                .map(hit -> {
                    BuskerSearchDocument doc = hit.source();
                    log.debug("➡️ 결과 항목: nickname='{}', followerCount={}",
                            doc.getNickname(), doc.getFollowerCount());
                    return ResponseScrollSearchBuskerDto.builder()
                            .buskerUuid(doc.getBuskerUuid())
                            .nickname(doc.getNickname())
                            .profileImageUrl(doc.getProfileImageUrl())
                            .followerCount(doc.getFollowerCount())
                            .build();
                })
                .collect(Collectors.toList());

        return CursorPage.<ResponseScrollSearchBuskerDto>builder()
                .content(content)
                .hasNext(hasNext)
                .nextCursorFollowerCount(cursorFollowerCount)
                .nextCursorBuskerUuid(cursorBuskerUuid)
                .build();
    }
}
