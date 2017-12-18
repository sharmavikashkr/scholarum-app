package com.scholarum.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.UserRole;

@Service
public class UserRoleService {

	public String[] getUserRoles(ScUser user) {
		List<UserRole> userRoles = user.getUserRoles();
		String[] roles = new String[userRoles.size()];
		int i = 0;
		for (UserRole userRole : userRoles) {
			roles[i++] = userRole.getRole().name();
		}
		return roles;
	}

}
