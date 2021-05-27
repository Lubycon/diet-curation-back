package com.lubycon.eatitall.domain.restaurant.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class CurationRestaurantDto {

  @NotNull
  private final Long curationId;

  @NotNull
  private final Long restaurantId;

  @NotNull
  private final String name;

  @Nullable
  private final String hashtags;

  @NotNull
  private final String thumbnailImageUrl;

  @NotNull
  private final String address;

  @Nullable
  private final Long kakaoMapId;

  @Nullable
  private final BigDecimal latitude;

  @Nullable
  private final BigDecimal longitude;

  @QueryProjection
  public CurationRestaurantDto(@NotNull Long curationId,
      @NotNull Long restaurantId, @NotNull String name,
      @Nullable String hashtags,
      @NotNull String thumbnailImageUrl, @NotNull String address,
      @Nullable Long kakaoMapId, @Nullable BigDecimal latitude,
      @Nullable BigDecimal longitude) {
    this.curationId = curationId;
    this.restaurantId = restaurantId;
    this.name = name;
    this.hashtags = hashtags;
    this.thumbnailImageUrl = thumbnailImageUrl;
    this.address = address;
    this.kakaoMapId = kakaoMapId;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
