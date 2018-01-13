package com.scholarum.dashboard.validation;

import java.util.Date;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.exception.ScException;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.util.CommonUtil;
import com.scholarum.common.validation.RequestValidator;

@Component
@Order(0)
public class IsValidUserRequest implements RequestValidator<ScUser> {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final String MOBILE_PATTERN = "^[7-9]{1}[0-9]{9}$";

	private static final String NAME_PATTERN = "[a-zA-Z ]*";

	@Autowired
	private UserRepository userRepo;

	@Override
	public void validate(ScUser user) {
		Date timeNow = new Date();
		if (CommonUtil.isNull(user)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Invalid create user request");
		}
		if (!(match(user.getEmail(), EMAIL_PATTERN) || match(user.getMobile(), MOBILE_PATTERN)
				|| match(user.getName(), NAME_PATTERN))) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Invalid user params");
		}
		if (CommonUtil.isNotNull(userRepo.findByEmailOrMobile(user.getEmail(), user.getMobile()))) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "User already exists");
		}
		user.setPassword(new BCryptPasswordEncoder().encode("password@123"));
		user.setCreated(timeNow);
		user.setActive(true);
	}

	private boolean match(String value, String pattern) {
		if (CommonUtil.isNotNull(value)) {
			return value.matches(pattern);
		}
		return true;
	}

}
