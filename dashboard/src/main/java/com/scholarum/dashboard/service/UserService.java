package com.scholarum.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.bean.UserPermissionBean;
import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.RoleModule;
import com.scholarum.common.entity.RolePermission;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.exception.ScException;
import com.scholarum.common.repository.ActivityRepository;
import com.scholarum.common.repository.AdminUserRepository;
import com.scholarum.common.repository.InstitutionUserRepository;
import com.scholarum.common.repository.RoleModuleRepository;
import com.scholarum.common.repository.RolePermissionRepository;
import com.scholarum.common.repository.SchoolUserRepository;
import com.scholarum.common.service.SecurityService;
import com.scholarum.common.type.HierarchyType;
import com.scholarum.common.util.CommonUtil;

@Service
public class UserService {

	@Autowired
	private AdminUserRepository adminUserRepo;

	@Autowired
	private InstitutionUserRepository instiUserRepo;

	@Autowired
	private SchoolUserRepository schoolUserRepo;

	@Autowired
	private SecurityService secSer;

	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private RoleModuleRepository roleModuleRepo;

	@Autowired
	private RolePermissionRepository rolePermRepo;

	public List<UserPermissionBean> getUserPermissions() {
		ScUser user = secSer.findLoggedInUser();
		Integer objectId = null;
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
		if (CommonUtil.isNull(objectId)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Operation not allowed");
		}
		List<UserPermissionBean> userPermList = new ArrayList<>();
		for (RoleModule roleModule : roleModuleRepo.findByRole(user.getUserRoles().get(0).getRole())) {
			Module module = roleModule.getModule();
			UserPermissionBean userPerm = new UserPermissionBean();
			userPerm.setModule(module);
			List<RolePermission> rolePermList = new ArrayList<>();
			for (Activity activity : activityRepo.findByModule(module)) {
				RolePermission rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(objectId, roleModule,
						activity);
				if (CommonUtil.isNull(rolePerm)) {
					rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(-1, roleModule, activity);
				}
				rolePermList.add(rolePerm);
			}
			userPerm.setRolePermList(rolePermList);
			userPermList.add(userPerm);
		}
		return userPermList;
	}

}
