package com.eurekaconnect.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.eurekaconnect.api.exception.NoContentException;
import com.eurekaconnect.api.model.Role;
import com.eurekaconnect.api.model.PingResponse;
import com.eurekaconnect.api.service.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/roles")
public class RoleController {
  private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

  private final RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping("/")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    LOGGER.info("saveRole :: id {}", role.getId());
    Role savedRole = roleService.save(role);
    return new ResponseEntity<>(savedRole, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Role> findRoleById(@PathVariable Integer id) {
    LOGGER.info("findRoleById :: id {}", id);
    Role role = roleService.findById(id).orElseThrow(() -> new NoContentException("Role not found", "E204024"));
    return new ResponseEntity<>(role, HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity<Page<Role>> findAllRoles(Pageable pageable) {
    LOGGER.info("findAllRoles :: page {} size {}", pageable.getPageNumber(), pageable.getPageSize());
    Page<Role> role = roleService.findAll(pageable);
    Optional.ofNullable(role).orElseThrow(() -> new NoContentException("Role list empty", "E204009"));
    return new ResponseEntity<>(role, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteRoleById(@PathVariable Integer id) {
    LOGGER.info("deleteRoleById :: id {}", id);
    roleService.deleteById(id);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @GetMapping("/ping")
  public ResponseEntity<PingResponse> pingRole() {
    LOGGER.info("pingRole");
    PingResponse pingResponse = new PingResponse();
    pingResponse.setMessage("RoleController is up and running!");
    pingResponse.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
    return new ResponseEntity<>(pingResponse, HttpStatus.OK);
  }
}