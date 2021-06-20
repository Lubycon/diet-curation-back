package com.lubycon.eatitall.domain.menu.entity;

import com.lubycon.eatitall.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Material extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length=100)
  private String name;

  @Lob
  @Column(nullable = true)
  private String contents;

  @Builder
  public Material(String name, String contents, int isHidden) {
    this.name = name;
    this.contents = contents;
    this.isHidden = isHidden;
  }

  public void updateMaterial(Material buildMaterial) {
    this.name = buildMaterial.name;
    this.contents = buildMaterial.contents;
    this.isHidden = buildMaterial.isHidden;
  }
}
