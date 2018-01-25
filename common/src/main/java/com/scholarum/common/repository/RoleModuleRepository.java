package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.Role;
import com.scholarum.common.entity.RoleModule;

@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModule, Integer> {

	public List<RoleModule> findByRole(Role role);

	public List<RoleModule> findByModule(Module module);

	public RoleModule findByRoleAndModule(Role role, Module module);

	@Query("SELECT rm.module FROM RoleModule rm WHERE rm.role = ?1")
	public List<Module> findModuleByRole(Role role);

}
