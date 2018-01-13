package com.scholarum.admin.validation;

import java.util.Date;
import java.util.UUID;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scholarum.common.entity.Institution;
import com.scholarum.common.exception.ScException;
import com.scholarum.common.repository.InstitutionRepository;
import com.scholarum.common.util.CommonUtil;
import com.scholarum.common.util.HmacSignerUtil;
import com.scholarum.common.util.RandomIdGenerator;
import com.scholarum.common.validation.RequestValidator;

@Component
@Order(0)
public class IsValidInstitutionRequest implements RequestValidator<Institution> {

	@Autowired
	private InstitutionRepository instRepo;

	@Autowired
	private HmacSignerUtil hmacSigner;

	@Override
	public void validate(Institution institution) {
		Date timeNow = new Date();
		if (CommonUtil.isNull(institution)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Invalid create institution request");
		}
		if (CommonUtil.isEmpty(institution.getName()) || CommonUtil.isEmpty(institution.getEmail())
				|| CommonUtil.isEmpty(institution.getMobile())) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Mandatory params missing");
		}
		String secretKey = hmacSigner.signWithSecretKey(UUID.randomUUID().toString(),
				String.valueOf(timeNow.getTime()));
		String accessKey = secretKey + secretKey.toLowerCase() + secretKey.toUpperCase();
		do {
			accessKey = RandomIdGenerator.generateAccessKey(accessKey.toCharArray());
		} while (CommonUtil.isNotNull(instRepo.findByAccessKey(accessKey)));
		institution.setAccessKey(accessKey);
		institution.setSecretKey(secretKey);
		institution.setCreated(timeNow);
		institution.setActive(true);
	}

}
