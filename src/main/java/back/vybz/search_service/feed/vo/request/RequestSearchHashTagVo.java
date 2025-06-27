package back.vybz.search_service.feed.vo.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestSearchHashTagVo {
    private String keyword;
    private Integer size;

    @Builder
    public RequestSearchHashTagVo(String keyword,
                                  Integer size) {
        this.keyword = keyword;
        this.size = size;
    }
}
