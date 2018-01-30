package com.scholarum.common.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Role;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.type.RoleType;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

	public List<UserRole> findByRole(RoleType role);

	public UserRole findByUserAndRole(ScUser user, Role role);

	@Transactional
	@Modifying
	@Query("DELETE FROM UserRole WHERE id = ?1")
	public void deleteUserRole(Integer id);

}
