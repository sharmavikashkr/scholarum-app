package com.scholarum.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Activity;
import com.scholarum.common.entity.Module;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	public List<Activity> findByModule(Module module);

	public Activity findByModuleAndName(Module module, String name);

}
