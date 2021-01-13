package com.eurekaconnect.api.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorDetail {
  private LocalDateTime timestamp;
  private String errorCode;
  private String errorMessage;
  private String exceptionName;
  private String url;
  private String sessionId;
  private String description;

  public ErrorDetail() {
    super();
  }

  public ErrorDetail(String errorCode, String errorMessage, String exceptionName,
      String description, String url, String sessionId) {
    this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.exceptionName = exceptionName;
    this.description = description;
    this.url = url;
    this.sessionId = sessionId;
  }

  public Map<String, Object> toMap() {
    Map<String, Object> data = new HashMap<>();
    data.put("timestamp", timestamp.toString());
    data.put("errorCode", errorCode);
    data.put("errorMessage", errorMessage);
    data.put("exceptionName", exceptionName);
    data.put("description", description);
    data.put("url", url);
    data.put("sessionId", sessionId);
    return data;
  }
}
