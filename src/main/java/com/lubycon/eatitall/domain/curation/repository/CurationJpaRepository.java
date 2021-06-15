package com.lubycon.eatitall.domain.curation.repository;

import com.lubycon.eatitall.api.model.response.curation.CurationResponse;
import com.lubycon.eatitall.domain.curation.entity.Curation;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationJpaRepository extends JpaRepository<Curation, Long> {

  List<CurationResponse> findAllByIsHidden(int isHidden);

  @NotNull
  Optional<Curation> findById(@NotNull Long curationId);

  Optional<Curation> findByTitle(String title);

}
