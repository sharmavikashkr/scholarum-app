package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.SchoolUser;

@Repository
public interface SchoolUserRepository extends JpaRepository<SchoolUser, Integer> {

	public SchoolUser findByUserId(Integer userId);

	public List<SchoolUser> findBySchoolId(Integer merchantId);

}
