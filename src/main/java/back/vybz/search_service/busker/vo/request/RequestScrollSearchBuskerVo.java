package back.vybz.search_service.busker.vo.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestScrollSearchBuskerVo {

    private String keyword;
    private Integer size;
    private Integer cursorFollowerCount;
    private String cursorBuskerUuid;

    @Builder
    public RequestScrollSearchBuskerVo(String keyword,
                                       Integer size,
                                       Integer cursorFollowerCount,
                                       String cursorBuskerUuid) {
        this.keyword = keyword;
        this.size = size;
        this.cursorFollowerCount = cursorFollowerCount;
        this.cursorBuskerUuid = cursorBuskerUuid;
    }

}
