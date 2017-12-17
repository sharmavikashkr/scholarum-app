package com.scholarum.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.type.Role;
import com.scholarum.common.type.UserType;
import com.scholarum.common.util.CommonUtil;

@Component
public class StartupService {

	@Autowired
	private UserRepository userRepo;

	public void createSuperAdmin() {
		Date timeNow = new Date();
		if (CommonUtil.isNotNull(userRepo.findByEmail("admin@scholarum.in"))) {
			return;
		}
		ScUser user = new ScUser();
		user.setCreated(timeNow);
		user.setName("Scholarum Admin");
		user.setEmail("admin@scholarum.in");
		user.setPassword(new BCryptPasswordEncoder().encode("password@123"));
		user.setMobile("9999999999");
		user.setUserType(UserType.ADMIN);
		user.setCreatedBy("SYSTEM");
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setRole(Role.ROLE_SCHOLARUM);
		userRole.setScUser(user);
		userRoles.add(userRole);
		user.setUserRoles(userRoles);
		user.setActive(true);
		userRepo.save(user);
	}

}
