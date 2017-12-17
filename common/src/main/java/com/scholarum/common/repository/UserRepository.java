package com.scholarum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.ScUser;

@Repository
public interface UserRepository extends JpaRepository<ScUser, Integer> {

	public ScUser findByEmail(String email);

	public ScUser findByEmailOrMobile(String email, String mobile);

}
