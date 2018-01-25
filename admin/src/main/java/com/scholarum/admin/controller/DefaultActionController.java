package com.scholarum.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.admin.service.DefaultActionService;
import com.scholarum.common.bean.Permission;
import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.Hierarchy;
import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.Role;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/admin/action")
public class DefaultActionController {

	@Autowired
	private DefaultActionService defActService;
	
	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/hierarchies/get", method = RequestMethod.GET)
	public List<Hierarchy> getHierarchies() {
		return defActService.getHierarchies();
	}
	
	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/roles/get", method = RequestMethod.POST)
	public List<Role> getRoles(@RequestParam("hierarchyId") Integer hierarchyId) {
		return defActService.getRoles(hierarchyId);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/role/activities/get", method = RequestMethod.POST)
	public List<Activity> getRoleActivities(@RequestParam("roleId") Integer roleId) {
		return defActService.getRoleActivities(roleId);
	}
	
	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/modules/get", method = RequestMethod.GET)
	public List<Module> getModules() {
		return defActService.getModules();
	}
	
	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/module/activities/get", method = RequestMethod.POST)
	public List<Activity> getModuleActivities(@RequestParam("moduleId") Integer moduleId) {
		return defActService.getModuleActivities(moduleId);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/module/new", method = RequestMethod.POST)
	public void newModule(@RequestParam("name") String moduleName) {
		defActService.newModule(moduleName);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/activity/new", method = RequestMethod.POST)
	public void newActivity(@RequestParam("moduleId") Integer moduleId, @RequestParam("name") String activityName) {
		defActService.newActivity(moduleId, activityName);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/roleModule/new", method = RequestMethod.POST)
	public void newRoleModule(@RequestParam("roleId") Integer roleId, @RequestParam("moduleId") Integer moduleId) {
		defActService.newRoleModule(roleId, moduleId);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping(value = "/permission/new", method = RequestMethod.POST)
	public void newPermission(@RequestBody Permission permission) {
		defActService.newPermission(permission);
	}

}
