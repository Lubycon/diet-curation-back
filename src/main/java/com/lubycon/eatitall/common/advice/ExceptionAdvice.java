package com.lubycon.eatitall.common.advice;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_BAD_REQUEST;
import static com.lubycon.eatitall.common.util.MessageUtils.MSG_INTERNAL_SERVER_ERROR;
import static com.lubycon.eatitall.common.util.MessageUtils.MSG_NOT_FOUND;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.common.exception.InvalidRequestException;
import com.lubycon.eatitall.common.exception.NotFoundException;
import com.lubycon.eatitall.common.exception.ServerException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Response> serverExceptionResponse(HttpServletRequest request,
      Exception exception) {
    String errorMessage = exception.getMessage() == null ?
        MSG_INTERNAL_SERVER_ERROR : exception.getMessage();

    log.error("ServerException : {}", errorMessage);
    exception.printStackTrace();

    Response response = new Response(new ServerException(errorMessage), request.getRequestURL().toString());
    return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Response> illegalArgumentExceptionResponse(HttpServletRequest request,
      IllegalArgumentException exception) {
    String errorMessage = exception.getMessage() == null ?
        MSG_BAD_REQUEST : exception.getMessage();

    log.error("IllegalArgumentException : {}", errorMessage);
    exception.printStackTrace();

    Response response = new Response(new InvalidRequestException(errorMessage), request.getRequestURL().toString());
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Response> notFoundExceptionResponse(HttpServletRequest request,
      NotFoundException exception) {
    String errorMessage = exception.getMessage() == null ?
        MSG_NOT_FOUND : exception.getMessage();

    log.error("NotFoundException : {}", errorMessage);
    exception.printStackTrace();

    Response response = new Response(new NotFoundException(errorMessage), request.getRequestURL().toString());
    return new ResponseEntity(response, HttpStatus.NOT_FOUND);
  }

}
