package dev.ams.ai.shoppingservice.config.rag;

import dev.ams.ai.shoppingservice.entity.Product;
import dev.ams.ai.shoppingservice.entity.Shop;
import dev.ams.ai.shoppingservice.repository.CustomerRepository;
import dev.ams.ai.shoppingservice.repository.ProductRepository;
import dev.ams.ai.shoppingservice.repository.ShopRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EntityDocumentReader implements DocumentReader {

    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;
    private final ShopRepository shopRepo;

    public EntityDocumentReader(ProductRepository p, CustomerRepository c, ShopRepository s) {
        this.productRepo = p;
        this.customerRepo = c;
        this.shopRepo = s;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> read() {
        return this.get();
    }

    public List<Document> get() {
        List<Document> docs = new ArrayList<>();

        // Products
        productRepo.findAll().forEach(p -> {
            docs.add(toProductDocument(p));
        });

        // Shops
        shopRepo.findAll().forEach(s -> {
            docs.add(toShopDocument(s));
        });

        return docs;
    }

    private Document toProductDocument(Product product) {
        String content = String.format(
                "%s is a %s product priced at %.2f and sold by %s. It is described as: %s",
                product.getTitle(),
                product.getCategory(),
                product.getPrice(),
                product.getShop().getName(),
                product.getDescription()
        );

        Map<String, Object> metadata = Map.of(
                "entityType", "Product",
                "productId", product.getId(),
                "category", product.getCategory()
        );

        return new Document(content, metadata);
    }

    private Document toShopDocument(Shop shop) {
        String content = String.format(
                "Shop %s has a rating of %.2f and is selling %s categories with. It is described as: %s",
                shop.getName(),
                shop.getRating(),
                shop.getCategories(),
                shop.getDescription()
        );
        Map<String, Object> metadata = Map.of(
                "entityType", "Shop",
                "shopId", shop.getId(),
                "categories", String.join(", ", shop.getCategories())
        );

        return new Document(content, metadata);


    }
}

