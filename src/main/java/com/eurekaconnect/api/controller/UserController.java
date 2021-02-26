package com.eurekaconnect.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.eurekaconnect.api.exception.NoContentException;
import com.eurekaconnect.api.model.User;
import com.eurekaconnect.api.model.PingResponse;
import com.eurekaconnect.api.service.UserService;

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
@RequestMapping(value = "/users")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/")
  public ResponseEntity<User> saveUser(@RequestBody User user) {
    LOGGER.info("saveUser :: username {}", user.getUsername());
    User savedUser = userService.save(user);
    return new ResponseEntity<>(savedUser, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findUserById(@PathVariable Integer id) {
    LOGGER.info("findUserById :: id {}", id);
    User user = userService.findById(id).orElseThrow(() -> new NoContentException("User not found", "E204024"));
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity<Page<User>> findAllUsers(Pageable pageable) {
    LOGGER.info("findAllUsers :: page {} size {}", pageable.getPageNumber(), pageable.getPageSize());
    Page<User> user = userService.findAll(pageable);
    Optional.ofNullable(user).orElseThrow(() -> new NoContentException("User list empty", "E204009"));
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteUserById(@PathVariable Integer id) {
    LOGGER.info("deleteUserById :: id {}", id);
    userService.deleteById(id);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @GetMapping("/ping")
  public ResponseEntity<PingResponse> pingUser() {
    LOGGER.info("pingUser");
    PingResponse pingResponse = new PingResponse();
    pingResponse.setMessage("UserController is up and running!");
    pingResponse.setTimeStamp(LocalDateTime.now(ZoneOffset.UTC));
    return new ResponseEntity<>(pingResponse, HttpStatus.OK);
  }
}