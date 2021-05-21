package com.lubycon.eatitall.common.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;

public class ServerException extends BaseException {

  public ServerException(String message) {
    super(message);
  }

  public HttpStatus getHttpStatus() {
    return INTERNAL_SERVER_ERROR;
  }

}
