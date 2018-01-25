package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.Role;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	public List<Activity> findByModule(Module module);

	public Activity findByModuleAndName(Module module, String name);

	@Query("SELECT a FROM Activity a WHERE a.module in (SELECT rm.module FROM RoleModule rm WHERE rm.role = ?1)")
	public List<Activity> findActivityByRole(Role role);
}
