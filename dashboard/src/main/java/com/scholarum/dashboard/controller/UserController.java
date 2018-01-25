package com.scholarum.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.bean.UserPermissionBean;
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
	public ScUser getUser(HttpServletResponse httpResponse) {
		return secSer.findLoggedInUser();
	}

	@PreAuthorize(RoleUtil.ALL_AUTH)
	@RequestMapping("/permissions/get")
	public List<UserPermissionBean> getUserPermissions(HttpServletResponse httpResponse) {
		return userSer.getUserPermissions();
	}

}
