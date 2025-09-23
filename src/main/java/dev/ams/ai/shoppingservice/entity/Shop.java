package dev.ams.ai.shoppingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.ams.ai.shoppingservice.config.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
@Data
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Convert(converter = StringListConverter.class)
    private List<String> categories;

    // Average rating out of 5.00
    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    private String logoUrl;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("shop")
    private List<Product> products = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
