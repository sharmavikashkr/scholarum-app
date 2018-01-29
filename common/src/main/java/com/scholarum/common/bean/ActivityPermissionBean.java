package com.scholarum.common.bean;

import com.scholarum.common.entity.Activity;

public class ActivityPermissionBean {

	private Activity activity;

	private boolean add;

	private boolean view;

	private boolean edit;

	public ActivityPermissionBean(Activity activity, boolean add, boolean view, boolean edit, boolean delete) {
		super();
		this.activity = activity;
		this.add = add;
		this.view = view;
		this.edit = edit;
		this.delete = delete;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	private boolean delete;

}
