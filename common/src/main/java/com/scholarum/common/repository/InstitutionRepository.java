package com.scholarum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

	public Institution findByAccessKey(String accessKey);

	public Institution findByEmail(String email);

}
