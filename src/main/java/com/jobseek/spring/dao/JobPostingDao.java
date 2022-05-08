package com.jobseek.spring.dao;

import com.jobseek.spring.entity.Interested;
import com.jobseek.spring.entity.JobPosting;

import java.util.List;


/**
 * @author himank
 *
 */
public interface JobPostingDao {
	
	/**
	 * @param job
	 * @param cid
	 * @return New JobPosting
	 * @throws Exception 
	 */
	JobPosting createJobPosting(JobPosting job, int cid);

	List<Integer> getCompanyJobPostingIds(int compId);
	/**
	 * @param id
	 * @return Requested JobPosting
	 */
	JobPosting getJobPosting(int id);

	/**
	 * @param id
	 * @return True if JobPosting is Deleted
	 */
	boolean deleteJobPosting(int id);

	/**
	 * @param job
	 * @return Updated Job Posting
	 */
	JobPosting updateJobPosting(JobPosting job);

}