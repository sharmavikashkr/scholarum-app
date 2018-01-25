package com.scholarum.institution.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.School;
import com.scholarum.common.validation.RequestValidator;

@Service
public class SchoolValidator implements RequestValidator<School> {

	@Autowired
	private List<RequestValidator<School>> rules;

	@Override
	public void validate(School school) {
		for (RequestValidator<School> rule : rules) {
			rule.validate(school);
		}
	}

}
