package dev.ams.ai.shoppingservice.repository;

import dev.ams.ai.shoppingservice.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

}
