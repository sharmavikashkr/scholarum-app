package com.scholarum.common.bean;

import java.util.List;

import com.scholarum.common.entity.Institution;

public class SearchInstitutionResponse {

	private int pageNo;
	private int noOfPages;
	private List<Institution> instiList;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public List<Institution> getInstiList() {
		return instiList;
	}

	public void setInstiList(List<Institution> instiList) {
		this.instiList = instiList;
	}

}
