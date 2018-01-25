package com.scholarum.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.bean.Permission;
import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.Role;
import com.scholarum.common.util.RoleUtil;
import com.scholarum.dashboard.service.AdminActionService;

@RestController
@RequestMapping("/action")
public class AdminActionController {

	@Autowired
	private AdminActionService adminActSer;

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping(value = "/roles/get", method = RequestMethod.GET)
	public List<Role> getRoles() {
		return adminActSer.getRoles();
	}

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping(value = "/activities/get", method = RequestMethod.POST)
	public List<Activity> getActivities(@RequestParam("roleId") Integer roleId) {
		return adminActSer.getActivities(roleId);
	}

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping(value = "/permission/new", method = RequestMethod.POST)
	public void newPermission(@RequestBody Permission permission) {
		adminActSer.newPermission(permission);
	}

}
