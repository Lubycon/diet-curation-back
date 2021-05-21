package com.lubycon.eatitall.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BaseException {

  public InvalidRequestException(String message) {
    super(message);
  }

  public HttpStatus getHttpStatus() {
    return BAD_REQUEST;
  }

}

