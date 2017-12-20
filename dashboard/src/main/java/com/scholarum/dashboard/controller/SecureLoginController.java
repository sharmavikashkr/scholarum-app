package com.scholarum.dashboard.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.common.exception.ScException;
import com.scholarum.dashboard.service.SecureLoginService;

@RestController
public class SecureLoginController {

	@Autowired
	private SecureLoginService secLoginService;

	@RequestMapping(value = "/secure/login", method = RequestMethod.POST)
	public LinkedHashMap secureLogin(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpServletResponse response) {
		try {
			return secLoginService.secureLogin(email, password);
		} catch (Exception ex) {
			response.setStatus(HttpStatus.UNAUTHORIZED_401);
			throw new ScException(HttpStatus.UNAUTHORIZED_401, "Invalid credentials");
		}
	}

}
