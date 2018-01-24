package com.scholarum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.RoleModule;
import com.scholarum.common.entity.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

	public RolePermission findByObjectIdAndRoleModuleAndActivity(Integer objectId, RoleModule roleModule,
			Activity activity);

}
