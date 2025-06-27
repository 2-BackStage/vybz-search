package back.vybz.search_service.feed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "create-feed-search", createIndex = false)
public class ReelsDocument {

    @Id
    private String id;
    private String writerUuid;
    private String content;
    private List<String> hashTag;
    private String thumbnailUrl;
    private Long createdAt;

}
