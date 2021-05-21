package com.lubycon.eatitall.api.model.response.restaurant;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class CurationRestaurantResponse {

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
  private final String district;

  @NotNull
  private final String street;

  @NotNull
  private final String detailedAddress;

  @NotNull
  private final String fullAddress;

  @Nullable
  private final Long kakaoMapId;

  @Nullable
  private final BigDecimal mapLatitude;

  @Nullable
  private final BigDecimal mapLongitude;

  @QueryProjection
  public CurationRestaurantResponse(@NotNull Long curationId,
      @NotNull Long restaurantId, @NotNull String name,
      @Nullable String hashtags,
      @NotNull String thumbnailImageUrl, @NotNull String district,
      @NotNull String street, @NotNull String detailedAddress,
      @NotNull String fullAddress, @Nullable Long kakaoMapId,
      @Nullable BigDecimal mapLatitude,
      @Nullable BigDecimal mapLongitude) {
    this.curationId = curationId;
    this.restaurantId = restaurantId;
    this.name = name;
    this.hashtags = hashtags;
    this.thumbnailImageUrl = thumbnailImageUrl;
    this.district = district;
    this.street = street;
    this.detailedAddress = detailedAddress;
    this.fullAddress = fullAddress;
    this.kakaoMapId = kakaoMapId;
    this.mapLatitude = mapLatitude;
    this.mapLongitude = mapLongitude;
  }
}
