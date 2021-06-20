package com.lubycon.eatitall.domain.material.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
@Getter
@Setter
public class MaterialDto {

  @Nullable
  private Long id;

  @Nullable
  private String name;

  @Nullable
  private String contents;

  @Builder
  public MaterialDto(@Nullable Long id, @Nullable String name,
      @Nullable String contents) {
    this.id = id;
    this.name = name;
    this.contents = contents;
  }
}
