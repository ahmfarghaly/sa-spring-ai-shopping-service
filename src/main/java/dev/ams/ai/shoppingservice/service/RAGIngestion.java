package dev.ams.ai.shoppingservice.service;

import dev.ams.ai.shoppingservice.config.rag.EntityDocumentReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@ConditionalOnProperty(name = "application.rag.ingestion.enabled", havingValue = "true")
@Component
@RequiredArgsConstructor
public class RAGIngestion {

    private final EntityDocumentReader entityDocumentReader;
    private final List<DocumentTransformer> transformers;
    private final VectorStore writer;

    @PostConstruct
    public void runIngestion() {
        List<Document> docs = entityDocumentReader.read();
        for (DocumentTransformer transformer : transformers) {
            docs = transformer.transform(docs);
        }
        writer.write(docs);
    }
}
