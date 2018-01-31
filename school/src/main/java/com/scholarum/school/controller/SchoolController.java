package com.scholarum.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.School;
import com.scholarum.common.util.RoleUtil;
import com.scholarum.school.service.SchoolService;

@RestController
@RequestMapping("/school")
public class SchoolController {

	@Autowired
	private SchoolService schSer;

	@PreAuthorize(RoleUtil.SCHOOL_AUTH)
	@RequestMapping("/get")
	public School getSchool() {
		return schSer.getSchool();
	}

}
