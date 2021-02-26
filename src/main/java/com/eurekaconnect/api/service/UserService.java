package com.eurekaconnect.api.service;

import java.util.List;
import java.util.Optional;

import com.eurekaconnect.api.model.User;
import com.eurekaconnect.api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User save(User user) {
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
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
    return user;
  }

}
