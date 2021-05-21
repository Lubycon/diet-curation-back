package com.lubycon.eatitall.api.model.response.curation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@Getter
public class CurationDetailResponse {

  @NotNull
  private final Long curationId;

  @NotNull
  private final String title;

  @Nullable
  private final String contents;

}
