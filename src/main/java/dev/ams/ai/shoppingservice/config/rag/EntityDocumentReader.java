package dev.ams.ai.shoppingservice.config.rag;

import dev.ams.ai.shoppingservice.entity.Customer;
import dev.ams.ai.shoppingservice.entity.Order;
import dev.ams.ai.shoppingservice.entity.Product;
import dev.ams.ai.shoppingservice.repository.CustomerRepository;
import dev.ams.ai.shoppingservice.repository.OrderRepository;
import dev.ams.ai.shoppingservice.repository.ProductRepository;
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
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;

    public EntityDocumentReader(ProductRepository p, OrderRepository o, CustomerRepository c) {
        this.productRepo = p;
        this.orderRepo = o;
        this.customerRepo = c;
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

        // Should be limited in a real scenario
        orderRepo.findAll().forEach(o -> {

            docs.add(toOrderDocument(o));
        });

        // Customer summary, in a real scenario this should be protected for privacy
        customerRepo.findAll().forEach(cu -> {

            docs.add(toCustomerDocument(cu));
        });

        return docs;
    }

    private Document toCustomerDocument(Customer customer) {
        String content = String.format(
                "%s is a %d-year-old %s customer. %s",
                customer.getName(),
                customer.getAge(),
                customer.getGender().name().toLowerCase(),
                enrichCustomerContext(customer)
        );

        Map<String, Object> metadata = Map.of(
                "entityType", "Customer",
                "customerId", customer.getId(),
                "age", customer.getAge(),
                "gender", customer.getGender().name()
        );
        return new Document(content, metadata);
    }

    private String enrichCustomerContext(Customer customer) {
        // Add richer, descriptive text for better embeddings
        StringBuilder sb = new StringBuilder();
        sb.append("Purchased products: ");
        orderRepo.findByCustomerId(customer.getId()).forEach(o -> {
            o.getItems().forEach(item -> {
                sb.append(item.getProduct().getTitle())
                        .append(", ");
            });
        });
        return sb.toString();
    }

    private Document toProductDocument(Product product) {
        String content = String.format(
                "%s is a %s product priced at %.2f. It is described as: %s",
                product.getTitle(),
                product.getCategory(),
                product.getPrice(),
                product.getDescription()
        );

        Map<String, Object> metadata = Map.of(
                "entityType", "Product",
                "productId", product.getId(),
                "category", product.getCategory()
        );

        return new Document(content, metadata);
    }

    private Document toOrderDocument(Order order) {
        String content = String.format(
                "Order %d was placed by customer %s. It contains %d products and totals %.2f.",
                order.getId(),
                order.getCustomer().getName(),
                order.getItems().size(),
                order.getTotalAmount()
        );

        Map<String, Object> metadata = Map.of(
                "entityType", "Order",
                "orderId", order.getId(),
                "customerId", order.getCustomer().getId()
        );

        return new Document(content, metadata);
    }
}

