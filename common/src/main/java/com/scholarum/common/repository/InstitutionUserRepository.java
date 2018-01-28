package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Institution;
import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.entity.ScUser;

@Repository
public interface InstitutionUserRepository extends JpaRepository<InstitutionUser, Integer> {

	public InstitutionUser findByUser(ScUser user);

	public List<InstitutionUser> findByInstitution(Institution institution);

	@Query("SELECT iu.user FROM InstitutionUser iu WHERE iu.institution = ?1")
	public List<ScUser> findUsersForInstitution(Institution institution);

}
