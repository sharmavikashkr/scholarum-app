package com.scholarum.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.Role;
import com.scholarum.common.entity.RoleModule;
import com.scholarum.common.entity.RolePermission;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.entity.UserRole;
import com.scholarum.common.repository.ActivityRepository;
import com.scholarum.common.repository.AdminUserRepository;
import com.scholarum.common.repository.InstitutionUserRepository;
import com.scholarum.common.repository.ModuleRepository;
import com.scholarum.common.repository.RoleModuleRepository;
import com.scholarum.common.repository.RolePermissionRepository;
import com.scholarum.common.repository.SchoolUserRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.type.HierarchyType;
import com.scholarum.common.util.CommonUtil;

@Service
public class RolePermissionService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModuleRepository moduleRepo;

	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private RoleModuleRepository roleModuleRepo;

	@Autowired
	private RolePermissionRepository rolePermRepo;

	@Autowired
	private AdminUserRepository adminUserRepo;

	@Autowired
	private InstitutionUserRepository instiUserRepo;

	@Autowired
	private SchoolUserRepository schoolUserRepo;

	public boolean checkIfActionAllowed(String email, String moduleName, String activityName, String action) {
		try {
			boolean allow = false;
			ScUser user = userRepo.findByEmail(email);
			Module module = moduleRepo.findByName(moduleName);
			Activity activity = activityRepo.findByModuleAndName(module, activityName);
			for (UserRole userRole : user.getUserRoles()) {
				Integer objectId = -1;
				Role role = userRole.getRole();
				RoleModule roleModule = roleModuleRepo.findByRoleAndModule(role, module);
				if (HierarchyType.SCHOLARUM.equals(user.getHierarchy().getName())) {
					AdminUser adminUser = adminUserRepo.findByUser(user);
					objectId = adminUser.getAdmin().getId();
				} else if (HierarchyType.INSTITUTION.equals(user.getHierarchy().getName())) {
					InstitutionUser instiUser = instiUserRepo.findByUser(user);
					objectId = instiUser.getInstitution().getId();
				} else if (HierarchyType.SCHOOL.equals(user.getHierarchy().getName())) {
					SchoolUser schoolUser = schoolUserRepo.findByUser(user);
					objectId = schoolUser.getSchool().getId();
				}
				RolePermission rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(objectId, roleModule,
						activity);
				if (CommonUtil.isNull(rolePerm)) {
					rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(-1, roleModule, activity);
				}
				if ("ADD".equalsIgnoreCase(action)) {
					allow = allow || rolePerm.isAdd();
				} else if ("VIEW".equalsIgnoreCase(action)) {
					allow = allow || rolePerm.isView();
				} else if ("EDIT".equalsIgnoreCase(action)) {
					allow = allow || rolePerm.isEdit();
				} else if ("DELETE".equalsIgnoreCase(action)) {
					allow = allow || rolePerm.isDelete();
				}
			}
			return allow;
		} catch (Exception ex) {
			return false;
		}
	}

}
