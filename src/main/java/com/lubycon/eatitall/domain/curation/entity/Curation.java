package com.lubycon.eatitall.domain.curation.entity;

import static com.lubycon.eatitall.common.util.CommonUtils.userIp;

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
public class Curation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Lob
  @Column(nullable = true)
  private String contents;

  @Column(nullable = true, length = 500)
  private String imageUrl;

  @Builder
  public Curation(String title, String contents, int isHidden) {
    this.setCreatedIp(userIp());
    this.contents = contents;
    this.isHidden = isHidden;
  }

  public void updateCuration(Curation curation) {
    this.imageUrl = curation.imageUrl;
    this.isHidden = curation.isHidden;
  }

  public void updateImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}