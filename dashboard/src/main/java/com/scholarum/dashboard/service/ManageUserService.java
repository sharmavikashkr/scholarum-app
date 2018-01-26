package com.scholarum.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.Admin;
import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.Institution;
import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.entity.Role;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.School;
import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.exception.ScException;
import com.scholarum.common.repository.AdminUserRepository;
import com.scholarum.common.repository.InstitutionUserRepository;
import com.scholarum.common.repository.RoleRepository;
import com.scholarum.common.repository.SchoolUserRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.type.HierarchyType;
import com.scholarum.dashboard.validation.UserValidator;

@Service
public class ManageUserService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private AdminUserRepository adminUserRepo;

	@Autowired
	private InstitutionUserRepository instiUserRepo;

	@Autowired
	private SchoolUserRepository schoolUserRepo;

	@Autowired
	private SecurityService secSer;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserValidator userValid;

	public void newUser(ScUser newUser) {
		ScUser user = secSer.findLoggedInUser();
		userValid.validate(newUser);
		newUser.setCreatedBy(user.getEmail());
		Role role = roleRepo.findByName(newUser.getRoleType());
		newUser.setHierarchy(user.getHierarchy());
		if (!role.getHierarchy().getName().equals(user.getHierarchy().getName())) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Role does not exist");
		}
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setScUser(newUser);
		userRoles.add(userRole);
		newUser.setUserRoles(userRoles);
		userRepo.save(newUser);
		if (HierarchyType.SCHOLARUM.equals(user.getHierarchy().getName())) {
			Admin admin = secSer.getAdminForLoggedInUser();
			AdminUser adminUser = new AdminUser();
			adminUser.setAdmin(admin);
			adminUser.setUser(newUser);
			adminUserRepo.save(adminUser);
		} else if (HierarchyType.INSTITUTION.equals(user.getHierarchy().getName())) {
			Institution insti = secSer.getInstitutionForLoggedInUser();
			InstitutionUser instiUser = new InstitutionUser();
			instiUser.setInstitution(insti);
			instiUser.setUser(newUser);
			instiUserRepo.save(instiUser);
		} else if (HierarchyType.SCHOOL.equals(user.getHierarchy().getName())) {
			School school = secSer.getSchoolForLoggedInUser();
			SchoolUser schoolUser = new SchoolUser();
			schoolUser.setSchool(school);
			schoolUser.setUser(newUser);
			schoolUserRepo.save(schoolUser);
		}
	}

}
