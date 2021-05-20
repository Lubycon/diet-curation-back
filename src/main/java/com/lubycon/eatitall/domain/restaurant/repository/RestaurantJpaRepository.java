package com.lubycon.eatitall.domain.restaurant.repository;

import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long> {

}
