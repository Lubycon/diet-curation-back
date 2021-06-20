package com.lubycon.eatitall.domain.material.repository;

import com.lubycon.eatitall.domain.menu.entity.Material;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialJpaRepository extends JpaRepository<Material, Long> {

  Optional<Material> findByName(String name);

}
