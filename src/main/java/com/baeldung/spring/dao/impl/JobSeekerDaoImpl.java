/**
 * 
 */
package com.baeldung.spring.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.baeldung.spring.entity.JobPosting;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.dao.JobSeekerDao;
import com.baeldung.spring.entity.JobPostingsView;
import com.baeldung.spring.entity.JobSeeker;

/**
 * @author amayd
 *
 */
@Repository
@Transactional
@Service
public class JobSeekerDaoImpl implements JobSeekerDao {

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baeldung.spring.dao.JobSeekerDao#searchJobs(com.baeldung.spring.
	 * entity.JobPostingsView)
	 */
	@Override
	public List<?> filterJobs(JobPosting jpv, List<?> jobIds) {
		boolean locationFlag = false, companyFlag = false, salaryFlag = false;
		String selectQuery = "SELECT jpv.jobId, jpv.title, jpv.description, jpv.responsibilties, jpv.location, jpv.salary, jpv.company.companyId, jpv.state, jpv.company.companyName FROM JobPosting jpv WHERE jpv.jobId in :jobIds";

		String[] salaries = null;


		if (null != jpv.getLocation()) {
			locationFlag = true;
			selectQuery = selectQuery.concat(" AND jpv.location IN (:locations) ");
		}


//		if (null != jpv.getCompany() && null != jpv.getCompany().getCompanyName()) {
//			companyFlag = true;
//			selectQuery = selectQuery.concat(" AND jpv.companyName IN (:companies) ");
//		}
//
//

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


//		if (companyFlag) {
//			String[] companyArray = jpv.getCompany().getCompanyName().split(",");
//			List<String> companyList = Arrays.asList(companyArray);
//			query.setParameter("companies", companyList);
//		}

		if (salaryFlag) {
			salaries = jpv.getSalary().split(",");
			int sal1=Integer.parseInt(salaries[0]);
			int sal2=Integer.parseInt(salaries[1]);
			query.setParameter("salary1", sal1);
			query.setParameter("salary2", sal2);
		}

		List<?> jpListRes = query.getResultList();


		return jpListRes;

	}

	@Override
	public JobSeeker createJobSeeker(JobSeeker jseeker) throws Exception {
		try {
			entityManager.persist(jseeker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jseeker;
	}

	@Override
	public JobSeeker getJobSeeker(int id) {
		JobSeeker js = null;
		try {
			js = entityManager.find(JobSeeker.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}

	@Override
	public JobSeeker updateJobSeeker(JobSeeker js) {
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
		return jobseeker;
	}

	@Override
	public List<String> PasswordLookUp(String emailid) {

		Query query = entityManager.createQuery("SELECT password FROM JobSeeker js WHERE js.emailId = :emailId");
		query.setParameter("emailId", emailid);
		List<String> list = new ArrayList<String>();
		List<?> querylist = query.getResultList();
		for (Iterator<?> iterator = querylist.iterator(); iterator.hasNext();) {
			String pwd = (String) iterator.next();
			list.add(pwd);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baeldung.spring.dao.JobSeekerDao#verify(com.baeldung.spring.entity.
	 * JobSeeker)
	 */
	@Override
	public void verify(JobSeeker j) {
		JobSeeker jobseeker = getJobSeeker(j.getJobseekerId());
		jobseeker.setVerified(true);
		try {
			if (jobseeker != null) {
				entityManager.merge(jobseeker);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baeldung.spring.dao.JobSeekerDao#searchJobs(java.lang.String)
	 */
	@Override
	public List<?> searchJobs(String searchString) {
		searchString = "%" + searchString + "%";


		searchString = searchString.replaceAll(" ", "% %");
		String searchStringArray[] = searchString.split(" ");
		//System.out.println("array len="+searchStringArray.length);
		String selectQuery = "SELECT jp.jobId FROM JobPosting jp";
		if (!searchString.isEmpty()) {
			System.out.println("never come here");
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
		return list;
	}
	
	
	@Override
	public List<Integer> getUserIdFromEmail(String emailid) {

		Query query = entityManager.createQuery("SELECT jobseekerId FROM JobSeeker js WHERE js.emailId = :emailId");
		query.setParameter("emailId", emailid);
		List<Integer> list = new ArrayList<Integer>();
		List<?> querylist = query.getResultList();
		for (Iterator<?> iterator = querylist.iterator(); iterator.hasNext();) {
			int uid = (int) iterator.next();
			list.add(uid);
		}
		return list;
	}



}
