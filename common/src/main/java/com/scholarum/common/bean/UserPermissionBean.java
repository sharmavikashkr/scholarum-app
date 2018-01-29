package com.scholarum.common.bean;

import java.util.List;

import com.scholarum.common.entity.Module;

public class UserPermissionBean {

	private Module module;
	private List<ActivityPermissionBean> activityPermList;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<ActivityPermissionBean> getActivityPermList() {
		return activityPermList;
	}

	public void setActivityPermList(List<ActivityPermissionBean> activityPermList) {
		this.activityPermList = activityPermList;
	}

}
