package com.eurekaconnect.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.eurekaconnect.api.exception.NoContentException;
import com.eurekaconnect.api.model.OrganisationUserRole;
import com.eurekaconnect.api.model.PingResponse;
import com.eurekaconnect.api.service.OrganisationUserRoleService;

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
@RequestMapping(value = "/organisationuserroles")
public class OrganisationUserRoleController {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrganisationUserRoleController.class);

  private final OrganisationUserRoleService organisationUserRoleService;

  @Autowired
  public OrganisationUserRoleController(OrganisationUserRoleService organisationUserRoleService) {
    this.organisationUserRoleService = organisationUserRoleService;
  }

  @PostMapping("/")
  public ResponseEntity<OrganisationUserRole> saveOrganisationUserRole(
      @RequestBody OrganisationUserRole organisationUserRole) {
    LOGGER.info("saveOrganisationUserRole :: id {}", organisationUserRole.getId());
    OrganisationUserRole savedOrganisationUserRole = organisationUserRoleService.save(organisationUserRole);
    return new ResponseEntity<>(savedOrganisationUserRole, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrganisationUserRole> findOrganisationUserRoleById(@PathVariable Integer id) {
    LOGGER.info("findOrganisationUserRoleById :: id {}", id);
    OrganisationUserRole organisationUserRole = organisationUserRoleService.findById(id)
        .orElseThrow(() -> new NoContentException("OrganisationUserRole not found", "E204024"));
    return new ResponseEntity<>(organisationUserRole, HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity<Page<OrganisationUserRole>> findAllOrganisationUserRoles(Pageable pageable) {
    LOGGER.info("findAllOrganisationUserRoles :: page {} size {}", pageable.getPageNumber(), pageable.getPageSize());
    Page<OrganisationUserRole> organisationUserRole = organisationUserRoleService.findAll(pageable);
    Optional.ofNullable(organisationUserRole)
        .orElseThrow(() -> new NoContentException("OrganisationUserRole list empty", "E204009"));
    return new ResponseEntity<>(organisationUserRole, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteOrganisationUserRoleById(@PathVariable Integer id) {
    LOGGER.info("deleteOrganisationUserRoleById :: id {}", id);
    organisationUserRoleService.deleteById(id);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @GetMapping("/ping")
  public ResponseEntity<PingResponse> pingOrganisationUserRole() {
    LOGGER.info("pingOrganisationUserRole");
    PingResponse pingResponse = new PingResponse();
    pingResponse.setMessage("OrganisationUserRoleController is up and running!");
    pingResponse.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
    return new ResponseEntity<>(pingResponse, HttpStatus.OK);
  }
}