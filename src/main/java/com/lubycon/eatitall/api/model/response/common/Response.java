package com.lubycon.eatitall.api.model.response.common;

import static org.springframework.http.HttpStatus.OK;

import com.lubycon.eatitall.common.exception.BaseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Response {

  private final int code;
  private final boolean success;
  private final String message;
  private final Date timestamp;
  private final Map<String, Object> data;
  private Error error;

  public Response() {
    this(OK);
  }

  public Response(HttpStatus httpStatus) {
    this.code = httpStatus.value();
    this.success = !httpStatus.isError();
    this.message = httpStatus.getReasonPhrase();
    this.timestamp = new Date();
    this.data = new HashMap<>();
  }

  public Response(String key, Object result) {
    this(key, result, OK);
  }

  public Response(String key, Object result, HttpStatus status) {
    this(status);
    this.add(key, result);
  }

  public void add(String key, Object result) {
    this.data.put(key, result);
  }

  public Response(BaseException exception, String referredUrl) {
    this(exception.getHttpStatus());
    this.error = new Error(code, exception.getMessage(), referredUrl);
  }

}

