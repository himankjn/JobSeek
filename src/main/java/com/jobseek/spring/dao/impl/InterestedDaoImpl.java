package com.jobseek.spring.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobseek.spring.dao.InterestedDao;
import com.jobseek.spring.entity.Interested;

/**
 * @author himank
 *
 */
@Repository
@Transactional
@Service
public class InterestedDaoImpl implements InterestedDao {
	private static final Logger logger = LogManager.getLogger(InterestedDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * @Override public List<String> PasswordLookUp(String emailid) {
	 * 
	 * Query query = entityManager.
	 * createQuery("SELECT password FROM Company c WHERE c.companyUser = :emailId "
	 * ); query.setParameter("emailId", emailid); List<String> list = new
	 * ArrayList<String>(); List<?> querylist = query.getResultList(); for
	 * (Iterator<?> iterator = querylist.iterator(); iterator.hasNext();) {
	 * String pwd = (String) iterator.next(); System.out.println(pwd +
	 * " is the password"); list.add(pwd); }
	 * System.out.println("list :::::::::::::::::::::::::::::       " + list);
	 * return list; }
	 */
	@Override
	public Interested createInterest(Interested in) throws Exception {
		logger.info("[createInterest] - "+in.toString());
		try {
			entityManager.persist(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - createInterest] - "+in.toString());
		return in;
	}

	@Override
	public Interested getInterest(int id) {
		logger.info("[getInterest] - "+id);
		Interested is = null;

		is = entityManager.find(Interested.class, id);
		logger.info("[RESULT - getInterest] - "+is.toString());
		return is;
	}

	@Override
	public boolean deleteInterest(int id) {
		logger.info("[deleteInterest] - "+id);
		Interested i = getInterest(id);
		if (i != null) {
			entityManager.remove(i);
		} else {
			logger.info("[RESULT - deleteInterest] - "+false);
			return false;
		}
		logger.info("[RESULT - deleteInterest] - "+true);
		return true;
	}
	
	/**
	 * @param jobId
	 * @param userId
	 * @return Job Id for interested list
	 */
	public List<?> getInterestedfromJobIdUserId(int jobId, int userId) {
		logger.info("[getInterestedfromJobIdUserId] - "+jobId+ " - "+userId);
		Query query = entityManager.createQuery("SELECT ID FROM Interested jd WHERE jd.jobId = :jobid and jd.jobSeekerId =:userid");
		query.setParameter("jobid", jobId);
		query.setParameter("userid", userId);
		List<?> querylist = query.getResultList();
		logger.info("[RESULT - getInterestedfromJobIdUserId]"+querylist.toString());
		return querylist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllInterestedJobId(int userId) {
		logger.info("[getAllInterestedJobId] - "+userId);
		Query query = entityManager.createQuery("SELECT jobId FROM Interested jd WHERE jd.jobSeekerId =:userid");
		query.setParameter("userid", userId);
		List<Integer> querylist = query.getResultList();
		logger.info("[RESULT - getAllInterestedJobId] - "+querylist.toString());
		return querylist;
	}

}