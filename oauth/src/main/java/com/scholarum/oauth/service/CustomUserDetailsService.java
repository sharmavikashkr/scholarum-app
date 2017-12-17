package com.scholarum.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scholarum.common.entity.CustomUserDetails;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserRoleService userRoleService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		ScUser user = userRepo.findByEmail(email);
		if (user != null) {
			if (!user.isActive()) {
				return null;
			}
			String password = user.getPassword();
			String[] roles = userRoleService.getUserRoles(user);
			CustomUserDetails customUserDetails = new CustomUserDetails(email, password, roles);
			return customUserDetails;
		}
		return null;
	}
}
