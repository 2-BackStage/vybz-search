package back.vybz.search_service.feed.application.service;

import back.vybz.search_service.feed.dto.request.RequestSearchHashTagDto;
import back.vybz.search_service.feed.dto.response.ResponseSearchHashTagDto;

import java.io.IOException;
import java.util.List;

public interface HashTagSearchService {
    List<ResponseSearchHashTagDto> searchHashTags(RequestSearchHashTagDto requestSearchHashTagDto) throws IOException;
}
