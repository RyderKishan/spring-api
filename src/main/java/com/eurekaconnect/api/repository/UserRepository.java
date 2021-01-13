package com.eurekaconnect.api.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.eurekaconnect.api.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

  Page<User> findAllByOrgId(Pageable pageable, String orgId);

  Optional<User> findByIdAndOrgId(Integer id, String orgId);

  Optional<User> findUserByUserName(String userName);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "UPDATE ec_users SET password_hash = :passwordHash WHERE id = :id and org_id = :orgId", nativeQuery = true)
  Integer updatePassword(@Param("id") Integer id, @Param("passwordHash") String passwordHash,
      @Param("orgId") String orgId);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "DELETE FROM ec_users WHERE id = :id AND org_id = :orgId", nativeQuery = true)
  Integer deleteByIdAndOrgId(Integer id, String orgId);

}
