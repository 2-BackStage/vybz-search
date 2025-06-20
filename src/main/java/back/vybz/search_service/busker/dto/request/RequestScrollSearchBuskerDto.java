package back.vybz.search_service.busker.dto.request;

import back.vybz.search_service.busker.vo.request.RequestScrollSearchBuskerVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestScrollSearchBuskerDto {

    private String keyword;
    private Integer size;
//    private Integer cursorFollowerCount;
    private String cursorBuskerUuid;

    @Builder
    public RequestScrollSearchBuskerDto(String keyword,
                                        Integer size,
                                        Integer cursorFollowerCount,
                                        String cursorBuskerUuid) {
        this.keyword = keyword;
        this.size = size;
//        this.cursorFollowerCount = cursorFollowerCount;
        this.cursorBuskerUuid = cursorBuskerUuid;
    }
    public static RequestScrollSearchBuskerDto from(RequestScrollSearchBuskerVo requestScrollSearchBuskerVo) {
        return RequestScrollSearchBuskerDto.builder()
                .keyword(requestScrollSearchBuskerVo.getKeyword())
                .size(requestScrollSearchBuskerVo.getSize())
//                .cursorFollowerCount(requestScrollSearchBuskerVo.getCursorFollowerCount())
                .cursorBuskerUuid(requestScrollSearchBuskerVo.getCursorBuskerUuid())
                .build();
    }
}
