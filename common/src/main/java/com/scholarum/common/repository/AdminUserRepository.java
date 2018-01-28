package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Admin;
import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.ScUser;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

	public AdminUser findByUser(ScUser user);

	public List<AdminUser> findByAdmin(Admin admin);

	@Query("SELECT au.user FROM AdminUser au WHERE au.admin = ?1")
	public List<ScUser> findUsersForAdmin(Admin admin);

}
