package com.lubycon.eatitall.domain.restaurant.repository;

import static com.lubycon.eatitall.domain.curation.entity.QCuration.curation;
import static com.lubycon.eatitall.domain.restaurant.entity.QCurationRestaurant.curationRestaurant;
import static com.lubycon.eatitall.domain.restaurant.entity.QRestaurant.restaurant;

import com.lubycon.eatitall.api.model.response.restaurant.CurationRestaurantResponse;
import com.lubycon.eatitall.api.model.response.restaurant.QCurationRestaurantResponse;
import com.lubycon.eatitall.common.repository.AbstractQueryRepository;
import com.lubycon.eatitall.domain.curation.dto.CurationDto;
import com.lubycon.eatitall.domain.curation.dto.QCurationDto;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CurationRestaurantQueryRepository extends AbstractQueryRepository {

  public List<CurationRestaurantResponse> findRestaurantsByCurationId(Long curationId) {
    return select(
        new QCurationRestaurantResponse(
            curationRestaurant.curation.curationId,
            restaurant.restaurantId,
            restaurant.name,
            restaurant.hashtags,
            restaurant.thumbnailImageUrl,
            restaurant.address,
            restaurant.kakaoMap.kakaoMapId,
            restaurant.kakaoMap.mapLatitude,
            restaurant.kakaoMap.mapLongitude
        ))
        .from(curationRestaurant)
        .join(restaurant)
        .on(curationRestaurant.restaurant.restaurantId.eq(restaurant.restaurantId))
        .where(curationRestaurant.curation.curationId.eq(curationId))
        .fetch();
  }

  public List<CurationDto> findCurationsByRestaurantId(Long restaurantId) {
    return select(
        new QCurationDto(
            curationRestaurant.curation.curationId,
            curation.title
        ))
        .from(curationRestaurant)
        .join(curation)
        .on(curationRestaurant.curation.curationId.eq(curation.curationId))
        .where(curationRestaurant.restaurant.restaurantId.eq(restaurantId))
        .fetch();
  }
}
