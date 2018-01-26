package com.scholarum.institution.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.Institution;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.School;
import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.repository.HierarchyRepository;
import com.scholarum.common.repository.RoleRepository;
import com.scholarum.common.repository.SchoolRepository;
import com.scholarum.common.repository.SchoolUserRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.type.HierarchyType;
import com.scholarum.common.type.RoleType;
import com.scholarum.dashboard.validation.UserValidator;
import com.scholarum.institution.validation.SchoolValidator;

@Service
public class ManageSchoolService {

	@Autowired
	private SecurityService secSer;

	@Autowired
	private HierarchyRepository hieRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private SchoolRepository schoolRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SchoolUserRepository schoolUserRepo;

	@Autowired
	private SchoolValidator schoolValid;

	@Autowired
	private UserValidator userValid;

	public void newSchool(SchoolUser schoolUser) {
		ScUser user = secSer.findLoggedInUser();
		Institution institution = secSer.getInstitutionForLoggedInUser();
		School school = schoolUser.getSchool();
		schoolValid.validate(school);
		school.setCreatedBy(user.getCreatedBy());
		school.setInstitution(institution);
		schoolRepo.save(school);

		ScUser newUser = schoolUser.getUser();
		userValid.validate(newUser);
		newUser.setCreatedBy(user.getCreatedBy());
		newUser.setHierarchy(hieRepo.findByName(HierarchyType.SCHOOL));
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setRole(roleRepo.findByName(RoleType.ROLE_SCHOOL_ADMIN));
		userRole.setScUser(newUser);
		userRoles.add(userRole);
		newUser.setUserRoles(userRoles);
		userRepo.save(newUser);

		schoolUserRepo.save(schoolUser);

		// TODO : send reset password link
	}

}
