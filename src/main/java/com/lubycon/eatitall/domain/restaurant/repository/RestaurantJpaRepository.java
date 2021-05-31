package com.lubycon.eatitall.domain.restaurant.repository;

import com.lubycon.eatitall.domain.restaurant.dto.RestaurantDto;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long> {

  @Query(name = "find_restaurant_dto", nativeQuery = true)
  List<RestaurantDto> findRestaurants();

  @Query(value = "select r from Restaurant r where r.id = :restaurntId and r.isHidden = 0")
  @NotNull
  Optional<Restaurant> findById(@Param("restaurntId") @NotNull Long restaurantId);

  Restaurant findByName(String name);

}
