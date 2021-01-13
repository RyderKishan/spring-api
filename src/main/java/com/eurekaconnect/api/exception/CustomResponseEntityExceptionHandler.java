package com.eurekaconnect.api.exception;

import com.eurekaconnect.api.model.ErrorDetail;
import com.eurekaconnect.api.utils.Utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ErrorDetail> handleBadRequestException(BadRequestException exception,
      WebRequest request) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, request);
  }

  @ExceptionHandler(NoContentException.class)
  public final ResponseEntity<ErrorDetail> handleNoContentException(NoContentException exception, WebRequest request) {
    return buildErrorResponse(HttpStatus.NO_CONTENT, exception, request);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public final ResponseEntity<ErrorDetail> handleUnauthorizedException(UnauthorizedException exception,
      WebRequest request) {
    return buildErrorResponse(HttpStatus.UNAUTHORIZED, exception, request);
  }

  @ExceptionHandler(TransactionSystemException.class)
  public final ResponseEntity<ErrorDetail> handleMethodArgumentTypeMismatchException(
      TransactionSystemException exception, WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail("E500003", exception.getMessage(), exception.getClass().getName(),
        exception.getMostSpecificCause().getMessage(), request.getDescription(false), request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public final ResponseEntity<ErrorDetail> handleEmptyResultDataAccessException(
      EmptyResultDataAccessException exception, WebRequest request) {
    String message = exception.getMessage();
    ErrorDetail errorDetail = new ErrorDetail("E500004", message.replaceFirst("com.eurekaconnect.api.model.", ""),
        exception.getClass().getName(), exception.getMostSpecificCause().getMessage(), request.getDescription(false),
        request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public final ResponseEntity<ErrorDetail> handleInvalidDataAccessApiUsageException(
      InvalidDataAccessApiUsageException exception, WebRequest request) {
    String message = exception.getMessage();
    ErrorDetail errorDetail = new ErrorDetail("E500005", message.replaceFirst("com.eurekaconnect.api.model.", ""),
        exception.getClass().getName(), exception.getMostSpecificCause().getMessage(), request.getDescription(false),
        request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(PropertyReferenceException.class)
  public final ResponseEntity<ErrorDetail> handlePropertyReferenceException(PropertyReferenceException exception,
      WebRequest request) {
    String message = exception.getMessage();
    ErrorDetail errorDetail = new ErrorDetail("E500006", message, exception.getClass().getName(), message,
        request.getDescription(false), request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public final ResponseEntity<ErrorDetail> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException exception, WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail("E400018", "Invalid Arguments", exception.getClass().getName(),
        exception.getLocalizedMessage(), request.getDescription(false), request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public final ResponseEntity<ErrorDetail> handleIllegalArgumentException(IllegalArgumentException exception,
      WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail("E500001", "Illegal Arguments", exception.getClass().getName(),
        exception.getLocalizedMessage(), request.getDescription(false), request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public final ResponseEntity<ErrorDetail> handleDataIntegrityViolationException(
      DataIntegrityViolationException exception, WebRequest request) {
    String message = exception.getRootCause().getMessage();
    String value = Utils.getErrorMessageByPattern(message, "\\'(.*?)\\'");
    StringBuilder sb = new StringBuilder(value);
    if (!value.isBlank()) {
      sb.append(" Already Exists");
    } else {
      sb.append(message.replaceFirst("com.eurekaconnect.api.model.", ""));
    }
    ErrorDetail errorDetail = new ErrorDetail("E500002", sb.toString(), exception.getClass().getName(),
        exception.getLocalizedMessage(), request.getDescription(false), request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ArithmeticException.class)
  public final ResponseEntity<ErrorDetail> handleArithmeticException(ArithmeticException exception,
      WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail("ER_500_001", exception.getMessage(), request.getDescription(false),
        exception.getClass().getName(), exception.getLocalizedMessage(), request.getSessionId());
    return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorDetail> buildErrorResponse(HttpStatus httpStatus, CustomException exception,
      WebRequest webRequest) {

    ErrorDetail errorDetail = new ErrorDetail(exception.getErrorCode(), exception.getErrorMessage(),
        exception.getClass().getName(), exception.getMessage(), webRequest.getDescription(false),
        webRequest.getSessionId());
    return new ResponseEntity<>(errorDetail, httpStatus);
  }
}
