package com.eurekaconnect.api.service;

import java.util.List;
import java.util.Optional;

import com.eurekaconnect.api.model.Organisation;
import com.eurekaconnect.api.repository.OrganisationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {
  private final OrganisationRepository organisationRepository;

  @Autowired
  public OrganisationService(OrganisationRepository organisationRepository) {
    this.organisationRepository = organisationRepository;
  }

  public Organisation save(Organisation organisation) {
    return organisationRepository.save(organisation);
  }

  public List<Organisation> saveAll(List<Organisation> organisations) {
    return organisationRepository.saveAll(organisations);
  }

  public Optional<Organisation> findById(Integer id) {
    return organisationRepository.findById(id);
  }

  public Boolean existsById(Integer id) {
    return organisationRepository.existsById(id);
  }

  public Page<Organisation> findAll(Pageable pageable) {
    return organisationRepository.findAll(pageable);
  }

  public List<Organisation> findAllById(List<Integer> ids) {
    return organisationRepository.findAllById(ids);
  }

  public long count() {
    return organisationRepository.count();
  }

  public void deleteById(Integer id) {
    organisationRepository.deleteById(id);
  }

  public void delete(Organisation organisation) {
    organisationRepository.delete(organisation);
  }

  public void deleteAll(List<Organisation> organisations) {
    organisationRepository.deleteAll(organisations);
  }

  public void deleteAll() {
    organisationRepository.deleteAll();
  }

}
