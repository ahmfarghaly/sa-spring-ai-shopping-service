package dev.ams.ai.shoppingservice.controller;

import dev.ams.ai.shoppingservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/shopping-behavior/{customerId}")
    public ResponseEntity<String> analyzeShoppingBehavior(@PathVariable Long customerId) {
        log.info("Analyzing shopping behavior for customer: {}", customerId);
        String analysis = analyticsService.analyzeCustomerBehavior(customerId);
        log.debug("Analysis result for customer {}: {}", customerId, analysis);
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/product-trends")
    public ResponseEntity<String> analyzeProductTrends() {
        log.info("Analyzing product trends");
        String analysis = analyticsService.analyzeProductTrends();
        log.debug("Product trends analysis: {}", analysis);
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/customer-segments")
    public ResponseEntity<String> analyzeCustomerSegments() {
        log.info("Analyzing customer segments");
        String analysis = analyticsService.analyzeCustomerSegments();
        log.debug("Customer segments analysis: {}", analysis);
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/customer-journey/{customerId}")
    public ResponseEntity<String> analyzeCustomerJourney(@PathVariable Long customerId) {
        log.info("Analyzing customer journey for customer: {}", customerId);
        String journeyAnalysis = analyticsService.analyzeCustomerJourney(customerId);
        log.debug("Customer journey analysis for customer {}: {}", customerId, journeyAnalysis);
        return ResponseEntity.ok(journeyAnalysis);
    }

    @GetMapping("/product-performance/{productId}")
    public ResponseEntity<String> analyzeProductPerformance(@PathVariable Long productId) {
        log.info("Analyzing performance for product: {}", productId);
        String performance = analyticsService.analyzeProductPerformance(productId);
        log.debug("Product performance analysis for product {}: {}", productId, performance);
        return ResponseEntity.ok(performance);
    }

    @GetMapping("/customer-ltv/{customerId}")
    public ResponseEntity<String> predictCustomerLTV(@PathVariable Long customerId) {
        log.info("Predicting LTV for customer: {}", customerId);
        String ltvPrediction = analyticsService.predictCustomerLTV(customerId);
        log.debug("LTV prediction for customer {}: {}", customerId, ltvPrediction);
        return ResponseEntity.ok(ltvPrediction);
    }

    @GetMapping("/trending-products")
    public ResponseEntity<String> getTrendingProducts(
            @RequestParam(defaultValue = "7") int days) {
        log.info("Fetching trending products for the last {} days", days);
        String trendingProducts = analyticsService.getTrendingProducts(days);
        log.debug("Trending products for the last {} days: {}", days, trendingProducts);
        return ResponseEntity.ok(trendingProducts);
    }

//    @GetMapping("/conversion-funnel")
//    public ResponseEntity<String> analyzeConversionFunnel(
//            @RequestParam(defaultValue = "30") int days) {
//        log.info("Analyzing conversion funnel for the last {} days", days);
//        String funnelAnalysis = analyticsService.analyzeConversionFunnel(days);
//        log.debug("Conversion funnel analysis for the last {} days: {}", days, funnelAnalysis);
//        return ResponseEntity.ok(funnelAnalysis);
//    }

}
