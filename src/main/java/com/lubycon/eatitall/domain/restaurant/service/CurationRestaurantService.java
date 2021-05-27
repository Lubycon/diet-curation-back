package com.lubycon.eatitall.domain.restaurant.service;

import com.lubycon.eatitall.api.model.response.restaurant.CurationRestaurantResponse;
import com.lubycon.eatitall.domain.restaurant.dto.CurationRestaurantDto;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import com.lubycon.eatitall.domain.restaurant.repository.CurationRestaurantQueryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurationRestaurantService {

  private final CurationRestaurantQueryRepository curationRestaurantQueryRepository;

  public List<CurationRestaurantResponse> retrieveRestaurantsByCurationId(Long curationId) {
    List<CurationRestaurantDto> restaurants = curationRestaurantQueryRepository
        .findRestaurantsByCurationId(curationId);
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(
        MatchingStrategies.STRICT);
    return restaurants.stream()
        .map(curationRestaurantDto -> {
          CurationRestaurantResponse curationRestaurantResponse = modelMapper
              .map(curationRestaurantDto, CurationRestaurantResponse.class);
          curationRestaurantResponse.setId(curationRestaurantDto.getRestaurantId());
          if (curationRestaurantDto.getHashtags() != null) {
            curationRestaurantResponse.setHashtags(curationRestaurantDto.getHashtags().split(","));
          }
          KakaoMap kakaoMap = KakaoMap.builder()
              .id(curationRestaurantDto.getKakaoMapId())
              .latitude(curationRestaurantDto.getLatitude())
              .longitude(curationRestaurantDto.getLongitude())
              .build();
          curationRestaurantResponse.setKakaoMap(kakaoMap);
          return curationRestaurantResponse;
        })
        .collect(Collectors.toList());
  }
}
