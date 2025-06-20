package back.vybz.search_service.busker.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseScrollSearchBuskerVo {

    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;
    private Integer followerCount;

    @Builder
    public ResponseScrollSearchBuskerVo(String buskerUuid,
                                        String nickname,
                                        String profileImageUrl,
                                        Integer followerCount) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.followerCount = followerCount;
    }
}
