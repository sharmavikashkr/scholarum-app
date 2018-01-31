package com.scholarum.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.admin.service.AdminService;
import com.scholarum.common.entity.Admin;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService admSer;

	@PreAuthorize(RoleUtil.SCHOLARUM_AUTH)
	@RequestMapping("/get")
	public Admin getAdmin() {
		return admSer.getAdmin();
	}

	@PreAuthorize("hasPermission('MODULE','ACTIVITY','ADD')")
	@RequestMapping("/test/add")
	public String addTest() {
		return "User has permission to add activity";
	}

	@PreAuthorize("hasPermission('MODULE','ACTIVITY','DELETE')")
	@RequestMapping("/test/delete")
	public String deleteTest() {
		return "User has permission to delete activity";
	}
}
