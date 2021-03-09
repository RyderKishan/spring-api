package com.eurekaconnect.api.repository;

import java.util.List;

import com.eurekaconnect.api.model.OrganisationUserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationUserRoleRepository
        extends JpaRepository<OrganisationUserRole, Integer>, JpaSpecificationExecutor<OrganisationUserRole> {

    @Query(value = "SELECT org_id FROM ec_organisations_users_roles WHERE user_id = :userId", nativeQuery = true)
    List<Integer> findAllOrgIdByUserId(@Param("userId") Integer userId);

}
