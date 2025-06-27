package back.vybz.search_service.feed.dto.request;

import back.vybz.search_service.feed.vo.request.RequestScrollSearchReelsVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestScrollSearchReelsDto {

    private String keyword;
    private Integer size;
    private String cursorId;
    private Long cursorCreatedAt;

    @Builder
    public RequestScrollSearchReelsDto(String keyword,
                                       Integer size,
                                       String cursorId,
                                       Long cursorCreatedAt) {
        this.keyword = keyword;
        this.size = size;
        this.cursorId = cursorId;
        this.cursorCreatedAt = cursorCreatedAt;
    }

    public static RequestScrollSearchReelsDto from(RequestScrollSearchReelsVo requestScrollSearchReelsVo) {
        return RequestScrollSearchReelsDto.builder()
                .keyword(requestScrollSearchReelsVo.getKeyword())
                .size(requestScrollSearchReelsVo.getSize())
                .cursorId(requestScrollSearchReelsVo.getCursorId())
                .cursorCreatedAt(requestScrollSearchReelsVo.getCursorCreatedAt())
                .build();
    }
}
