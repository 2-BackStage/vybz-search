package back.vybz.search_service.feed.dto.response;

import back.vybz.search_service.feed.vo.response.ResponseSearchHashTagVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSearchHashTagDto {
    private String tagName;

    @Builder
    public ResponseSearchHashTagDto(String tagName) {
        this.tagName = tagName;
    }

    public ResponseSearchHashTagVo toVo() {
        return ResponseSearchHashTagVo.builder()
                .tagName(this.tagName)
                .build();
    }
}
