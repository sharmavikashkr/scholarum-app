package com.scholarum.common.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private RolePermissionService rolePermSer;

	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		return false;
	}

	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
		if ((auth == null) || (targetId == null) || (targetType == null) || (permission == null)
				|| !(targetId instanceof String) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(auth, targetId.toString(), targetType, permission.toString());
	}

	private boolean hasPrivilege(Authentication auth, String moduleName, String activityName, String action) {
		Object userDetails = auth.getPrincipal();
		String email = null;
		if (userDetails instanceof UserDetails) {
			email = ((UserDetails) userDetails).getUsername();
		}
		return rolePermSer.checkIfActionAllowed(email, moduleName, activityName, action);
	}
}