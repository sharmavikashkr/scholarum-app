package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.entity.ScUser;
import com.scholarum.common.entity.School;

@Repository
public interface InstitutionUserRepository extends JpaRepository<InstitutionUser, Integer> {

	public InstitutionUser findByUser(ScUser user);

	public List<InstitutionUser> findBySchool(School school);

}
