package com.scholarum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

	public Module findByName(String name);

}
