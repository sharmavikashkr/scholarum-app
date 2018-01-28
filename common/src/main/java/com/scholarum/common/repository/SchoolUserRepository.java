package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.School;
import com.scholarum.common.entity.SchoolUser;

@Repository
public interface SchoolUserRepository extends JpaRepository<SchoolUser, Integer> {

	public SchoolUser findByUser(ScUser user);

	public List<SchoolUser> findBySchool(School school);

	@Query("SELECT su.user FROM SchoolUser su WHERE su.school = ?1")
	public List<ScUser> findUsersForSchool(School school);

}
