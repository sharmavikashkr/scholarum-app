package com.scholarum.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.admin.service.DefaultActionService;
import com.scholarum.common.bean.Permission;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/admin/action")
public class DefaultActionController {

	@Autowired
	private DefaultActionService defActionService;

	@PreAuthorize(RoleUtil.SCHOLARUM_AUTH)
	@RequestMapping(value = "/module/new", method = RequestMethod.POST)
	public void newModule(@RequestParam("name") String moduleName) {
		defActionService.newModule(moduleName);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_AUTH)
	@RequestMapping(value = "/activity/new", method = RequestMethod.POST)
	public void newActivity(@RequestParam("moduleId") Integer moduleId, @RequestParam("name") String activityName) {
		defActionService.newActivity(moduleId, activityName);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_AUTH)
	@RequestMapping(value = "/roleModule/new", method = RequestMethod.POST)
	public void newRoleModule(@RequestParam("roleId") Integer roleId, @RequestParam("moduleId") Integer moduleId) {
		defActionService.newRoleModule(roleId, moduleId);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_AUTH)
	@RequestMapping(value = "/permission/new", method = RequestMethod.POST)
	public void newPermission(@RequestBody Permission permission) {
		defActionService.newPermission(permission);
	}

}
