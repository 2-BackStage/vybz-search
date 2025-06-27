package back.vybz.search_service.feed.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSearchHashTagVo {
    private String tagName;

    @Builder
    public ResponseSearchHashTagVo(String tagName) {
        this.tagName = tagName;
    }
}