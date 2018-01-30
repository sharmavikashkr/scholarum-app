package com.scholarum.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.util.RoleUtil;
import com.scholarum.dashboard.service.ManageUserService;

@RestController
@RequestMapping("/manage/user")
public class ManageUserController {

	@Autowired
	private ManageUserService mngUserSer;

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping("/get")
	public List<ScUser> getUsers() {
		return mngUserSer.getUsers();
	}

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping("/new")
	public void newUser(@RequestBody ScUser user) {
		mngUserSer.newUser(user);
	}

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping("/toggle/{userId}")
	public void toggleUser(@PathVariable Integer userId) {
		mngUserSer.toggleUser(userId);
	}

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public void addRole(@RequestParam("userId") Integer userId, @RequestParam("roleId") Integer roleId) {
		mngUserSer.addRole(userId, roleId);
	}

	@PreAuthorize(RoleUtil.ALL_ADMIN_AUTH)
	@RequestMapping(value = "/removeRole", method = RequestMethod.POST)
	public void removeRole(@RequestParam("userId") Integer userId, @RequestParam("roleId") Integer roleId) {
		mngUserSer.removeRole(userId, roleId);
	}

}
