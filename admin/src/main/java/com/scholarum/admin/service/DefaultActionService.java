package com.scholarum.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.bean.Permission;
import com.scholarum.common.bean.UserPermissionBean;
import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.Hierarchy;
import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.Role;
import com.scholarum.common.entity.RoleModule;
import com.scholarum.common.entity.RolePermission;
import com.scholarum.common.exception.ScException;
import com.scholarum.common.repository.ActivityRepository;
import com.scholarum.common.repository.HierarchyRepository;
import com.scholarum.common.repository.ModuleRepository;
import com.scholarum.common.repository.RoleModuleRepository;
import com.scholarum.common.repository.RolePermissionRepository;
import com.scholarum.common.repository.RoleRepository;
import com.scholarum.common.util.CommonUtil;

@Service
public class DefaultActionService {

	@Autowired
	private HierarchyRepository hieRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ModuleRepository moduleRepo;

	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private RoleModuleRepository roleModuleRepo;

	@Autowired
	private RolePermissionRepository rolePermRepo;

	public List<Hierarchy> getHierarchies() {
		return hieRepo.findAll();
	}

	public List<Role> getRoles(Integer hierarchyId) {
		Hierarchy hierarchy = hieRepo.findOne(hierarchyId);
		if (CommonUtil.isNull(hierarchy)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Hierarchy does not exist");
		}
		return roleRepo.findByHierarchy(hierarchy);
	}

	public List<Activity> getRoleActivities(Integer roleId) {
		Role role = roleRepo.findOne(roleId);
		if (CommonUtil.isNull(role)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Role does not exist");
		}
		return activityRepo.findActivityByRole(role);
	}

	public List<Module> getModules() {
		return moduleRepo.findAll();
	}

	public List<Activity> getModuleActivities(Integer moduleId) {
		Module module = moduleRepo.findOne(moduleId);
		if (CommonUtil.isNull(module)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Module does not exist");
		}
		return activityRepo.findByModule(module);
	}

	public List<UserPermissionBean> getPermissions(Integer roleId) {
		Role role = roleRepo.findOne(roleId);
		if (CommonUtil.isNull(role)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Role does not exist");
		}
		List<UserPermissionBean> userPermList = new ArrayList<>();
		for (RoleModule roleModule : roleModuleRepo.findByRole(role)) {
			Module module = roleModule.getModule();
			UserPermissionBean userPerm = new UserPermissionBean();
			userPerm.setModule(module);
			List<RolePermission> rolePermList = new ArrayList<>();
			for (Activity activity : activityRepo.findByModule(module)) {
				RolePermission rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(-1, roleModule, activity);
				rolePermList.add(rolePerm);
			}
			userPerm.setRolePermList(rolePermList);
			userPermList.add(userPerm);
		}
		return userPermList;
	}

	public void newModule(String moduleName) {
		if (CommonUtil.isNotNull(moduleRepo.findByName(moduleName))) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Module already exists");
		}
		Module module = new Module();
		module.setName(moduleName);
		moduleRepo.save(module);
	}

	public void newActivity(Integer moduleId, String actityName) {
		Module module = moduleRepo.findOne(moduleId);
		if (CommonUtil.isNull(module)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Module does not exist");
		}
		if (CommonUtil.isNotNull(activityRepo.findByModuleAndName(module, actityName))) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Activity for this module already exists");
		}
		Activity activity = new Activity();
		activity.setModule(module);
		activity.setName(actityName);
		activityRepo.save(activity);
		for (RoleModule roleModule : roleModuleRepo.findByModule(module)) {
			RolePermission rolePerm = new RolePermission();
			rolePerm.setActivity(activity);
			rolePerm.setRoleModule(roleModule);
			rolePerm.setObjectId(-1);
			rolePermRepo.save(rolePerm);
		}
	}

	public void newRoleModule(Integer roleId, Integer moduleId) {
		Role role = roleRepo.findOne(roleId);
		if (CommonUtil.isNull(role)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Role does not exist");
		}
		Module module = moduleRepo.findOne(moduleId);
		if (CommonUtil.isNull(module)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Module does not exist");
		}
		if (CommonUtil.isNotNull(roleModuleRepo.findByRoleAndModule(role, module))) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Role Module mapping already exists");
		}
		RoleModule roleModule = new RoleModule();
		roleModule.setRole(role);
		roleModule.setModule(module);
		roleModuleRepo.save(roleModule);
		for (Activity activity : activityRepo.findByModule(module)) {
			RolePermission rolePerm = new RolePermission();
			rolePerm.setActivity(activity);
			rolePerm.setRoleModule(roleModule);
			rolePerm.setObjectId(-1);
			rolePermRepo.save(rolePerm);
		}
	}

	public void newPermission(Permission perm) {
		Role role = roleRepo.findOne(perm.getRoleId());
		if (CommonUtil.isNull(role)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Role does not exist");
		}
		Module module = moduleRepo.findOne(perm.getModuleId());
		if (CommonUtil.isNull(module)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Module does not exist");
		}
		Activity activity = activityRepo.findOne(perm.getActivityId());
		if (CommonUtil.isNull(activity)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Activity does not exist");
		}
		RoleModule roleModule = roleModuleRepo.findByRoleAndModule(role, module);
		if (CommonUtil.isNull(roleModule)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Assign Module to Role first");
		}
		RolePermission rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(-1, roleModule, activity);
		if (CommonUtil.isNull(rolePerm)) {
			rolePerm = new RolePermission();
			rolePerm.setActivity(activity);
			rolePerm.setObjectId(-1);
			rolePerm.setRoleModule(roleModule);
		}
		rolePerm.setAdd(perm.isAdd());
		rolePerm.setView(perm.isView());
		rolePerm.setEdit(perm.isEdit());
		rolePerm.setDelete(perm.isDelete());
		rolePermRepo.save(rolePerm);
	}
}
