package com.jobseek.spring.dao;

import java.util.List;

import com.jobseek.spring.entity.Interested;

/**
 * @author himank
 *
 */
public interface InterestedDao {

	/**
	 * @param in
	 * @return Created interest
	 * @throws Exception
	 */
	public Interested createInterest(Interested in) throws Exception;

	/**
	 * @param id
	 * @return true if interest has been deleted
	 */
	public boolean deleteInterest(int id);

	/**
	 * @param id
	 * @return Interest
	 */
	public Interested getInterest(int id);

	/**
	 * @param jobId
	 * @param userId
	 * @return List of the job ids of the jobs the user is interested in
	 */
	public List<?> getInterestedJobId(int jobId, int userId);
	
	/**
	 * @param userId
	 * @return List of the job ids of the jobs the user is interested in
	 */
	public List<Integer> getAllInterestedJobId(int userId); 
}
