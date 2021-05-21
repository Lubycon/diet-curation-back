package com.lubycon.eatitall.domain.restaurant.service;

import com.lubycon.eatitall.api.model.response.restaurant.CurationRestaurantResponse;
import com.lubycon.eatitall.domain.restaurant.repository.CurationRestaurantQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurationRestaurantService {

  private final CurationRestaurantQueryRepository curationRestaurantQueryRepository;

  public List<CurationRestaurantResponse> retrieveRestaurantsByCurationId(Long curationId) {
    return curationRestaurantQueryRepository.findRestaurantsByCurationId(curationId);
  }
}
