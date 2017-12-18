package com.scholarum.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.util.RoleUtil;

@RestController
public class TestController {

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping("/test")
	public String startup() {
		return "TEST";
	}

}
