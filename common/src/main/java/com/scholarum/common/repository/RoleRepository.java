package com.scholarum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Role;
import com.scholarum.common.type.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Role findByName(RoleType name);

}
