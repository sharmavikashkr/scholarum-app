package com.scholarum.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.scholarum.common.entity.Admin;
import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.repository.AdminRepository;
import com.scholarum.common.repository.AdminUserRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.type.Role;
import com.scholarum.common.type.UserType;
import com.scholarum.common.util.CommonUtil;

@Component
public class StartupService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private AdminUserRepository adminUserRepo;

	public void createSuperAdmin() {
		Date timeNow = new Date();
		if (CommonUtil.isNotNull(userRepo.findByEmail("admin@scholarum.in"))) {
			return;
		}
		Admin admin = new Admin();
		admin.setAccessKey("");
		admin.setActive(true);
		admin.setCreated(new Date());
		admin.setEmail("care@scholarum.in");
		admin.setMobile("9090909090");
		admin.setName("Scholarum");
		admin.setSecretKey("");
		adminRepo.save(admin);
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
		userRole.setRole(Role.ROLE_SCHOLARUM_ADMIN);
		userRole.setScUser(user);
		userRoles.add(userRole);
		user.setUserRoles(userRoles);
		user.setActive(true);
		userRepo.save(user);
		AdminUser adminUser = new AdminUser();
		adminUser.setAdmin(admin);
		adminUser.setUser(user);
		adminUserRepo.save(adminUser);
	}

}
