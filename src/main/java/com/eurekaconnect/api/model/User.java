package com.eurekaconnect.api.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "ec_users")
public class User implements UserDetails {
  /**
   *
   */
  private static final long serialVersionUID = 179587349769383710L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(unique = true, nullable = false)
  private String username;
  private String password;

  private Boolean accountNonExpired;
  private Boolean accountNonLocked;
  private Boolean credentialsNonExpired;
  private Boolean enabled;

  @JsonIgnore
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdOn;
  @JsonIgnore
  private LocalDateTime updatedOn;

  @PrePersist
  public void prePersist() {
    accountNonExpired = true;
    accountNonLocked = true;
    credentialsNonExpired = true;
    enabled = true;
    createdOn = LocalDateTime.now(ZoneOffset.UTC);
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }

  @PreUpdate
  public void preUpdate() {
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ADMIN"));
    return authorities;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public String getPassword() {
    return password;
  }
}