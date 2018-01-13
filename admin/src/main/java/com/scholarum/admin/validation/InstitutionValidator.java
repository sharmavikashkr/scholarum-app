package com.scholarum.admin.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.Institution;
import com.scholarum.common.validation.RequestValidator;

@Service
public class InstitutionValidator implements RequestValidator<Institution> {

	@Autowired
	private List<RequestValidator<Institution>> rules;

	@Override
	public void validate(Institution institution) {
		for (RequestValidator<Institution> rule : rules) {
			rule.validate(institution);
		}
	}

}
