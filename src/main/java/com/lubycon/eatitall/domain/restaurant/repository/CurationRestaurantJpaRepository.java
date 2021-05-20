package com.lubycon.eatitall.domain.restaurant.repository;

import com.lubycon.eatitall.domain.restaurant.entity.CurationRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationRestaurantJpaRepository extends JpaRepository<CurationRestaurant, Long> {

}