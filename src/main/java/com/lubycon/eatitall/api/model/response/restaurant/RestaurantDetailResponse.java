package com.lubycon.eatitall.api.model.response.restaurant;

import com.lubycon.eatitall.domain.curation.dto.CurationDto;
import com.lubycon.eatitall.domain.menu.dto.MenuDto;
import com.lubycon.eatitall.domain.restaurant.model.Address;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import java.util.List;
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
public class RestaurantDetailResponse {

  @NotNull
  private Long restaurantId;

  @NotNull
  private String name;

  @Nullable
  private String description;

  @Nullable
  private String hashtags;

  @Nullable
  private String thumbnailImageUrl;

  @NotNull
  private Address address;

  @Nullable
  private KakaoMap kakaoMap;

  @Nullable
  private List<CurationDto> curations;

  @Nullable
  private List<MenuDto> menus;

}
