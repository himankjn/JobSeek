/**
 * 
 */
package com.jobseek.spring.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jobseek.spring.entity.JobPosting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobseek.spring.dao.JobSeekerDao;
import com.jobseek.spring.entity.JobSeeker;

/**
 * @author himank
 *
 */
@Repository
@Transactional
@Service
public class JobSeekerDaoImpl implements JobSeekerDao {
	private static final Logger logger = LogManager.getLogger(InterestedDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jobseek.spring.dao.JobSeekerDao#searchJobs(com.jobseek.spring.
	 * entity.JobPostingsView)
	 */
	@Override
	public List<?> filterJobs(JobPosting jpv, List<?> jobIds) {
		logger.info("[filterJobs] - "+jpv.toString()+" - "+jobIds.toString());
		boolean locationFlag = false, companyFlag = false, salaryFlag = false;
		String selectQuery = "SELECT jpv.jobId, jpv.title, jpv.description, jpv.responsibilties, jpv.location, jpv.salary, jpv.company.companyId, jpv.state, jpv.company.companyName FROM JobPosting jpv WHERE jpv.jobId in :jobIds";

		String[] salaries = null;


		if (null != jpv.getLocation()) {
			locationFlag = true;
			selectQuery = selectQuery.concat(" AND jpv.location IN (:locations) ");
		}

		if (null != jpv.getCompany() && null != jpv.getCompany().getCompanyName()) {
			companyFlag = true;
			selectQuery = selectQuery.concat(" AND jpv.company.companyName IN (:companies) ");
		}



		if (null != jpv.getSalary()) {
			salaryFlag = true;
			selectQuery = selectQuery.concat(" AND cast(jpv.salary as int) >= :salary1 AND cast(jpv.salary as int)<= :salary2 ");
		}

		Query query = entityManager.createQuery(selectQuery);
		query.setParameter("jobIds", jobIds);
		if (locationFlag) {
			String[] location = jpv.getLocation().split(",");

			List<String> locationList = Arrays.asList(location);
			query.setParameter("locations", locationList);
		}


		if (companyFlag) {
			String[] companyArray = jpv.getCompany().getCompanyName().split(",");
			List<String> companyList = Arrays.asList(companyArray);
			query.setParameter("companies", companyList);
		}

		if (salaryFlag) {
			salaries = jpv.getSalary().split(",");
			int sal1=Integer.parseInt(salaries[0]);
			int sal2=Integer.parseInt(salaries[1]);
			query.setParameter("salary1", sal1);
			query.setParameter("salary2", sal2);
		}

		List<?> jpListRes = query.getResultList();

		logger.info("[RESULT - filterJobs] - "+jpListRes.toString());
		return jpListRes;

	}

	@Override
	public JobSeeker createJobSeeker(JobSeeker jseeker) throws Exception {
		logger.info("[createJobSeeker] - "+jseeker.toString());
		try {
			entityManager.persist(jseeker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - createJobSeeker] - "+jseeker.toString());
		return jseeker;
	}

	@Override
	public JobSeeker getJobSeeker(int id) {
		logger.info("[getJobSeeker] - "+id);
		JobSeeker js = null;
		try {
			js = entityManager.find(JobSeeker.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - getJobSeeker] - "+js.toString());
		return js;
	}

	@Override
	public JobSeeker updateJobSeeker(JobSeeker js) {
		logger.info("[updateJobSeeker] - "+ js.toString());
		JobSeeker jobseeker = getJobSeeker(js.getJobseekerId());
		jobseeker.setEmailId(js.getEmailId());
		jobseeker.setFirstName(js.getFirstName());
		jobseeker.setLastName(js.getLastName());
		jobseeker.setHighestEducation(js.getHighestEducation());
		jobseeker.setPassword(js.getPassword());
		jobseeker.setSkills(js.getSkills());
		jobseeker.setWorkEx(js.getWorkEx());
		try {
			if (jobseeker != null) {
				entityManager.merge(jobseeker);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - updateJobSeeker] - "+jobseeker.toString());
		return jobseeker;
	}

	@Override
	public List<String> PasswordLookUp(String emailid) {
		logger.info("[PasswordLookUp] - "+emailid);
		Query query = entityManager.createQuery("SELECT password FROM JobSeeker js WHERE js.emailId = :emailId");
		query.setParameter("emailId", emailid);
		List<String> list = new ArrayList<String>();
		List<?> querylist = query.getResultList();
		for (Iterator<?> iterator = querylist.iterator(); iterator.hasNext();) {
			String pwd = (String) iterator.next();
			list.add(pwd);
		}
		logger.info("[RESULT - PasswordLookUp] - "+list.toString());
		return list;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jobseek.spring.dao.JobSeekerDao#verify(com.jobseek.spring.entity.
	 * JobSeeker)
	 */
	@Override
	public void verify(JobSeeker j) {
		logger.info("[verify] - "+j.toString());
		JobSeeker jobseeker = getJobSeeker(j.getJobseekerId());
		jobseeker.setVerified(true);
		try {
			if (jobseeker != null) {
				entityManager.merge(jobseeker);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[RESULT - verify] - "+true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jobseek.spring.dao.JobSeekerDao#searchJobs(java.lang.String)
	 */
	@Override
	public List<?> searchJobs(String searchString) {
		logger.info("[searchJobs] - "+searchString);
		searchString = "%" + searchString + "%";


		searchString = searchString.replaceAll(" ", "% %");
		String searchStringArray[] = searchString.split(" ");
		//System.out.println("array len="+searchStringArray.length);
		String selectQuery = "SELECT jp.jobId FROM JobPosting jp";
		if (!searchString.isEmpty()) {
			selectQuery = selectQuery.concat(" WHERE ");
		}

		for (int i = 0; i < searchStringArray.length; i++) {
			selectQuery = selectQuery.concat("jp.keywords LIKE :searchParam" + i);
			if (i != searchStringArray.length - 1) {
				selectQuery = selectQuery.concat(" AND ");
			}
		}

		Query query = entityManager.createQuery(selectQuery);
		for (int i = 0; i < searchStringArray.length; i++) {
			query.setParameter("searchParam" + i, searchStringArray[i]);
		}
		
		List<?> list = query.getResultList();
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("RESUTL - searchJobs] - "+list.toString());
		return list;
	}
	
	
	@Override
	public List<Integer> getUserIdFromEmail(String emailid) {
		logger.info("[getUserIdFromEmail] - "+emailid);
		Query query = entityManager.createQuery("SELECT jobseekerId FROM JobSeeker js WHERE js.emailId = :emailId");
		query.setParameter("emailId", emailid);
		List<Integer> list = new ArrayList<Integer>();
		List<?> querylist = query.getResultList();
		for (Iterator<?> iterator = querylist.iterator(); iterator.hasNext();) {
			int uid = (int) iterator.next();
			list.add(uid);
		}
		logger.info("[RESULT - getUserIdFromEmail] - "+list.toString());
		return list;
	}



}
