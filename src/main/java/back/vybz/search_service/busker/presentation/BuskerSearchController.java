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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
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

        log.debug("🔍 [검색 요청] keyword='{}', size={}, cursorFollowerCount={}, cursorBuskerUuid='{}'",
                requestScrollSearchBuskerVo.getKeyword(),
                requestScrollSearchBuskerVo.getSize(),
                requestScrollSearchBuskerVo.getCursorFollowerCount(),
                requestScrollSearchBuskerVo.getCursorBuskerUuid());
        return new BaseResponseEntity<>(
                buskerSearchService.searchBuskers(RequestScrollSearchBuskerDto.from(requestScrollSearchBuskerVo))
                        .map(ResponseScrollSearchBuskerDto::toVo)
        );
    }
}

