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
import com.scholarum.common.repository.UserRoleRepository;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.type.HierarchyType;
import com.scholarum.common.type.RoleType;
import com.scholarum.common.util.CommonUtil;
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
	private UserRoleRepository userRoleRepo;

	@Autowired
	private UserValidator userValid;

	public List<ScUser> getUsers() {
		ScUser user = secSer.findLoggedInUser();
		if (HierarchyType.SCHOLARUM.equals(user.getHierarchy().getName())) {
			Admin admin = secSer.getAdminForLoggedInUser();
			return adminUserRepo.findUsersForAdmin(admin);
		} else if (HierarchyType.INSTITUTION.equals(user.getHierarchy().getName())) {
			Institution insti = secSer.getInstitutionForLoggedInUser();
			return instiUserRepo.findUsersForInstitution(insti);
		} else if (HierarchyType.SCHOOL.equals(user.getHierarchy().getName())) {
			School school = secSer.getSchoolForLoggedInUser();
			return schoolUserRepo.findUsersForSchool(school);
		}
		return null;
	}

	public void newUser(ScUser newUser) {
		ScUser user = secSer.findLoggedInUser();
		userValid.validate(newUser);
		newUser.setCreatedBy(user.getEmail());
		Role role = roleRepo.findByName(newUser.getRoleType());
		newUser.setHierarchy(user.getHierarchy());
		if (CommonUtil.isNull(role) || !role.getHierarchy().getName().equals(user.getHierarchy().getName())) {
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

	public void toggleUser(Integer userId) {
		ScUser admin = secSer.findLoggedInUser();
		ScUser child = userRepo.findOne(userId);
		validateUser(admin, child);
		isUserAdmin(child);
		child.setActive(!child.isActive());
		userRepo.save(child);
	}

	public void addRole(Integer userId, Integer roleId) {
		ScUser admin = secSer.findLoggedInUser();
		Role role = roleRepo.findOne(roleId);
		validateRole(admin, role);
		ScUser child = userRepo.findOne(userId);
		validateUser(admin, child);
		UserRole userRole = userRoleRepo.findByUserAndRole(child, role);
		if (CommonUtil.isNotNull(userRole)) {
			return;
		}
		userRole = new UserRole();
		userRole.setRole(role);
		userRole.setScUser(child);
		userRoleRepo.save(userRole);
	}

	public void removeRole(Integer userId, Integer roleId) {
		ScUser admin = secSer.findLoggedInUser();
		Role role = roleRepo.findOne(roleId);
		validateRole(admin, role);
		isRoleAdmin(role);
		ScUser child = userRepo.findOne(userId);
		validateUser(admin, child);
		UserRole userRole = userRoleRepo.findByUserAndRole(child, role);
		if (CommonUtil.isNotNull(userRole)) {
			userRoleRepo.deleteUserRole(userRole.getId());
		}
	}

	private void validateRole(ScUser admin, Role role) {
		if (CommonUtil.isNull(role) || !role.getHierarchy().getName().equals(admin.getHierarchy().getName())) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Role does not exist");
		}
	}

	private void validateUser(ScUser user, ScUser child) {
		if (CommonUtil.isNull(child)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "User does not exist");
		}
		if (HierarchyType.SCHOLARUM.equals(user.getHierarchy().getName())) {
			Admin admin = secSer.getAdminForLoggedInUser();
			if (CommonUtil.isNull(adminUserRepo.findByAdminAndUser(admin, child))) {
				throw new ScException(HttpStatus.BAD_REQUEST_400, "User does not exist");
			}
		} else if (HierarchyType.INSTITUTION.equals(user.getHierarchy().getName())) {
			Institution insti = secSer.getInstitutionForLoggedInUser();
			if (CommonUtil.isNull(instiUserRepo.findByInstitutionAndUser(insti, child))) {
				throw new ScException(HttpStatus.BAD_REQUEST_400, "User does not exist");
			}
		} else if (HierarchyType.SCHOOL.equals(user.getHierarchy().getName())) {
			School school = secSer.getSchoolForLoggedInUser();
			if (CommonUtil.isNull(schoolUserRepo.findBySchoolAndUser(school, child))) {
				throw new ScException(HttpStatus.BAD_REQUEST_400, "User does not exist");
			}
		}
	}

	private void isUserAdmin(ScUser child) {
		for (UserRole userRole : child.getUserRoles()) {
			if (RoleType.ROLE_SCHOLARUM_ADMIN.equals(userRole.getRole().getName())
					|| RoleType.ROLE_INSTITUTION_ADMIN.equals(userRole.getRole().getName())
					|| RoleType.ROLE_SCHOOL_ADMIN.equals(userRole.getRole().getName())) {
				throw new ScException(HttpStatus.BAD_REQUEST_400, "Cannot toggle Admin");
			}
		}
	}

	private void isRoleAdmin(Role role) {
		if (RoleType.ROLE_SCHOLARUM_ADMIN.equals(role.getName())
				|| RoleType.ROLE_INSTITUTION_ADMIN.equals(role.getName())
				|| RoleType.ROLE_SCHOOL_ADMIN.equals(role.getName())) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Cannot remove Admin role");
		}
	}

}
