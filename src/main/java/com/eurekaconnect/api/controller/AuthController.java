package com.eurekaconnect.api.controller;

import com.eurekaconnect.api.model.User;
import com.eurekaconnect.api.security.JwtTokenUtil;
import com.eurekaconnect.api.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

  private final UserService userService;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestHeader String email, @RequestHeader String password) {
    LOGGER.info("login :: email {} password {}", email, password);

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    User user = userService.loadUserByUsername(email);
    String token = jwtTokenUtil.generateToken(user);
    return new ResponseEntity<>(token, HttpStatus.OK);
  }

}