package back.vybz.search_service.feed.application.service;

import back.vybz.search_service.common.util.CursorPage;
import back.vybz.search_service.feed.dto.request.RequestScrollSearchReelsDto;
import back.vybz.search_service.feed.dto.response.ResponseScrollSearchReelsDto;

import java.io.IOException;

public interface ReelsSearchService {
    CursorPage<ResponseScrollSearchReelsDto> searchReels(RequestScrollSearchReelsDto requestScrollSearchReelsDto) throws IOException;
}
