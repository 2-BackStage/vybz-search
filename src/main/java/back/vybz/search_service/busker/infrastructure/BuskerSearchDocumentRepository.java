package back.vybz.search_service.busker.infrastructure;

import back.vybz.search_service.busker.domain.BuskerSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BuskerSearchDocumentRepository extends ElasticsearchRepository<BuskerSearchDocument,String> {
}
