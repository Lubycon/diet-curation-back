package com.lubycon.eatitall.domain.restaurant.repository;

import com.lubycon.eatitall.domain.restaurant.entity.CurationRestaurant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationRestaurantJpaRepository extends JpaRepository<CurationRestaurant, Long> {

  Optional<CurationRestaurant> findByCurationIdAndRestaurantId(Long curationId, Long RestaurantId);

}