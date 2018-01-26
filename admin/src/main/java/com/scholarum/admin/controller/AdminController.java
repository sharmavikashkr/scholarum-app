package com.scholarum.admin.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.Admin;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private SecurityService secSer;

	@PreAuthorize(RoleUtil.SCHOLARUM_AUTH)
	@RequestMapping("/get")
	public Admin getAdmin(HttpServletResponse httpResponse) {
		return secSer.getAdminForLoggedInUser();
	}

	@PreAuthorize("hasPermission('MODULE','ACTIVITY','ADD')")
	@RequestMapping("/test/add")
	public String addTest(HttpServletResponse httpResponse) {
		return "User has permission to add activity";
	}

	@PreAuthorize("hasPermission('MODULE','ACTIVITY','DELETE')")
	@RequestMapping("/test/delete")
	public String deleteTest(HttpServletResponse httpResponse) {
		return "User has permission to delete activity";
	}
}
