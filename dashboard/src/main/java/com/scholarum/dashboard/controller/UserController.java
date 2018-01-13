package com.scholarum.dashboard.controller;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SecurityService secSer;

	@PreAuthorize(RoleUtil.ALL_AUTH)
	@RequestMapping("/get")
	public ScUser getUser(HttpServletResponse httpResponse) {
		try {
			return secSer.findLoggedInUser();
		} catch (Exception ex) {
			httpResponse.setStatus(HttpStatus.BAD_REQUEST_400);
			throw ex;
		}
	}

}
