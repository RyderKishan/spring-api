package com.eurekaconnect.api.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "ec_users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  private String authGroup;
  @Column(nullable = false)
  private String orgId;
  @Column(unique = true, nullable = false)
  private String userName;


  private Boolean isVerified;
  private Boolean active;

  @JsonIgnore
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdOn;
  @JsonIgnore
  private LocalDateTime updatedOn;

  @PrePersist
  public void prePersist() {
    active = true;
    isVerified = false;
    authGroup = "user";
    createdOn = LocalDateTime.now(ZoneOffset.UTC);
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }

  @PreUpdate
  public void preUpdate() {
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }
}