package com.scholarum.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scholarum.common.entity.Hierarchy;
import com.scholarum.common.type.HierarchyType;

@Repository
public interface HierarchyRepository extends JpaRepository<Hierarchy, Integer> {

	public Hierarchy findByName(HierarchyType name);

}
