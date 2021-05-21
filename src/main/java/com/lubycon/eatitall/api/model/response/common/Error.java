package com.lubycon.eatitall.api.model.response.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Error {

  private final int code;
  private final String errorMessage;
  private final String referredUrl;

  @Builder
  public Error(int code, String errorMessage, String referredUrl) {
    this.code = code;
    this.errorMessage = errorMessage;
    this.referredUrl = referredUrl;
  }

}
