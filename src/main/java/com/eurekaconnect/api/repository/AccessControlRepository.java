package com.eurekaconnect.api.repository;

import com.eurekaconnect.api.model.AccessControl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessControlRepository extends JpaRepository<AccessControl, Integer>, JpaSpecificationExecutor<AccessControl> {

}
