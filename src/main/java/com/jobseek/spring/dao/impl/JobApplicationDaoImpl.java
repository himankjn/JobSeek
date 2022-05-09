/**
 * 
 */
package com.jobseek.spring.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobseek.spring.dao.JobApplicationDao;
import com.jobseek.spring.entity.JobApplication;
import com.jobseek.spring.entity.JobPosting;
import com.jobseek.spring.entity.JobSeeker;

import java.util.List;

/**
 * @author himank
 *
 */
@Service
@Transactional
public class JobApplicationDaoImpl implements JobApplicationDao {
	private static final Logger logger = LogManager.getLogger(InterestedDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JobApplication apply(int jobseekerId, int jobId, boolean resumeFlag, String resumePath) {
		logger.info("[apply] - "+jobseekerId+ " - "+jobId+" - "+resumeFlag+" - "+resumePath);
		JobApplication ja = new JobApplication();
		try {
			JobSeeker js = entityManager.find(JobSeeker.class, jobseekerId);
			JobPosting jp = entityManager.find(JobPosting.class, jobId);
			ja.setJobPosting(jp);
			ja.setJobSeeker(js);
			ja.setResume(resumeFlag);
			if (!resumePath.equals(null)) {
				ja.setResumePath(resumePath);
			}
			ja.setState(0);
			entityManager.persist(ja);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - apply] - "+ja.toString());
		return ja;
	}

	@Override
	public boolean cancel(int jobAppId) {
		logger.info("[cancel] - "+jobAppId);
		JobApplication ja = getJobApplication(jobAppId);
		if (ja != null) {
			logger.info("[RESULT - cancel] - "+true);
			entityManager.remove(ja);
			return true;
		}
		logger.info("[RESULT - cancel] - "+false);
		return false;
	}

	public JobApplication getJobApplication(int jobAppId) {
		logger.info("[getJobApplicatino] - "+jobAppId);
		JobApplication ja = null;
		try {
			ja = entityManager.find(JobApplication.class, jobAppId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - getJobApplication] - "+ja.toString());
		return ja;
	}
	@Override
	public List<?> getJobApplicationsbyjobId(int jobId) {
		logger.info("[getJobApplicationsbyjobId] - "+jobId);
		Query query = entityManager.createQuery("SELECT ja.appId,ja.interviewAccepted,ja.interviewFlag,ja.interviewLocation,ja.interviewTime,ja.resume,ja.resumePath,ja.state,ja.jobSeeker.jobseekerId,ja.jobSeeker.firstName,ja.jobSeeker.lastName,ja.jobposting.jobId FROM JobApplication ja WHERE ja.jobposting.jobId = :jobId");
		query.setParameter("jobId",jobId);
		List<?> querylist = query.getResultList();
		logger.info("[RESULT - getJobApplicationbyjobId] - "+querylist.toString());

		return querylist;
	}
	@Override
	public List<?> getJobApplicationsbyjobIdAndUserId(int jobId,int userId){
		logger.info("[getJobApplicationsbyjobIdAndUserId] - "+jobId+" - "+userId);
		Query query = entityManager.createQuery("SELECT ja.appId,ja.interviewAccepted,ja.interviewFlag,ja.interviewLocation,ja.interviewTime,ja.resume,ja.resumePath,ja.state,ja.jobSeeker.jobseekerId,ja.jobSeeker.firstName,ja.jobSeeker.lastName,ja.jobposting.jobId FROM JobApplication ja WHERE ja.jobposting.jobId = :jobId AND ja.jobSeeker.jobseekerId=:userId");
		query.setParameter("jobId",jobId);
		query.setParameter("userId",userId);
		List<?> querylist = query.getResultList();
		logger.info("[RESULT - getJobApplicationsbyjobIdAndUserId] - "+querylist.toString());
		return querylist;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jobseek.spring.dao.JobApplicationDao#modifyJobApplicationStatus(int)
	 */
	@Override
	public JobApplication modifyJobApplicationStatus(int jobAppId, int state) {
		logger.info("[modifyJobApplicationStatus] - "+jobAppId+" - "+state);
		JobApplication ja = null;
		ja = getJobApplication(jobAppId);
		try {
			if (ja != null) {
				ja.setState(state);
				entityManager.merge(ja);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESUTL - modifyJobApplicationStatus] - "+ja.toString());
		return ja;
	}

	@Override
	public JobApplication updateApplication(JobApplication ja) {
		logger.info("[updateApplication] - "+ja.toString());
		entityManager.merge(ja);
		logger.info("[RESULT - updateApplication] - "+true);
		return null;
	}
}
