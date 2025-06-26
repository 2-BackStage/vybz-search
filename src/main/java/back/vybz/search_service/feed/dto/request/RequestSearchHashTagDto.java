package back.vybz.search_service.feed.dto.request;

import back.vybz.search_service.feed.vo.request.RequestSearchHashTagVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSearchHashTagDto {

    private String keyword;
    private Integer size;

    @Builder
    public RequestSearchHashTagDto(String keyword,
                                   Integer size) {
        this.keyword = keyword;
        this.size = size;
    }

    public static RequestSearchHashTagDto from(RequestSearchHashTagVo requestSearchHashTagVo) {
        return RequestSearchHashTagDto.builder()
                .keyword(requestSearchHashTagVo.getKeyword())
                .size(requestSearchHashTagVo.getSize())
                .build();
    }
}