package com.lubycon.eatitall.api.model.response.curation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
public class CurationResponse {

  @NotNull
  private final Long id;

  @NotNull
  private final String title;

}
