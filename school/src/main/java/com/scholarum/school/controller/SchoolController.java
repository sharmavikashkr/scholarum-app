package com.scholarum.school.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.School;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/school")
public class SchoolController {

	@Autowired
	private SecurityService secSer;

	@PreAuthorize(RoleUtil.SCHOOL_AUTH)
	@RequestMapping("/get")
	public School getSchool(HttpServletResponse httpResponse) {
		return secSer.getSchoolForLoggedInUser();
	}

}
