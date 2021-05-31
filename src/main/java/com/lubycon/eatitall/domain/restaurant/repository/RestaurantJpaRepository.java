package com.lubycon.eatitall.domain.restaurant.repository;

import com.lubycon.eatitall.domain.restaurant.dto.RestaurantDto;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long> {

  @Query(name = "find_restaurant_dto", nativeQuery = true)
  List<RestaurantDto> findRestaurants();

  Optional<Restaurant> findById(Long restaurantId);

}
