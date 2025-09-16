package dev.ams.ai.shoppingservice.controller;

import dev.ams.ai.shoppingservice.entity.Product;
import dev.ams.ai.shoppingservice.service.EventsPublisherService;
import dev.ams.ai.shoppingservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final EventsPublisherService eventsPublisherService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Fetching all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Simulate a product view workflow, in real scenario would be triggered by a UI event.
     * @param id The id of the product
     * @param customerId The id of the customer
     * @param sessionId The session id of the customer
     * @param viewDuration The duration of the view
     * @return The product
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id,
                                              @RequestParam(required = false) Long customerId,
                                              @RequestHeader(value = "X-Session-ID", required = false) String sessionId,
                                              @RequestParam(required = false, defaultValue = "0") Integer viewDuration) {
        Product product = productService.getProduct(id);

        if (customerId != null) {
            eventsPublisherService.publishProductView(customerId, id, sessionId, viewDuration, "direct");
        }

        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        log.info("Fetching products by category: {}", category);
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query,
                                                        @RequestParam(required = false) Long customerId,
                                                        @RequestHeader(value = "X-Session-ID", required = false) String sessionId) {
        List<Product> products = productService.searchProducts(query);

        if (customerId != null) {
            eventsPublisherService.publishSearch(customerId, query, products.size(), sessionId);
        }

        return ResponseEntity.ok(products);
    }
}
