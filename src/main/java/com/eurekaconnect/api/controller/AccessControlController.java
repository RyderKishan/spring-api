package com.eurekaconnect.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.eurekaconnect.api.exception.NoContentException;
import com.eurekaconnect.api.model.AccessControl;
import com.eurekaconnect.api.model.PingResponse;
import com.eurekaconnect.api.service.AccessControlService;

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
@RequestMapping(value = "/accesscontrols")
public class AccessControlController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AccessControlController.class);

  private final AccessControlService accessControlService;

  @Autowired
  public AccessControlController(AccessControlService accessControlService) {
    this.accessControlService = accessControlService;
  }

  @PostMapping("/")
  public ResponseEntity<AccessControl> saveAccessControl(@RequestBody AccessControl accessControl) {
    LOGGER.info("saveAccessControl :: id {}", accessControl.getId());
    AccessControl savedAccessControl = accessControlService.save(accessControl);
    return new ResponseEntity<>(savedAccessControl, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccessControl> findAccessControlById(@PathVariable Integer id) {
    LOGGER.info("findAccessControlById :: id {}", id);
    AccessControl accessControl = accessControlService.findById(id)
        .orElseThrow(() -> new NoContentException("AccessControl not found", "E204024"));
    return new ResponseEntity<>(accessControl, HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity<Page<AccessControl>> findAllAccessControls(Pageable pageable) {
    LOGGER.info("findAllAccessControls :: page {} size {}", pageable.getPageNumber(), pageable.getPageSize());
    Page<AccessControl> accessControl = accessControlService.findAll(pageable);
    Optional.ofNullable(accessControl).orElseThrow(() -> new NoContentException("AccessControl list empty", "E204009"));
    return new ResponseEntity<>(accessControl, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteAccessControlById(@PathVariable Integer id) {
    LOGGER.info("deleteAccessControlById :: id {}", id);
    accessControlService.deleteById(id);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @GetMapping("/ping")
  public ResponseEntity<PingResponse> pingAccessControl() {
    LOGGER.info("pingAccessControl");
    PingResponse pingResponse = new PingResponse();
    pingResponse.setMessage("AccessControlController is up and running!");
    pingResponse.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
    return new ResponseEntity<>(pingResponse, HttpStatus.OK);
  }
}