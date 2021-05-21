package com.lubycon.eatitall.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

  public NotFoundException(String message) {
    super(message);
  }

  public HttpStatus getHttpStatus() {
    return NOT_FOUND;
  }

}

