package com.eurekaconnect.api.exception;

import java.util.ArrayList;
import java.util.List;

import com.eurekaconnect.api.model.ErrorDetail;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
      WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail("E400020", "Method Arguments Invalid", exception.getClass().getName(),
        exception.getMessage(), request.getDescription(false), request.getSessionId());
    return errorDetail;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(MissingRequestHeaderException.class)
  public ErrorDetail handleMissingRequestHeaderException(MissingRequestHeaderException exception, WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail("E400023", "Missing Header: " + exception.getHeaderName(),
        exception.getClass().getName(), exception.getMessage(), request.getDescription(false), request.getSessionId());
    return errorDetail;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ErrorDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException exception,
      WebRequest request) {
    List<String> errors = new ArrayList<String>();
    errors.add("Request Body Missing");
    ErrorDetail errorDetail = new ErrorDetail("E400021", "Request Unreadable", exception.getClass().getName(),
        exception.getMessage(), request.getDescription(false), request.getSessionId());
    return errorDetail;
  }
}