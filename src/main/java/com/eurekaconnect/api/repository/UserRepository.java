package com.eurekaconnect.api.repository;

import java.util.Optional;

import com.eurekaconnect.api.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

  Optional<User> findUserByUsername(String username);

}
