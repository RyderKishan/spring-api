package com.eurekaconnect.api.controller;

import com.eurekaconnect.api.model.AuthResponse;
import com.eurekaconnect.api.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/auth")
public class AuthController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestHeader String email, @RequestHeader String password,
      @RequestHeader String orgId) {
    LOGGER.info("login :: email {} orgId {}", email, orgId);
    AuthResponse authResponse = authService.usernamePasswordAuthenticationToken(email, password, orgId);
    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }

}