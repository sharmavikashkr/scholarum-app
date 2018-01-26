package com.scholarum.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@RequestMapping("/new")
	public void newUser(@RequestBody ScUser user) {
		mngUserSer.newUser(user);
	}

}
