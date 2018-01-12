package com.scholarum.common.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.School;
import com.scholarum.common.entity.SchoolUser;
import com.scholarum.common.repository.SchoolUserRepository;
import com.scholarum.common.repository.UserRepository;
import com.scholarum.common.type.Role;

@Service
public class SecurityService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SchoolUserRepository schUserRepo;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private TokenStore tokenStore;

	public ScUser findLoggedInUser() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			return userRepo.findByEmail(((UserDetails) userDetails).getUsername());
		}
		return null;
	}

	public ScUser findLoggedInUser(String token) {
		try {
			OAuth2Authentication oauth = tokenStore.readAuthentication(tokenStore.readAccessToken(token));
			UserDetails userDetails = (UserDetails) oauth.getUserAuthentication().getPrincipal();
			return userRepo.findByEmail(userDetails.getUsername());
		} catch (Exception ex) {
			return null;
		}
	}

	public boolean isSchoolUser() {
		ScUser user = findLoggedInUser();
		String[] roles = userRoleService.getUserRoles(user);
		return (Arrays.asList(roles).contains(Role.ROLE_SCHOOL_ADMIN.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_FINANCE.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_OPS.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_TEACHER.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_STAFF.name()));
	}

	public School getSchoolForLoggedInUser() {
		ScUser user = findLoggedInUser();
		String[] roles = userRoleService.getUserRoles(user);
		if (Arrays.asList(roles).contains(Role.ROLE_SCHOOL_ADMIN.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_FINANCE.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_OPS.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_TEACHER.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_STAFF.name())) {
			SchoolUser schUser = schUserRepo.findByUser(user);
			return schUser.getSchool();
		}
		return null;
	}

	public School getSchoolForLoggedInUser(String token) {
		ScUser user = findLoggedInUser(token);
		if (user == null) {
			return null;
		}
		String[] roles = userRoleService.getUserRoles(user);
		if (Arrays.asList(roles).contains(Role.ROLE_SCHOOL_ADMIN.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_FINANCE.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_OPS.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_TEACHER.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOOL_STAFF.name())) {
			SchoolUser schUser = schUserRepo.findByUser(user);
			return schUser.getSchool();
		}
		return null;
	}

	public boolean isScholarumUser(String token) {
		ScUser user = findLoggedInUser(token);
		if (user == null) {
			return false;
		}
		String[] roles = userRoleService.getUserRoles(user);
		return (Arrays.asList(roles).contains(Role.ROLE_SCHOLARUM_ADMIN.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOLARUM_SUPERVISOR.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOLARUM_FINANCE.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOLARUM_OPS.name())
				|| Arrays.asList(roles).contains(Role.ROLE_SCHOLARUM_ADVISOR.name()));
	}
}
