package com.scholarum.institution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.Institution;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

	@Autowired
	private SecurityService secSer;

	@PreAuthorize(RoleUtil.INSTITUTION_AUTH)
	@RequestMapping("/get")
	public Institution getInstitution() {
		return secSer.getInstitutionForLoggedInUser();
	}

}
