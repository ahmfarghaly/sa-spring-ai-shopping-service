package dev.ams.ai.shoppingservice.config.rag;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.model.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RAGIngestionConfig {

    @Bean
    public DocumentTransformer splitter() {
        return new TokenTextSplitter();
    }

    @Bean
    public DocumentTransformer keywordEnricher(ChatModel chatModel) {
        return KeywordMetadataEnricher.builder(chatModel)
                .keywordCount(5)
                .build();
    }
}

