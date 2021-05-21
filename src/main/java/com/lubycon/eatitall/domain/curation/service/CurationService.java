package com.lubycon.eatitall.domain.curation.service;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_CURATION_NOT_FOUND;

import com.lubycon.eatitall.api.model.response.curation.CurationDetailResponse;
import com.lubycon.eatitall.api.model.response.curation.CurationResponse;
import com.lubycon.eatitall.common.exception.NotFoundException;
import com.lubycon.eatitall.domain.curation.repository.CurationJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurationService {

  private final CurationJpaRepository curationJpaRepository;

  public List<CurationResponse> retrieveAllCurations() {
    return curationJpaRepository.findAllBy();
  }

  public CurationDetailResponse retrieveCurationByCurationId(Long curationId) {
    return curationJpaRepository.findByCurationId(curationId)
        .orElseThrow(() -> new NotFoundException(MSG_CURATION_NOT_FOUND));
  }

}
