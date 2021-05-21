package com.lubycon.eatitall.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

  public BaseException(String errorMessage) {
    super(errorMessage);
  }

  public abstract HttpStatus getHttpStatus();

}
