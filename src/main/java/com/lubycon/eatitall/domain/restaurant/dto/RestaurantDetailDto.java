package com.lubycon.eatitall.domain.restaurant.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@Getter
public class RestaurantDetailDto {

  @NotNull
  private Long id;

  @NotNull
  private String name;

  @Nullable
  private String description;

  @Nullable
  private String hashtags;

  @Nullable
  private String thumbnailImageUrl;

  @NotNull
  private String address;

  @Nullable
  private Long kakaoMapId;

  @Nullable
  private BigDecimal latitude;

  @Nullable
  private BigDecimal longitude;

  @Nullable
  private String curationIds;

  @Nullable
  private Long materialId;

  @Nullable
  private String materialName;

  @Nullable
  private String materialContents;

  @QueryProjection
  public RestaurantDetailDto(@NotNull Long id,
      @NotNull String name,
      @Nullable String description,
      @Nullable String hashtags,
      @Nullable String thumbnailImageUrl,
      @NotNull String address,
      @Nullable Long kakaoMapId,
      @Nullable BigDecimal latitude,
      @Nullable BigDecimal longitude,
      @Nullable Long materialId,
      @Nullable String materialName,
      @Nullable String materialContents) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.hashtags = hashtags;
    this.thumbnailImageUrl = thumbnailImageUrl;
    this.address = address;
    this.kakaoMapId = kakaoMapId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.materialId = materialId;
    this.materialName = materialName;
    this.materialContents = materialContents;
  }
}
