package com.eurekaconnect.api.service;

import java.util.List;
import java.util.Optional;

import com.eurekaconnect.api.model.Organisation;
import com.eurekaconnect.api.model.User;
import com.eurekaconnect.api.repository.OrganisationRepository;
import com.eurekaconnect.api.repository.OrganisationUserRoleRepository;
import com.eurekaconnect.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final OrganisationRepository organisationRepository;
  private final OrganisationUserRoleRepository organisationUserRoleRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, OrganisationUserRoleRepository organisationUserRoleRepository,
      OrganisationRepository organisationRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.organisationUserRoleRepository = organisationUserRoleRepository;
    this.organisationRepository = organisationRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User save(User user) {
    String nPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(nPassword);
    return userRepository.save(user);
  }

  public List<User> saveAll(List<User> users) {
    return userRepository.saveAll(users);
  }

  public Optional<User> findById(Integer id) {
    return userRepository.findById(id);
  }

  public Boolean existsById(Integer id) {
    return userRepository.existsById(id);
  }

  public Page<User> findAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public List<User> findAllById(List<Integer> ids) {
    return userRepository.findAllById(ids);
  }

  public long count() {
    return userRepository.count();
  }

  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }

  public void delete(User user) {
    userRepository.delete(user);
  }

  public void deleteAll(List<User> users) {
    userRepository.deleteAll(users);
  }

  public void deleteAll() {
    userRepository.deleteAll();
  }

  @Override
  public User loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    List<Integer> orgIds = organisationUserRoleRepository.findAllOrgIdByUserId(user.getId());
    List<Organisation> organisations = organisationRepository.findAllById(orgIds);
    user.setOrganisations(organisations);
    return user;
  }

}
