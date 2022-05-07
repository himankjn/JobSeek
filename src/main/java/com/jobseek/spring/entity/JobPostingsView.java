/**
 * 
 */
package com.jobseek.spring.entity;

import javax.persistence.*;

/**
 * @author himank
 *
 */


public class JobPostingsView {

	@Id
	@Column(name = "jobId")
	private int jobId;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "responsibilities")
	private String responsibilites;
	@Column(name = "location")
	private String location;
	@Column(name = "salary")
	private String salary;
	@Column(name = "companyId")
	private int companyId;
	@Column(name = "companyName")
	private String companyName;
	@Column(name = "keywords")
	private String keywords;
	@Column(name = "state")
	private int state;

	/**
	 * @return JobId
	 */
	public int getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 */
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Responsibilites
	 */
	public String getResponsibilites() {
		return responsibilites;
	}

	/**
	 * @param responsibilites
	 */
	public void setResponsibilites(String responsibilites) {
		this.responsibilites = responsibilites;
	}

	/**
	 * @return Location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return Salary
	 */
	public String getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}

	/**
	 * @return companyId
	 */
	public int getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return Company Name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return keywords for job
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return state of job
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            of job
	 */
	public void setState(int state) {
		this.state = state;
	}

}
