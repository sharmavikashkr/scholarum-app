package com.scholarum.institution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.Institution;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.util.RoleUtil;
import com.scholarum.institution.service.InstitutionService;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

	@Autowired
	private SecurityService secSer;

	@Autowired
	private InstitutionService instiSer;

	@PreAuthorize(RoleUtil.INSTITUTION_AUTH)
	@RequestMapping("/get")
	public Institution getInstitution() {
		return secSer.getInstitutionForLoggedInUser();
	}

	@PreAuthorize(RoleUtil.INSTITUTION_ADMIN_AUTH)
	@RequestMapping("/school/new")
	public void newInstitution(@RequestBody SchoolUser schoolUser) {
		instiSer.newSchool(schoolUser);
	}

}
