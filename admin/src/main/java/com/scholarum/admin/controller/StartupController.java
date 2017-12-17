package com.scholarum.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.admin.service.StartupService;

@RestController
public class StartupController {

	@Autowired
	private StartupService startupService;

	@RequestMapping("/startup")
	public void startup() {
		startupService.createSuperAdmin();
	}

}
