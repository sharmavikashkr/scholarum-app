package com.scholarum.institution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.Institution;
import com.scholarum.common.service.SecurityService;

@Service
public class InstitutionService {

	@Autowired
	private SecurityService secSer;

	public Institution getInstitution() {
		return secSer.getInstitutionForLoggedInUser();
	}

}
