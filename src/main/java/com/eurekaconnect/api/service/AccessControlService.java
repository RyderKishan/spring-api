package com.eurekaconnect.api.service;

import java.util.List;
import java.util.Optional;

import com.eurekaconnect.api.model.AccessControl;
import com.eurekaconnect.api.repository.AccessControlRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {
  private final AccessControlRepository acccessControlRepository;

  @Autowired
  public AccessControlService(AccessControlRepository acccessControlRepository) {
    this.acccessControlRepository = acccessControlRepository;
  }

  public AccessControl save(AccessControl acccessControl) {
    return acccessControlRepository.save(acccessControl);
  }

  public List<AccessControl> saveAll(List<AccessControl> acccessControls) {
    return acccessControlRepository.saveAll(acccessControls);
  }

  public Optional<AccessControl> findById(Integer id) {
    return acccessControlRepository.findById(id);
  }

  public Boolean existsById(Integer id) {
    return acccessControlRepository.existsById(id);
  }

  public Page<AccessControl> findAll(Pageable pageable) {
    return acccessControlRepository.findAll(pageable);
  }

  public List<AccessControl> findAllById(List<Integer> ids) {
    return acccessControlRepository.findAllById(ids);
  }

  public long count() {
    return acccessControlRepository.count();
  }

  public void deleteById(Integer id) {
    acccessControlRepository.deleteById(id);
  }

  public void delete(AccessControl acccessControl) {
    acccessControlRepository.delete(acccessControl);
  }

  public void deleteAll(List<AccessControl> acccessControls) {
    acccessControlRepository.deleteAll(acccessControls);
  }

  public void deleteAll() {
    acccessControlRepository.deleteAll();
  }

}
