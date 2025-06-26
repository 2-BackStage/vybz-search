package back.vybz.search_service.feed.infrastructure;

import back.vybz.search_service.feed.domain.ReelsDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReelsDocumentRepository extends ElasticsearchRepository<ReelsDocument, String> {
}
