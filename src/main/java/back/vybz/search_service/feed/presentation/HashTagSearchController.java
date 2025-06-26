package back.vybz.search_service.feed.presentation;

import back.vybz.search_service.common.entity.BaseResponseEntity;
import back.vybz.search_service.feed.application.service.HashTagSearchService;
import back.vybz.search_service.feed.dto.request.RequestSearchHashTagDto;
import back.vybz.search_service.feed.dto.response.ResponseSearchHashTagDto;
import back.vybz.search_service.feed.vo.request.RequestSearchHashTagVo;
import back.vybz.search_service.feed.vo.response.ResponseSearchHashTagVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class HashTagSearchController {

    private final HashTagSearchService hashTagSearchService;

    @Operation(
            summary = "해시태그 검색 API",
            description = "해시태그 키워드 기반으로 최대 60개까지 검색",
            tags = {"HASH-TAG-SEARCH"}
    )
    @GetMapping("/hashtags")
    public BaseResponseEntity<List<ResponseSearchHashTagVo>> searchHashTags(
            @ModelAttribute RequestSearchHashTagVo requestSearchHashTagVo
    ) throws IOException {

        RequestSearchHashTagDto dto = RequestSearchHashTagDto.from(requestSearchHashTagVo);

        List<ResponseSearchHashTagVo> result = hashTagSearchService.searchHashTags(dto).stream()
                .map(ResponseSearchHashTagDto::toVo)
                .collect(Collectors.toList());

        return new BaseResponseEntity<>(result);
    }
}
