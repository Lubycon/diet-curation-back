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

  @Column(name="kakao_map_id", nullable = true)
  private Long id;

  @Column(nullable = true, precision = 11, scale = 7)
  private BigDecimal latitude;

  @Column(nullable = true, precision = 11, scale = 7)
  private BigDecimal longitude;

  @Builder
  public KakaoMap(Long id, BigDecimal latitude, BigDecimal longitude) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
