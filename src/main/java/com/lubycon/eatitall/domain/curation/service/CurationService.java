package com.lubycon.eatitall.domain.curation.service;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_CURATION_NOT_FOUND;

import com.lubycon.eatitall.api.model.response.curation.CurationDetailResponse;
import com.lubycon.eatitall.api.model.response.curation.CurationResponse;
import com.lubycon.eatitall.common.exception.NotFoundException;
import com.lubycon.eatitall.domain.curation.entity.Curation;
import com.lubycon.eatitall.domain.curation.repository.CurationJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurationService {

  private final CurationJpaRepository curationJpaRepository;

  public List<CurationResponse> retrieveAllCurations() {
    return curationJpaRepository.findAllByIsHidden(0);
  }

  public CurationDetailResponse retrieveCurationByCurationId(Long curationId) {
    Curation curation = curationJpaRepository.findById(curationId)
        .orElseThrow(() -> new NotFoundException(MSG_CURATION_NOT_FOUND));
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(curation, CurationDetailResponse.class);
  }

}
