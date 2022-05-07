/**
 * 
 */
package com.jobseek.spring.dao;

import com.jobseek.spring.entity.JobApplication;

import java.util.List;

/**
 * @author himank
 *
 */
public interface JobApplicationDao {

	/**
	 * @param jobAppId
	 * @return True if the application was successfully cancelled
	 */
	boolean cancel(int jobAppId);

	/**
	 * @param jobseekerId
	 * @param jobId
	 * @param resumeFlag
	 * @param resumePath
	 * @return The newly created job application
	 */
	JobApplication apply(int jobseekerId, int jobId, boolean resumeFlag, String resumePath);

	/**
	 * @param jobAppId
	 * @return Required job application
	 */
	JobApplication getJobApplication(int jobAppId);

	List<?> getJobApplicationsbyjobId(int jobId);
	List<?> getJobApplicationsbyjobIdAndUserId(int jobId,int userId);
	/**
	 * @param state
	 * @return Modified job application
	 */
	JobApplication modifyJobApplicationStatus(int jobAppId, int state);
	
	/**
	 * @param ja
	 * @return Updated job application
	 */
	JobApplication updateApplication(JobApplication ja);

}
