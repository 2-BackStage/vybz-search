package back.vybz.search_service.feed.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseScrollSearchReelsVo {

    private String id;
    private String writerUuid;
    private String content;
    private List<String> hashTag;
    private String thumbnailUrl;
    private Long createdAt;

    @Builder
    public ResponseScrollSearchReelsVo(String id,
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
}
