package com.eurekaconnect.api.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "ec_organisations")
public class Organisation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  @Column(nullable = false, unique = true)
  private String domain;

  private String state;

  @JsonIgnore
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdOn;

  @JsonIgnore
  private LocalDateTime updatedOn;

  @PrePersist
  public void prePersist() {
    createdOn = LocalDateTime.now(ZoneOffset.UTC);
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }

  @PreUpdate
  public void preUpdate() {
    updatedOn = LocalDateTime.now(ZoneOffset.UTC);
  }
}
