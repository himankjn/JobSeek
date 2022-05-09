package com.jobseek.spring.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobseek.spring.dao.CompanyDao;
import com.jobseek.spring.entity.Company;

/**
 * @author himank
 *
 */
@Repository
@Transactional
@Service
public class CompanyDaoImpl implements CompanyDao {

	private static final Logger logger = LogManager.getLogger(CompanyDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<String> PasswordLookUp(String emailid) {
		logger.info("[PasswordLookUp] - " + emailid);

		Query query = entityManager.createQuery("SELECT password FROM Company c WHERE c.companyUser = :emailId ");
		query.setParameter("emailId", emailid);
		List<String> list = new ArrayList<String>();
		List<?> querylist = query.getResultList();
		for (Iterator<?> iterator = querylist.iterator(); iterator.hasNext();) {
			String pwd = (String) iterator.next();
			System.out.println(pwd + " is the password");
			list.add(pwd);
		}
		System.out.println("list :::::::::::::::::::::::::::::       " + list);
		logger.info("[Result - PasswordLookUp] - " + list);
		return list;
	}
	
	@Override
	public List<Integer> getCompanyIdFromEmail(String emailid) {
		logger.info("[getCompanyIdFromEmail] - "+emailid);

		Query query = entityManager.createQuery("SELECT companyId FROM Company c WHERE c.companyUser = :emailId ");
		query.setParameter("emailId", emailid);
		List<Integer> list = new ArrayList<Integer>();
		List<?> querylist = query.getResultList();
		for (Iterator<?> iterator = querylist.iterator(); iterator.hasNext();) {
			int cid = (int) iterator.next();
			list.add(cid);
		}
		logger.info("[RESULT - getCompanyIdFromEmail] - ",list);
		return list;
	}

	@Override
	public Company createCompany(Company c) throws Exception {
		logger.info("[createCompany] - "+c.toString());

		try {
			entityManager.persist(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("[RESULT - createCompany] - "+c.toString());
		return c;
	}

	@Override
	public Company getCompany(int id) {
		logger.info("[getCompany] - "+id);
		Company c = null;

		c = entityManager.find(Company.class, id);
		logger.info("[RESULT - getCompany] - "+c.toString());
		return c;
	}

	@Override
	public Company updateCompany(Company js) {
		logger.info("[updateCompany] - "+js.toString());
		Company c = getCompany(js.getCompanyId());
		c.setCompanyName(js.getCompanyName());
		c.setCompanyUser(js.getCompanyUser());
		c.setDescription(js.getDescription());
		c.setHeadquarters(js.getHeadquarters());
		c.setVerified(js.isVerified());
		try {
			if (c != null) {
				entityManager.merge(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("[RESULT - updateCompany] - "+c.toString());
		return c;
	}

	/* (non-Javadoc)
	 * @see com.jobseek.spring.dao.CompanyDao#verify(com.jobseek.spring.entity.Company)
	 */
	@Override
	public void verify(Company c) {
		logger.info("[verify] - "+c.toString());
		Company c1 = getCompany(c.getCompanyId());
		c1.setVerified(true);
		logger.info("[RESULT - verify] - "+c1.toString());
		try {
			if (c != null) {
				entityManager.merge(c1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.jobseek.spring.dao.CompanyDao#getJobsByCompany(java.lang.String, int)
	 */
	@Override
	public List<?> getJobsByCompany(int companyId) {
		logger.info("[getJobsByCompany] - "+companyId);
		Query query = entityManager.createQuery("SELECT jp.jobId, jp.title, jp.description, jp.responsibilties, jp.location, jp.salary, jp.state, jp.company.companyId, jp.company.companyName FROM JobPosting jp WHERE jp.company.companyId = :companyId");
		query.setParameter("companyId", companyId);
		List<?> querylist = query.getResultList();
		logger.info("[RESULT - getJobsByCompany] - "+querylist.toString());
		return querylist;
	}

}