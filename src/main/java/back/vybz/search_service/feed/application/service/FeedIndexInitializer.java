//package back.vybz.search_service.feed.application.service;
//
//import back.vybz.search_service.feed.domain.FeedDocument;
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//public class FeedIndexInitializer {
//
//    private final ElasticsearchClient elasticsearchClient;
//    private final FeedDocument feedDocument;
//
//    @PostConstruct
//    public void init() throws IOException {
//        createSimpleSearchIndex();
//        createdWithAutoComplete();
//    }
//}
