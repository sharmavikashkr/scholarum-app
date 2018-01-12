package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.AdminUser;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.School;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

	public AdminUser findByUser(ScUser user);

	public List<AdminUser> findBySchool(School school);

}
