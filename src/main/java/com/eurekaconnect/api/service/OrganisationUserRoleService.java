package com.eurekaconnect.api.service;

import java.util.List;
import java.util.Optional;

import com.eurekaconnect.api.model.OrganisationUserRole;
import com.eurekaconnect.api.repository.OrganisationUserRoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrganisationUserRoleService {
  private final OrganisationUserRoleRepository organisationUserRoleRepository;

  @Autowired
  public OrganisationUserRoleService(OrganisationUserRoleRepository organisationUserRoleRepository) {
    this.organisationUserRoleRepository = organisationUserRoleRepository;
  }

  public OrganisationUserRole save(OrganisationUserRole organisationUserRole) {
    return organisationUserRoleRepository.save(organisationUserRole);
  }

  public List<OrganisationUserRole> saveAll(List<OrganisationUserRole> organisationUserRoles) {
    return organisationUserRoleRepository.saveAll(organisationUserRoles);
  }

  public Optional<OrganisationUserRole> findById(Integer id) {
    return organisationUserRoleRepository.findById(id);
  }

  public Boolean existsById(Integer id) {
    return organisationUserRoleRepository.existsById(id);
  }

  public Page<OrganisationUserRole> findAll(Pageable pageable) {
    return organisationUserRoleRepository.findAll(pageable);
  }

  public List<OrganisationUserRole> findAllById(List<Integer> ids) {
    return organisationUserRoleRepository.findAllById(ids);
  }

  public long count() {
    return organisationUserRoleRepository.count();
  }

  public void deleteById(Integer id) {
    organisationUserRoleRepository.deleteById(id);
  }

  public void delete(OrganisationUserRole organisationUserRole) {
    organisationUserRoleRepository.delete(organisationUserRole);
  }

  public void deleteAll(List<OrganisationUserRole> organisationUserRoles) {
    organisationUserRoleRepository.deleteAll(organisationUserRoles);
  }

  public void deleteAll() {
    organisationUserRoleRepository.deleteAll();
  }

  public List<Integer> findOrgIdByUserId(Integer id) {
    return organisationUserRoleRepository.findAllOrgIdByUserId(id);
  }

}
