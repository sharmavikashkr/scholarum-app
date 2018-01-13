package com.scholarum.admin.controller;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
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
		try {
			return secSer.getAdminForLoggedInUser();
		} catch (Exception ex) {
			httpResponse.setStatus(HttpStatus.BAD_REQUEST_400);
			throw ex;
		}
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_AUTH + " and hasPermission('ACTIVITY','ADD')")
	@RequestMapping("/test")
	public String test(HttpServletResponse httpResponse) {
		return "User has permission to add activity";
	}
}
