package com.scholarum.dashboard.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.bean.UserPermissionBean;
import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.util.RoleUtil;
import com.scholarum.dashboard.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SecurityService secSer;

	@Autowired
	private UserService userSer;

	@PreAuthorize(RoleUtil.ALL_AUTH)
	@RequestMapping("/get")
	public ScUser getUser() {
		return secSer.findLoggedInUser();
	}

	@PreAuthorize(RoleUtil.ALL_AUTH)
	@RequestMapping("/modules/get")
	public Set<Module> getUserModule() {
		return userSer.getUserModules();
	}

	@PreAuthorize(RoleUtil.ALL_AUTH)
	@RequestMapping("/permissions/get/{moduleId}")
	public UserPermissionBean getUserPermissions(@PathVariable Integer moduleId) {
		return userSer.getUserPermissions(moduleId);
	}

}
