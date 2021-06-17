package com.lubycon.eatitall.api.model.response.restaurant;

import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurationRestaurantResponse {

  @NotNull
  private Long curationId;

  @NotNull
  private Long id;

  @NotNull
  private String name;

  @Nullable
  private String description;

  @Nullable
  private String[] hashtags;

  @Nullable
  private String thumbnailImageUrl;

  @NotNull
  private String address;

  @Nullable
  private KakaoMap kakaoMap;
}
