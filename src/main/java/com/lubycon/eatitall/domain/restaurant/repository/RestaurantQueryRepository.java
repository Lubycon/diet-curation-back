package com.lubycon.eatitall.domain.restaurant.repository;


import static com.lubycon.eatitall.domain.menu.entity.QMaterial.material;
import static com.lubycon.eatitall.domain.restaurant.entity.QRestaurant.restaurant;

import com.lubycon.eatitall.common.repository.AbstractQueryRepository;
import com.lubycon.eatitall.domain.restaurant.dto.QRestaurantDetailDto;
import com.lubycon.eatitall.domain.restaurant.dto.RestaurantDetailDto;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantQueryRepository extends AbstractQueryRepository {

  public RestaurantDetailDto findById(Long restaurantId) {
    return select(
        new QRestaurantDetailDto(
            restaurant.id,
            restaurant.name,
            restaurant.description,
            restaurant.hashtags,
            restaurant.thumbnailImageUrl,
            restaurant.address,
            restaurant.kakaoMap.id,
            restaurant.kakaoMap.latitude,
            restaurant.kakaoMap.longitude,
            material.id,
            material.name,
            material.contents
        ))
        .from(restaurant)
        .join(material)
        .on(restaurant.materialId.eq(material.id))
        .where(restaurant.id.eq(restaurantId))
        .fetchOne();
  }
}
