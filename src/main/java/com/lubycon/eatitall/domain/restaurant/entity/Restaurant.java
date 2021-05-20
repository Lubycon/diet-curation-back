package com.lubycon.eatitall.domain.restaurant.entity;

import static com.lubycon.eatitall.common.util.CommonUtils.userIp;

import com.lubycon.eatitall.common.entity.BaseEntity;
import com.lubycon.eatitall.domain.restaurant.model.Address;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
public class Restaurant extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long restaurantId;

  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Lob
  @Column(nullable = true)
  private String description;

  @Column(nullable = true, length = 300)
  private String hashtags;

  @Column(nullable = true, length = 500)
  private String thumbnailImageUrl;

  @Embedded
  private Address address;

  @Embedded
  private KakaoMap kakaoMap;

  @Builder
  public Restaurant(String name, String description, String hashtags,
      String thumbnailImageUrl, Address address, KakaoMap kakaoMap) {
    this.setCreatedIp(userIp());
    this.name = name;
    this.description = description;
    this.hashtags = hashtags;
    this.thumbnailImageUrl = thumbnailImageUrl;
    this.address = address;
    this.kakaoMap = kakaoMap;
  }
}
