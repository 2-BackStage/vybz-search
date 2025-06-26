package back.vybz.search_service.busker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "create-busker-search", createIndex = false)
public class BuskerSearchDocument {

    @Id
    private String buskerUuid;
    private String nickname;
    private String nicknameChosung;
    private String profileImageUrl;
    private Integer followerCount;



}
