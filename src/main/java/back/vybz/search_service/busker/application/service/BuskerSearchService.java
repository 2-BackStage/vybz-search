package back.vybz.search_service.busker.application.service;

import back.vybz.search_service.busker.dto.request.RequestScrollSearchBuskerDto;
import back.vybz.search_service.busker.dto.response.ResponseScrollSearchBuskerDto;
import back.vybz.search_service.common.util.CursorPage;

import java.io.IOException;

public interface BuskerSearchService {
    CursorPage<ResponseScrollSearchBuskerDto> searchBuskers(RequestScrollSearchBuskerDto requestScrollSearchBuskerDto)throws IOException;

}
