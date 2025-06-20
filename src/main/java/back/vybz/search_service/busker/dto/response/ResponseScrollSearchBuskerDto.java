package back.vybz.search_service.busker.dto.response;

import back.vybz.search_service.busker.vo.response.ResponseScrollSearchBuskerVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseScrollSearchBuskerDto {

    private String buskerUuid;
    private String nickname;
    private String profileImageUrl;
    // private Integer followerCount;  // followerCount 사용 X

    @Builder
    private ResponseScrollSearchBuskerDto(String buskerUuid, String nickname, String profileImageUrl /*, Integer followerCount */) {
        this.buskerUuid = buskerUuid;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        // this.followerCount = followerCount;
    }

    public ResponseScrollSearchBuskerVo toVo() {
        return ResponseScrollSearchBuskerVo.builder()
                .buskerUuid(buskerUuid)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                // .followerCount(followerCount)
                .build();
    }
}
