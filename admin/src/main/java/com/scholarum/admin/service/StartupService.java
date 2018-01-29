package com.scholarum.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.scholarum.common.entity.Admin;
import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.Hierarchy;
import com.scholarum.common.entity.Role;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.repository.AdminRepository;
import com.scholarum.common.repository.AdminUserRepository;
import com.scholarum.common.repository.HierarchyRepository;
import com.scholarum.common.repository.RoleRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.type.HierarchyType;
import com.scholarum.common.type.RoleType;
import com.scholarum.common.util.CommonUtil;
import com.scholarum.common.util.HmacSignerUtil;
import com.scholarum.common.util.RandomIdGenerator;

@Component
public class StartupService {

	@Autowired
	private HierarchyRepository hieRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private AdminUserRepository adminUserRepo;

	@Autowired
	private HmacSignerUtil hmacSigner;

	public void setupHierarchy() {
		if (!hieRepo.findAll().isEmpty()) {
			return;
		}
		for (HierarchyType name : HierarchyType.values()) {
			Hierarchy hierarchy = new Hierarchy();
			hierarchy.setName(name);
			hieRepo.save(hierarchy);
		}
	}

	public void setupRoles() {
		if (!roleRepo.findAll().isEmpty()) {
			return;
		}
		for (RoleType name : RoleType.values()) {
			Role role = new Role();
			role.setName(name);
			role.setAlias(name.getAlias());
			if (name.name().contains(HierarchyType.SCHOLARUM.name())) {
				role.setHierarchy(hieRepo.findByName(HierarchyType.SCHOLARUM));
			} else if (name.name().contains(HierarchyType.INSTITUTION.name())) {
				role.setHierarchy(hieRepo.findByName(HierarchyType.INSTITUTION));
			} else if (name.name().contains(HierarchyType.SCHOOL.name())) {
				role.setHierarchy(hieRepo.findByName(HierarchyType.SCHOOL));
			}
			roleRepo.save(role);
		}
	}

	public void createSuperAdmin() {
		Date timeNow = new Date();
		if (CommonUtil.isNotNull(adminRepo.findByEmail("care@scholarum.in"))) {
			return;
		}
		Admin admin = new Admin();
		admin.setActive(true);
		admin.setCreated(new Date());
		admin.setEmail("care@scholarum.in");
		admin.setMobile("9090909090");
		admin.setName("Scholarum Inc");
		String secretKey = hmacSigner.signWithSecretKey(UUID.randomUUID().toString(),
				String.valueOf(timeNow.getTime()));
		String accessKey = secretKey + secretKey.toLowerCase() + secretKey.toUpperCase();
		accessKey = RandomIdGenerator.generateAccessKey(accessKey.toCharArray());
		admin.setAccessKey(accessKey);
		admin.setSecretKey(secretKey);
		adminRepo.save(admin);
		ScUser user = new ScUser();
		user.setCreated(timeNow);
		user.setName("Scholarum Admin");
		user.setEmail("admin@scholarum.in");
		user.setPassword(new BCryptPasswordEncoder().encode("password@123"));
		user.setMobile("9999999999");
		user.setHierarchy(hieRepo.findByName(HierarchyType.SCHOLARUM));
		user.setCreatedBy("SYSTEM");
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setRole(roleRepo.findByName(RoleType.ROLE_SCHOLARUM_ADMIN));
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
