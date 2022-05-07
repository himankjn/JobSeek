/**
 * 
 */
package com.jobseek.spring.dao;

import java.util.List;

import com.jobseek.spring.entity.JobPosting;
import com.jobseek.spring.entity.JobSeeker;

/**
 * @author himank
 *
 */
public interface JobSeekerDao {
	
	/**
	 * @param jpv
	 * @param jobIds 
	 * @return Job Postings according to the provided parameter
	 */
	public List<?> filterJobs(JobPosting jpv, List<?> jobIds);

	/**
	 * @param job
	 * @return new job seeker
	 * @throws Exception
	 */
	public JobSeeker createJobSeeker(JobSeeker job) throws Exception;

	/**
	 * @param js
	 * @return updated job seeker
	 */
	public JobSeeker updateJobSeeker(JobSeeker js);

	/**
	 * @param id
	 * @return job seeker
	 */
	public JobSeeker getJobSeeker(int id);

	/**
	 * @param emailid
	 * @return password
	 */
	public List<String> PasswordLookUp(String emailid);


	/**
	 * @param j
	 */
	public void verify(JobSeeker j);
	
	/**
	 * @param searchString
	 * @return Jobs for that search string
	 */
	public List<?> searchJobs(String searchString);

	/**
	 * @param emailid
	 * @return userId
	 */
	public List<Integer> getUserIdFromEmail(String emailid);
}
