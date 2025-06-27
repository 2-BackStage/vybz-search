package back.vybz.search_service.feed.application.service;

import back.vybz.search_service.feed.domain.ReelsDocument;
import back.vybz.search_service.feed.dto.request.RequestSearchHashTagDto;
import back.vybz.search_service.feed.dto.response.ResponseSearchHashTagDto;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagSearchServiceImpl implements HashTagSearchService {

    private final ElasticsearchClient elasticsearchClient;
    private static final String INDEX_NAME = "create-reels-search";
    private static final int MAX_RESULTS = 60;

    @Override
    public List<ResponseSearchHashTagDto> searchHashTags(RequestSearchHashTagDto request) throws IOException {
        String keyword = request.getKeyword();

        // ✅ "keyword*" 형태로 검색 (예: "부산*" → #부산, #부산맛집 등)
        Query query = Query.of(q -> q
                .wildcard(w -> w
                        .field("hashTag.keyword")
                        .value(keyword + "*")
                )
        );

        SearchResponse<ReelsDocument> response = elasticsearchClient.search(s -> s
                        .index(INDEX_NAME)
                        .query(query)
                        .size(500), // 문서 최대 500개 뒤짐
                ReelsDocument.class
        );

        // ✅ 추출된 해시태그 목록 → 필터 + 중복 제거 + 정렬 + 제한
        Set<String> distinctTags = response.hits().hits().stream()
                .flatMap(hit -> Optional.ofNullable(hit.source())
                        .map(ReelsDocument::getHashTag)
                        .orElse(List.of()).stream())
                .filter(tag -> tag != null && tag.contains(keyword))
                .distinct()
                .limit(MAX_RESULTS)
                .collect(Collectors.toCollection(LinkedHashSet::new)); // 순서 유지

        return distinctTags.stream()
                .map(tag -> ResponseSearchHashTagDto.builder().tagName(tag).build())
                .collect(Collectors.toList());
    }
}
