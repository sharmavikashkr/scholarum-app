package com.scholarum.dashboard.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.validation.RequestValidator;

@Service
public class UserValidator implements RequestValidator<ScUser> {

	@Autowired
	private List<RequestValidator<ScUser>> rules;

	@Override
	public void validate(ScUser user) {
		for (RequestValidator<ScUser> rule : rules) {
			rule.validate(user);
		}
	}

}
