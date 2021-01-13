package com.eurekaconnect.api.utils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Utils {
  public static String decode(String encodedString) {
    try {
      return new String(Base64.getDecoder().decode(encodedString));
    } catch (Exception e) {
      return encodedString;
    }
  }

  public static String encodeString(String inputString) {
    try {
      return Base64.getEncoder().encodeToString(inputString.getBytes());
    } catch (Exception e) {
      return inputString;
    }
  }

  public static String getErrorMessageByPattern(String inputString, String regex) {
    if (inputString.isBlank() || regex.isBlank())
      return "";
    List<String> foundValues = new ArrayList<String>();
    try {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(inputString);
      while (matcher.find()) {
        String s = matcher.group(1);
        foundValues.add(s);
      }
      if (foundValues.size() > 0)
        return foundValues.get(0);
      return "";
    } catch (Exception e) {
      return "";
    }
  }
}