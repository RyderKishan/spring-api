package com.eurekaconnect.api.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PingResponse {
  private String message;
  private String version;
  private LocalDateTime timeStamp;
}
