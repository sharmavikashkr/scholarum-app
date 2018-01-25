package com.scholarum.common.bean;

import java.util.List;

import com.scholarum.common.entity.Module;
import com.scholarum.common.entity.RolePermission;

public class UserPermissionBean {

	private Module module;
	private List<RolePermission> rolePermList;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<RolePermission> getRolePermList() {
		return rolePermList;
	}

	public void setRolePermList(List<RolePermission> rolePermList) {
		this.rolePermList = rolePermList;
	}

}
