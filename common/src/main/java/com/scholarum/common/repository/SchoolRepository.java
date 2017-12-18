package com.scholarum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

	public School findByAccessKey(String accessKey);

	public School findByEmail(String email);

}
