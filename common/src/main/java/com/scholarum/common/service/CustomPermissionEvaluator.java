package com.scholarum.common.service;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomPermissionEvaluator implements PermissionEvaluator {
	
	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(auth, targetDomainObject.toString(), permission.toString());
	}

	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
		if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(auth, targetType, permission.toString());
	}

	private boolean hasPrivilege(Authentication auth, String activity, String permission) {
		/*for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
			if (grantedAuth.getAuthority().startsWith(targetType)) {
				if (grantedAuth.getAuthority().contains(permission)) {
					return true;
				}
			}
		}*/
		Object userDetails = auth.getPrincipal();
		String email = null;
		if (userDetails instanceof UserDetails) {
			email = ((UserDetails) userDetails).getUsername();
		}
		if(email.equalsIgnoreCase("admin@scholarum.in") && activity.equalsIgnoreCase("ACTIVITY") && permission.equals("ADD")) {
			return true;
		}
		return false;
	}
}