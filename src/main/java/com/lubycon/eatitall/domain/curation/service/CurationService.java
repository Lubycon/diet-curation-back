package com.lubycon.eatitall.domain.curation.service;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_CURATION_NOT_FOUND;

import com.lubycon.eatitall.api.model.response.curation.CurationDetailResponse;
import com.lubycon.eatitall.api.model.response.curation.CurationResponse;
import com.lubycon.eatitall.api.model.response.restaurant.CurationRestaurantResponse;
import com.lubycon.eatitall.common.exception.NotFoundException;
import com.lubycon.eatitall.domain.curation.entity.Curation;
import com.lubycon.eatitall.domain.curation.repository.CurationJpaRepository;
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
public class CurationService {

  private final CurationJpaRepository curationJpaRepository;
  private final CurationRestaurantQueryRepository curationRestaurantQueryRepository;

  public List<CurationResponse> retrieveAllCurations() {
    return curationJpaRepository.findAllByIsHidden(0);
  }

  public CurationDetailResponse retrieveCurationByCurationId(Long curationId) {
    Curation curation = curationJpaRepository.findById(curationId)
        .orElseThrow(() -> new NotFoundException(MSG_CURATION_NOT_FOUND));

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(
        MatchingStrategies.STRICT);

    List<CurationRestaurantResponse> restaurants = curationRestaurantQueryRepository
        .findRestaurantsByCurationId(curationId)
        .stream()
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

    CurationDetailResponse curationDetailResponse = modelMapper
        .map(curation, CurationDetailResponse.class);
    curationDetailResponse.setRestaurants(restaurants);
    return curationDetailResponse;
  }

}
