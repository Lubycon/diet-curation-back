package com.lubycon.eatitall.api.model.response.curation;

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
public class CurationDetailResponse {

  @NotNull
  private Long id;

  @NotNull
  private String title;

  @Nullable
  private String contents;

}
