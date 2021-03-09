package com.eurekaconnect.api.service;

import java.util.List;
import java.util.Optional;

import com.eurekaconnect.api.model.Role;
import com.eurekaconnect.api.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  private final RoleRepository roleRepository;

  @Autowired
  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role save(Role role) {
    return roleRepository.save(role);
  }

  public List<Role> saveAll(List<Role> roles) {
    return roleRepository.saveAll(roles);
  }

  public Optional<Role> findById(Integer id) {
    return roleRepository.findById(id);
  }

  public Boolean existsById(Integer id) {
    return roleRepository.existsById(id);
  }

  public Page<Role> findAll(Pageable pageable) {
    return roleRepository.findAll(pageable);
  }

  public List<Role> findAllById(List<Integer> ids) {
    return roleRepository.findAllById(ids);
  }

  public long count() {
    return roleRepository.count();
  }

  public void deleteById(Integer id) {
    roleRepository.deleteById(id);
  }

  public void delete(Role role) {
    roleRepository.delete(role);
  }

  public void deleteAll(List<Role> roles) {
    roleRepository.deleteAll(roles);
  }

  public void deleteAll() {
    roleRepository.deleteAll();
  }

}
