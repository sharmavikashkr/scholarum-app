package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.UserRole;
import com.scholarum.common.type.Role;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

	public List<UserRole> findByRole(Role role);

}
