package com.scholarum.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.scholarum.common.bean.Company;

@RestController
public class CommonErrorController implements ErrorController {

	private static final String PATH = "/error";

	@Autowired
	private Company company;

	@RequestMapping(value = PATH)
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("html/errorpage");
		mv.addObject("staticUrl", company.getStaticUrl());
		mv.addObject("message", "Requested Resource is not found");
		return mv;
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
