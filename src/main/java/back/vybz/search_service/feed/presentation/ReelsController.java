package back.vybz.search_service.feed.presentation;

import back.vybz.search_service.common.entity.BaseResponseEntity;
import back.vybz.search_service.common.util.CursorPage;
import back.vybz.search_service.feed.application.service.ReelsSearchService;
import back.vybz.search_service.feed.dto.request.RequestScrollSearchReelsDto;
import back.vybz.search_service.feed.dto.response.ResponseScrollSearchReelsDto;
import back.vybz.search_service.feed.vo.request.RequestScrollSearchReelsVo;
import back.vybz.search_service.feed.vo.response.ResponseScrollSearchReelsVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class ReelsController {

    private final ReelsSearchService reelsSearchService;

    @Operation(
            summary = "릴스 검색 API (무한스크롤)",
            description = "내용 또는 해시태그 기반 검색, 최신순 정렬, 무한스크롤 지원",
            tags = {"FEED-SEARCH-SERVICE"}
    )
    @GetMapping("/reels")
    public BaseResponseEntity<CursorPage<ResponseScrollSearchReelsVo>> searchReels(
            @ModelAttribute RequestScrollSearchReelsVo requestScrollSearchReelsVo
    ) throws IOException {
        return new BaseResponseEntity<>(
                reelsSearchService.searchReels(RequestScrollSearchReelsDto.from(requestScrollSearchReelsVo))
                        .map(ResponseScrollSearchReelsDto::toVo)
        );
    }
}
