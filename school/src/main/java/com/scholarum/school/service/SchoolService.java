package com.scholarum.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.School;
import com.scholarum.common.service.SecurityService;

@Service
public class SchoolService {

	@Autowired
	private SecurityService secSer;

	public School getSchool() {
		return secSer.getSchoolForLoggedInUser();
	}

}
