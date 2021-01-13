package com.eurekaconnect.api.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

  public static LocalDateTime getCurrentUTCLocalDateTime() {
    return LocalDateTime.now(ZoneOffset.UTC);
  }

  public static LocalDateTime plusHours(LocalDateTime date, long noOfHours) {
    return date.plusHours(noOfHours);
  }

  public static LocalDateTime minusHours(LocalDateTime date, long noOfHours) {
    return date.minusHours(noOfHours);
  }

  public static LocalDateTime plusDays(LocalDateTime date, long noOfDays) {
    return date.plusDays(noOfDays);
  }

  public static LocalDateTime minusDays(LocalDateTime date, long noOfDays) {
    return date.minusDays(noOfDays);
  }

  public static LocalDateTime minusYears(LocalDateTime date, long noOfYears) {
    return date.minusYears(noOfYears);
  }

  public static LocalDateTime plusYears(LocalDateTime date, long noOfYears) {
    return date.plusYears(noOfYears);
  }

  public static long timeTo(LocalDateTime date, TemporalUnit temporalUnit) {
    LocalDateTime current = LocalDateTime.now(ZoneOffset.UTC);
    return current.until(date, temporalUnit);
  }

  public static String formatLocalDateTimeToISOString(LocalDateTime date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    return formatter.format(date).concat(".000Z");
  }

}
