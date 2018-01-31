package com.scholarum.common.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.scholarum.common.bean.SearchInstitutionRequest;
import com.scholarum.common.bean.SearchInstitutionResponse;
import com.scholarum.common.entity.Institution;
import com.scholarum.common.util.CommonUtil;
import com.scholarum.common.util.DateUtil;

@Component
public class InstitutionDao {

	@PersistenceContext
	private EntityManager em;

	private final int pageSize = 15;

	public SearchInstitutionResponse findInstitutions(SearchInstitutionRequest searchReq) {
		SearchInstitutionResponse searchRes = new SearchInstitutionResponse();
		StringBuilder squery = new StringBuilder("SELECT i FROM Institution i WHERE");
		if (!CommonUtil.isEmpty(searchReq.getEmail())) {
			squery.append(" i.name LIKE :name AND");
		}
		if (!CommonUtil.isEmpty(searchReq.getEmail())) {
			squery.append(" i.email LIKE :email AND");
		}
		if (!CommonUtil.isEmpty(searchReq.getMobile())) {
			squery.append(" i.mobile = :mobile AND");
		}
		if (!CommonUtil.isNull(searchReq.getCreatedFrom())) {
			squery.append(" i.created between :startDate AND :endDate AND");
		}
		squery.append(" i.id > 0 ORDER BY i.id DESC");

		TypedQuery<Institution> query = em.createQuery(squery.toString(), Institution.class);

		if (!CommonUtil.isEmpty(searchReq.getName())) {
			query.setParameter("name", searchReq.getName());
		}
		if (!CommonUtil.isEmpty(searchReq.getEmail())) {
			query.setParameter("email", searchReq.getEmail());
		}
		if (!CommonUtil.isEmpty(searchReq.getMobile())) {
			query.setParameter("mobile", searchReq.getMobile());
		}
		if (!CommonUtil.isNull(searchReq.getCreatedFrom())) {
			query.setParameter("startDate", DateUtil.getStartOfDay(searchReq.getCreatedFrom()));
			query.setParameter("endDate", DateUtil.getEndOfDay(searchReq.getCreatedTo()));
		}
		searchRes.setNoOfPages(query.getResultList().size());
		query.setFirstResult((searchReq.getPageNo() - 1) * pageSize);
		query.setMaxResults(pageSize);
		searchRes.setPageNo(searchReq.getPageNo());
		searchRes.setInstiList(query.getResultList());
		return searchRes;
	}

}