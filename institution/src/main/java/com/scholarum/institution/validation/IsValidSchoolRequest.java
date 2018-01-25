package com.scholarum.institution.validation;

import java.util.Date;
import java.util.UUID;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scholarum.common.entity.School;
import com.scholarum.common.exception.ScException;
import com.scholarum.common.repository.SchoolRepository;
import com.scholarum.common.util.CommonUtil;
import com.scholarum.common.util.HmacSignerUtil;
import com.scholarum.common.util.RandomIdGenerator;
import com.scholarum.common.validation.RequestValidator;

@Component
@Order(0)
public class IsValidSchoolRequest implements RequestValidator<School> {

	@Autowired
	private SchoolRepository schoolRepo;

	@Autowired
	private HmacSignerUtil hmacSigner;

	@Override
	public void validate(School school) {
		Date timeNow = new Date();
		if (CommonUtil.isNull(school)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Invalid create school request");
		}
		if (CommonUtil.isEmpty(school.getName()) || CommonUtil.isEmpty(school.getEmail())
				|| CommonUtil.isEmpty(school.getMobile())) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Mandatory params missing");
		}
		if (CommonUtil.isNotNull(schoolRepo.findByEmail(school.getEmail()))) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "School with email already exists");
		}
		String secretKey = hmacSigner.signWithSecretKey(UUID.randomUUID().toString(),
				String.valueOf(timeNow.getTime()));
		String accessKey = secretKey + secretKey.toLowerCase() + secretKey.toUpperCase();
		do {
			accessKey = RandomIdGenerator.generateAccessKey(accessKey.toCharArray());
		} while (CommonUtil.isNotNull(schoolRepo.findByAccessKey(accessKey)));
		school.setAccessKey(accessKey);
		school.setSecretKey(secretKey);
		school.setCreated(timeNow);
		school.setActive(true);
	}

}
