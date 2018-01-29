package com.scholarum.dashboard.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarum.common.bean.ActivityPermissionBean;
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
import com.scholarum.common.repository.ModuleRepository;
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
	private ModuleRepository moduleRepo;

	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private RoleModuleRepository roleModuleRepo;

	@Autowired
	private RolePermissionRepository rolePermRepo;

	public Set<Module> getUserModules() {
		Set<Module> modSet = new LinkedHashSet<>();
		return modSet;
	}

	public UserPermissionBean getUserPermissions(Integer moduleId) {
		ScUser user = secSer.findLoggedInUser();
		Module module = moduleRepo.findOne(moduleId);
		if (CommonUtil.isNull(module)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Invalid Module");
		}
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
		UserPermissionBean userPerm = new UserPermissionBean();
		userPerm.setModule(module);
		for (RoleModule roleModule : roleModuleRepo.findByModule(module)) {
			List<ActivityPermissionBean> activityPermList = new ArrayList<>();
			for (Activity activity : activityRepo.findByModule(module)) {
				boolean add = false;
				boolean view = false;
				boolean edit = false;
				boolean delete = false;
				RolePermission rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(objectId, roleModule,
						activity);
				if (CommonUtil.isNull(rolePerm)) {
					rolePerm = rolePermRepo.findByObjectIdAndRoleModuleAndActivity(-1, roleModule, activity);
				}
				add = add || rolePerm.isAdd();
				view = view || rolePerm.isView();
				edit = edit || rolePerm.isEdit();
				delete = delete || rolePerm.isDelete();
				ActivityPermissionBean activityBean = new ActivityPermissionBean(activity, add, view, edit, delete);
				activityPermList.add(activityBean);
			}
			userPerm.setActivityPermList(activityPermList);
		}
		return userPerm;
	}

}
