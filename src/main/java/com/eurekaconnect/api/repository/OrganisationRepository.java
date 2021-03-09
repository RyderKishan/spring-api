package com.eurekaconnect.api.repository;

import com.eurekaconnect.api.model.Organisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Integer>, JpaSpecificationExecutor<Organisation> {

}
