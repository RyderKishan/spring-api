package com.eurekaconnect.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.eurekaconnect.api.exception.NoContentException;
import com.eurekaconnect.api.model.Organisation;
import com.eurekaconnect.api.model.PingResponse;
import com.eurekaconnect.api.service.OrganisationService;

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
@RequestMapping(value = "/organisations")
public class OrganisationController {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrganisationController.class);

  private final OrganisationService organisationService;

  @Autowired
  public OrganisationController(OrganisationService organisationService) {
    this.organisationService = organisationService;
  }

  @PostMapping("/")
  public ResponseEntity<Organisation> saveOrganisation(@RequestBody Organisation organisation) {
    LOGGER.info("saveOrganisation :: id {}", organisation.getId());
    Organisation savedOrganisation = organisationService.save(organisation);
    return new ResponseEntity<>(savedOrganisation, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Organisation> findOrganisationById(@PathVariable Integer id) {
    LOGGER.info("findOrganisationById :: id {}", id);
    Organisation organisation = organisationService.findById(id).orElseThrow(() -> new NoContentException("Organisation not found", "E204024"));
    return new ResponseEntity<>(organisation, HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity<Page<Organisation>> findAllOrganisations(Pageable pageable) {
    LOGGER.info("findAllOrganisations :: page {} size {}", pageable.getPageNumber(), pageable.getPageSize());
    Page<Organisation> organisation = organisationService.findAll(pageable);
    Optional.ofNullable(organisation).orElseThrow(() -> new NoContentException("Organisation list empty", "E204009"));
    return new ResponseEntity<>(organisation, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteOrganisationById(@PathVariable Integer id) {
    LOGGER.info("deleteOrganisationById :: id {}", id);
    organisationService.deleteById(id);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @GetMapping("/ping")
  public ResponseEntity<PingResponse> pingOrganisation() {
    LOGGER.info("pingOrganisation");
    PingResponse pingResponse = new PingResponse();
    pingResponse.setMessage("OrganisationController is up and running!");
    pingResponse.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
    return new ResponseEntity<>(pingResponse, HttpStatus.OK);
  }
}