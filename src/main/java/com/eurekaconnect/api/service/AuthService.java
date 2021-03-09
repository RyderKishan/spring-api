package com.eurekaconnect.api.service;

import com.eurekaconnect.api.exception.UnauthorizedException;
import com.eurekaconnect.api.model.AuthResponse;
import com.eurekaconnect.api.model.Organisation;
import com.eurekaconnect.api.model.User;
import com.eurekaconnect.api.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;

  @Autowired
  public AuthService(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  public AuthResponse usernamePasswordAuthenticationToken(String email, String password, String orgId) {
    try {
      Integer orgIdInteger = Integer.parseInt(orgId);
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      User user = userService.loadUserByUsername(email);
      Organisation organisation = user.getOrganisations().stream().filter(org -> orgIdInteger == org.getId()).findAny()
          .orElse(null);
      String token = jwtTokenUtil.generateToken(user, organisation);
      AuthResponse authResponse = new AuthResponse();
      authResponse.setToken(token);
      return authResponse;
    } catch (Exception e) {
      throw new UnauthorizedException("Org InActive", "E401006");
    }
  }

}
