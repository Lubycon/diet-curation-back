package com.lubycon.eatitall.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@Getter
public class MenuDto {

  @NotNull
  private final Long menuId;

  @NotNull
  private final Long restaurantId;

  @NotNull
  private final String name;

  @Nullable
  private final String description;

  @NotNull
  private final Integer price;

}
