package back.vybz.search_service.feed.vo.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestScrollSearchReelsVo {
    private String keyword;
    private Integer size;
    private String cursorId;
    private Long cursorCreatedAt;

    @Builder
    public RequestScrollSearchReelsVo(String keyword,
                                      Integer size,
                                      String cursorId,
                                      Long cursorCreatedAt) {
        this.keyword = keyword;
        this.size = size;
        this.cursorId = cursorId;
        this.cursorCreatedAt = cursorCreatedAt;
    }
}
