package com.scholarum.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scholarum.admin.service.ManageInstitutionService;
import com.scholarum.common.bean.SearchInstitutionRequest;
import com.scholarum.common.bean.SearchInstitutionResponse;
import com.scholarum.common.entity.InstitutionUser;
import com.scholarum.common.util.RoleUtil;

@RestController
@RequestMapping("/manage/institution")
public class ManageInstitutionController {

	@Autowired
	private ManageInstitutionService mngInstiSer;

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping("/new")
	public void newInstitution(@RequestBody InstitutionUser instiUser) {
		mngInstiSer.newInstitution(instiUser);
	}

	@PreAuthorize(RoleUtil.SCHOLARUM_ADMIN_AUTH)
	@RequestMapping("/search")
	public SearchInstitutionResponse searchInsti(@RequestBody SearchInstitutionRequest searchReq) {
		return mngInstiSer.searchInstitutions(searchReq);
	}
}
