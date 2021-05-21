package com.lubycon.eatitall.domain.restaurant.repository;

import com.lubycon.eatitall.api.model.response.restaurant.RestaurantResponse;
import com.lubycon.eatitall.domain.restaurant.dto.RestaurantDetailDto;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long> {

  List<RestaurantResponse> findAllBy();

  Optional<RestaurantDetailDto> findByRestaurantId(Long RestaurantId);

}
