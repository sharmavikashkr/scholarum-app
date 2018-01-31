package com.scholarum.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.Admin;
import com.scholarum.common.service.SecurityService;

@Service
public class AdminService {

	@Autowired
	private SecurityService secSer;

	public Admin getAdmin() {
		return secSer.getAdminForLoggedInUser();
	}

}
