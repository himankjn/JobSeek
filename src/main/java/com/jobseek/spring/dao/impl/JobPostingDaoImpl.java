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

import com.jobseek.spring.dao.JobPostingDao;
import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.JobPosting;

/**
 * @author himank
 *
 */
@Repository
@Transactional
@Service
public class JobPostingDaoImpl implements JobPostingDao {
	private static final Logger logger = LogManager.getLogger(InterestedDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JobPosting createJobPosting(JobPosting job, int cid) {
		logger.info("[createJobPosting] - "+job.toString()+" - "+cid);
		try {
			System.out.println("1");
			Company c = entityManager.find(Company.class, cid);
			job.setCompany(c);
			System.out.println("2");
			entityManager.persist(job);
			System.out.println("3");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - createJobPosting] - "+job.toString());
		return job;
	}

	@Override
	public List<Integer> getCompanyJobPostingIds(int compId) {
		logger.info("]getCompanyJobPostingIds] - "+compId);
		JobPosting j = null;
		Query query = entityManager.createQuery("SELECT jp.jobId FROM JobPosting jp where jp.company.companyId=:compId");
		query.setParameter("compId",compId);
		List<Integer> querylist = query.getResultList();
		logger.info("[RESULT - getCompanyJobPostingIds] - "+querylist.toString());
		return querylist;
	}
	@Override
	public JobPosting getJobPosting(int id) {
		logger.info("[getJobPosting] - "+id);
		JobPosting j = null;

		j = entityManager.find(JobPosting.class, id);
		logger.info("[RESULT - getJobPosting] - "+j.toString());
		return j;
	}

	@Override
	public boolean deleteJobPosting(int id) {
		logger.info("[deleteJobPosting] - "+id);
		JobPosting p = getJobPosting(id);
		if (p != null) {
			entityManager.remove(p);
		} else {
			logger.info("[RESULT - deleteJobPosting] - "+false);
			return false;
		}
		logger.info("[RESULT - deleteJobPosting] - "+true);
		return true;
	}

	@Override
	public JobPosting updateJobPosting(JobPosting p) {
		logger.info("[updateJobPosting] - "+p.toString());
		JobPosting p1 = getJobPosting(p.getJobId());
		p1.setDescription(p.getDescription());
		p1.setLocation(p.getLocation());
		p1.setResponsibilities(p.getResponsibilities());
		p1.setSalary(p.getSalary());
		p1.setState(p.getState());
		p1.setTitle(p.getTitle());
		try {
			if (p1 != null) {
				entityManager.merge(p1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - updateJobPosting] - "+p1.toString());
		return p1;
	}
}