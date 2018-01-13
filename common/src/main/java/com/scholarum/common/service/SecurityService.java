package com.scholarum.common.service;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.Admin;
import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.Institution;
import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.School;
import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.exception.ScException;
import com.scholarum.common.repository.AdminUserRepository;
import com.scholarum.common.repository.InstitutionUserRepository;
import com.scholarum.common.repository.SchoolUserRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.type.Hierarchy;

@Service
public class SecurityService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AdminUserRepository adminUserRepo;

	@Autowired
	private InstitutionUserRepository instUserRepo;

	@Autowired
	private SchoolUserRepository schUserRepo;

	public ScUser findLoggedInUser() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			return userRepo.findByEmail(((UserDetails) userDetails).getUsername());
		}
		throw new ScException(HttpStatus.BAD_REQUEST_400, "Invalid User");
	}

	public Admin getAdminForLoggedInUser() {
		ScUser user = findLoggedInUser();
		if (Hierarchy.SCHOLARUM.equals(user.getHierarchy())) {
			AdminUser adminUser = adminUserRepo.findByUser(user);
			return adminUser.getAdmin();
		}
		return null;
	}

	public Institution getInstitutionForLoggedInUser() {
		ScUser user = findLoggedInUser();
		if (Hierarchy.INSTITUTION.equals(user.getHierarchy())) {
			InstitutionUser instUser = instUserRepo.findByUser(user);
			return instUser.getInstitution();
		}
		return null;
	}

	public School getSchoolForLoggedInUser() {
		ScUser user = findLoggedInUser();
		if (Hierarchy.SCHOOL.equals(user.getHierarchy())) {
			SchoolUser schUser = schUserRepo.findByUser(user);
			return schUser.getSchool();
		}
		return null;
	}
}
