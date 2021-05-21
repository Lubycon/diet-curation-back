package com.lubycon.eatitall.domain.curation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class CurationDto {

  @NotNull
  private final Long curationId;

  @NotNull
  private final String title;

  @QueryProjection
  public CurationDto(@NotNull Long curationId,
      @NotNull String title) {
    this.curationId = curationId;
    this.title = title;
  }
}
