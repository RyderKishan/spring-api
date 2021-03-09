package com.eurekaconnect.api.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.eurekaconnect.api.model.Organisation;
import com.eurekaconnect.api.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

  @Value("${app.jwtExpirationMins}")
  private int jwtExpirationMins;

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  private Claims getClaimsBody(String token) {
    Claims claims;
    try {
      claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }

  public String generateToken(User user, Organisation organisation) {

    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("id", String.valueOf(user.getId()));
    claims.put("password", String.valueOf(user.getPassword()));
    claims.put("orgId", String.valueOf(organisation.getId()));

    Date validity = new Date(System.currentTimeMillis() + 1000 * 60 * jwtExpirationMins);

    return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(validity)
        .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
  }

  public boolean validateToken(String token) {
    Claims claims = getClaimsBody(token);
    return !(token.isBlank() || token.isEmpty() || claims.getExpiration().before(new Date()));
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      String token = bearerToken.substring(7, bearerToken.length());
      if (token.isEmpty() || token.isBlank() || token.equals("null") || token.equals("undefined"))
        return null;
      return token;
    }
    return null;
  }

  public Authentication getAuthentication(String token) {
    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    Claims claims = getClaimsBody(token);
    // authorities.add(new SimpleGrantedAuthority("API"));
    Integer id = Integer.parseInt((String) claims.get("id"));
    String userName = claims.getSubject();
    String password = (String) claims.get("password");
    User user = new User();
    user.setId(id);
    user.setEmail(userName);
    user.setPassword(password);
    return new UsernamePasswordAuthenticationToken(user, password, authorities);
  }
}
