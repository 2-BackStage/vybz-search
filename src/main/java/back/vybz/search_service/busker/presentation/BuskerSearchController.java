package back.vybz.search_service.busker.presentation;

import back.vybz.search_service.busker.application.service.BuskerSearchService;
import back.vybz.search_service.busker.dto.request.RequestScrollSearchBuskerDto;
import back.vybz.search_service.busker.dto.response.ResponseScrollSearchBuskerDto;
import back.vybz.search_service.busker.vo.request.RequestScrollSearchBuskerVo;
import back.vybz.search_service.busker.vo.response.ResponseScrollSearchBuskerVo;
import back.vybz.search_service.common.entity.BaseResponseEntity;
import back.vybz.search_service.common.util.CursorPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class BuskerSearchController {

    private final BuskerSearchService buskerSearchService;

    @Operation(
            summary = "버스커 인물 검색 API (무한스크롤)",
            description = "닉네임으로 버스커 검색, followerCount 순 정렬, 무한스크롤 지원",
            tags = {"BUSKER-SEARCH-SERVICE"}
    )
    @GetMapping("/buskers")
    public BaseResponseEntity<CursorPage<ResponseScrollSearchBuskerVo>> searchBuskers(@ModelAttribute RequestScrollSearchBuskerVo requestScrollSearchBuskerVo) throws IOException {

        return new BaseResponseEntity<>(
                buskerSearchService.searchBuskers(RequestScrollSearchBuskerDto.from(requestScrollSearchBuskerVo))
                        .map(ResponseScrollSearchBuskerDto::toVo)
        );
    }
}

