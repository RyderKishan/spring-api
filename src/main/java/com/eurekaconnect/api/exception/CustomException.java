package com.eurekaconnect.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String errorMessage;
  private final String errorCode;

  public CustomException(String errorMessage, String errorCode) {
    super();
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }
}
