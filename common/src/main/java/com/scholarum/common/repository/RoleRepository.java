package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Hierarchy;
import com.scholarum.common.entity.Role;
import com.scholarum.common.type.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Role findByName(RoleType name);

	public List<Role> findByHierarchy(Hierarchy hierarchy);

}
