package com.lubycon.eatitall.domain.restaurant.service;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_RESTAURANT_NOT_FOUND;

import com.lubycon.eatitall.api.model.response.restaurant.RestaurantDetailResponse;
import com.lubycon.eatitall.api.model.response.restaurant.RestaurantResponse;
import com.lubycon.eatitall.common.exception.NotFoundException;
import com.lubycon.eatitall.domain.curation.dto.CurationDto;
import com.lubycon.eatitall.domain.menu.dto.MenuDto;
import com.lubycon.eatitall.domain.menu.repository.MenuJpaRepository;
import com.lubycon.eatitall.domain.restaurant.dto.RestaurantDto;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import com.lubycon.eatitall.domain.restaurant.repository.CurationRestaurantQueryRepository;
import com.lubycon.eatitall.domain.restaurant.repository.RestaurantJpaRepository;
import java.util.List;
import java.util.stream.Collectors;
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
    List<RestaurantDto> restaurantDtos = restaurantJpaRepository.findRestaurants();

    ModelMapper modelMapper = new ModelMapper();
    return restaurantDtos.stream()
        .map(restaurantDto -> {
          RestaurantResponse restaurantResponse = modelMapper
              .map(restaurantDto, RestaurantResponse.class);
          if (restaurantDto.getHashtags() != null) {
            restaurantResponse.setHashtags(restaurantDto.getHashtags().split(","));
          }
          if (restaurantDto.getCurationIds() != null) {
            restaurantResponse.setCurationIds(restaurantDto.getCurationIds().split(","));
          }
          KakaoMap kakaoMap = KakaoMap.builder()
              .id(restaurantDto.getKakaoMapId())
              .latitude(restaurantDto.getLatitude())
              .longitude(restaurantDto.getLongitude())
              .build();
          restaurantResponse.setKakaoMap(kakaoMap);
          return restaurantResponse;
        })
        .collect(Collectors.toList());
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

    if (restaurant.getHashtags() != null) {
      restaurantDetailResponse.setHashtags(restaurant.getHashtags().split(","));
    }
    restaurantDetailResponse.setCurations(curationDtos);
    restaurantDetailResponse.setMenus(menuDtos);

    return restaurantDetailResponse;
  }

}
