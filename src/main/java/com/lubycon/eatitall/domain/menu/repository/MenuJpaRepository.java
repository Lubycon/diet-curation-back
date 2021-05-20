package com.lubycon.eatitall.domain.menu.repository;

import com.lubycon.eatitall.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuJpaRepository extends JpaRepository<Menu, Long> {

}
