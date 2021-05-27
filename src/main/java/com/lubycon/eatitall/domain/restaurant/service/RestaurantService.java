package com.lubycon.eatitall.domain.restaurant.service;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_RESTAURANT_NOT_FOUND;

import com.lubycon.eatitall.api.model.response.restaurant.RestaurantDetailResponse;
import com.lubycon.eatitall.api.model.response.restaurant.RestaurantResponse;
import com.lubycon.eatitall.common.exception.NotFoundException;
import com.lubycon.eatitall.domain.curation.dto.CurationDto;
import com.lubycon.eatitall.domain.menu.dto.MenuDto;
import com.lubycon.eatitall.domain.menu.repository.MenuJpaRepository;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import com.lubycon.eatitall.domain.restaurant.repository.CurationRestaurantQueryRepository;
import com.lubycon.eatitall.domain.restaurant.repository.RestaurantJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final RestaurantJpaRepository restaurantJpaRepository;
  private final CurationRestaurantQueryRepository curationRestaurantQueryRepository;
  private final MenuJpaRepository menuJpaRepository;

  public List<RestaurantResponse> retrieveAllRestaurants() {
    return restaurantJpaRepository.findAllBy();
  }

  public RestaurantDetailResponse retrieveRestaurantByRestaurantId(Long restaurantId) {
    Restaurant restaurant = restaurantJpaRepository
        .findById(restaurantId)
        .orElseThrow(() -> new NotFoundException(MSG_RESTAURANT_NOT_FOUND));
    List<CurationDto> curationDtos = curationRestaurantQueryRepository
        .findCurationsByRestaurantId(restaurantId);
    List<MenuDto> menuDtos = menuJpaRepository.findMenusByRestaurantId(restaurantId);

    ModelMapper modelMapper = new ModelMapper();
    RestaurantDetailResponse restaurantDetailResponse = modelMapper
        .map(restaurant, RestaurantDetailResponse.class);
    restaurantDetailResponse.setCurations(curationDtos);
    restaurantDetailResponse.setMenus(menuDtos);

    return restaurantDetailResponse;
  }

}
