package com.scholarum.institution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.util.RoleUtil;
import com.scholarum.institution.service.ManageSchoolService;

@RestController
@RequestMapping("/manage/school")
public class ManageSchoolController {

	@Autowired
	private ManageSchoolService mngSchSer;

	@PreAuthorize(RoleUtil.INSTITUTION_ADMIN_AUTH)
	@RequestMapping("/new")
	public void newInstitution(@RequestBody SchoolUser schoolUser) {
		mngSchSer.newSchool(schoolUser);
	}

}
