package com.scholarum.common.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scholarum.common.type.HierarchyType;

@Entity
@Table(name = "sc_hierarchy")
public class Hierarchy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private HierarchyType name;

	public Integer getId() {
		return id;
	}

	public HierarchyType getName() {
		return name;
	}

	public void setName(HierarchyType name) {
		this.name = name;
	}

}
