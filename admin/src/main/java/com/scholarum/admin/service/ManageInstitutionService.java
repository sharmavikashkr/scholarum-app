package com.scholarum.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.admin.validation.InstitutionValidator;
import com.scholarum.common.entity.Institution;
import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.repository.HierarchyRepository;
import com.scholarum.common.repository.InstitutionRepository;
import com.scholarum.common.repository.InstitutionUserRepository;
import com.scholarum.common.repository.RoleRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.type.HierarchyType;
import com.scholarum.common.type.RoleType;
import com.scholarum.dashboard.validation.UserValidator;

@Service
public class ManageInstitutionService {

	@Autowired
	private HierarchyRepository hieRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private InstitutionRepository instRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private InstitutionUserRepository instUserRepo;

	@Autowired
	private InstitutionValidator instValid;

	@Autowired
	private UserValidator userValid;

	public void newInstitution(InstitutionUser instiUser, String createdBy) {
		Institution insti = instiUser.getInstitution();
		instValid.validate(insti);
		insti.setCreatedBy(createdBy);
		instRepo.save(insti);

		ScUser newUser = instiUser.getUser();
		userValid.validate(newUser);
		newUser.setCreatedBy(createdBy);
		newUser.setHierarchy(hieRepo.findByName(HierarchyType.INSTITUTION));
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setRole(roleRepo.findByName(RoleType.ROLE_INSTITUTION_ADMIN));
		userRole.setScUser(newUser);
		userRoles.add(userRole);
		newUser.setUserRoles(userRoles);
		userRepo.save(newUser);

		instUserRepo.save(instiUser);

		// TODO : send reset password link
	}

}
