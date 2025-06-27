package back.vybz.search_service.feed.dto.response;

import back.vybz.search_service.feed.vo.response.ResponseScrollSearchReelsVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseScrollSearchReelsDto {

    private String id;
    private String writerUuid;
    private String content;
    private List<String> hashTag;
    private String thumbnailUrl;
    private Long createdAt;

    @Builder
    public ResponseScrollSearchReelsDto(String id,
                                        String writerUuid,
                                        String content,
                                        List<String> hashTag,
                                        String thumbnailUrl,
                                        Long createdAt) {
        this.id = id;
        this.writerUuid = writerUuid;
        this.content = content;
        this.hashTag = hashTag;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
    }

    public ResponseScrollSearchReelsVo toVo() {
        return ResponseScrollSearchReelsVo.builder()
                .id(id)
                .writerUuid(writerUuid)
                .content(content)
                .hashTag(hashTag)
                .thumbnailUrl(thumbnailUrl)
                .createdAt(createdAt)
                .build();
    }
}
