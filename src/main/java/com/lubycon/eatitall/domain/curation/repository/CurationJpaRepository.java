package com.lubycon.eatitall.domain.curation.repository;

import com.lubycon.eatitall.domain.curation.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurationJpaRepository extends JpaRepository<Curation, Long> {

}
