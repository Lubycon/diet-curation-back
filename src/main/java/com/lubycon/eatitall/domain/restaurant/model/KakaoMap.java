package com.lubycon.eatitall.domain.restaurant.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class KakaoMap {

  @Column(nullable = true)
  private Long kakaoMapId;

  @Column(nullable = true, precision = 11, scale = 7)
  private BigDecimal mapLatitude;

  @Column(nullable = true, precision = 11, scale = 7)
  private BigDecimal mapLongitude;

  @Builder
  public KakaoMap(Long kakaoMapId, BigDecimal mapLatitude, BigDecimal mapLongitude) {
    this.kakaoMapId = kakaoMapId;
    this.mapLatitude = mapLatitude;
    this.mapLongitude = mapLongitude;
  }
}
