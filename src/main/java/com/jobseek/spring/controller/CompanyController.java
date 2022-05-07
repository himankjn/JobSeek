/**
 * 
 */
package com.jobseek.spring.controller;

import java.util.ArrayList;
import java.util.List;

import com.jobseek.spring.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.JobPosting;

/**
 * @author himank
 *
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	JobPostingDao jobDao;

	@Autowired
	JobApplicationDao jobappDao;

	@Autowired
	InterestedDao interestedDao;
	
	@Autowired
	JobSeekerDao jobSeekerDao;

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String showJobSeeker(@RequestParam("id") int id, Model model){
		
		Company company = companyDao.getCompany(id);
		
		model.addAttribute("company", company);
		return "companyprofile"; 
	}
	
	@RequestMapping(value = "/showjob", method = RequestMethod.POST)
	public String showJob(@RequestParam("cid") String cid, @RequestParam("jobId") String jobId, Model model) {
		JobPosting p1 = jobDao.getJobPosting(Integer.parseInt(jobId));
		Company company = companyDao.getCompany(Integer.parseInt(cid));
		model.addAttribute("job", p1);
		model.addAttribute("company", company);
		return "jobprofile";
	}
	
	@RequestMapping(value = "/showapplicants", method = RequestMethod.POST)
	public String showJobApplicants(@RequestParam("jobId") String jobId, Model model) {

		JobPosting p1 = jobDao.getJobPosting(Integer.parseInt(jobId));
		List<?> Ja= jobappDao.getJobApplicationsbyjobId(Integer.parseInt(jobId));
		Company companyId=jobDao.getJobPosting(Integer.parseInt(jobId)).getCompany();
		model.addAttribute	("job", p1);
		model.addAttribute("jobapps",Ja);
		model.addAttribute("jobapplen",Ja.size());
		model.addAttribute("company",companyId);
		return "jobapplicants";
	}
	
	/**
	 * @param companyId
	 * @param state
	 * @return List of jobs posted by the company
	 */
	@RequestMapping(value = "/getjobs", method = RequestMethod.POST)
	public String getJobs(@RequestParam("companyId") String companyId, Model model) {
		List<?> companyJobPostings = new ArrayList<String>();
		companyJobPostings = companyDao.getJobsByCompany(Integer.parseInt(companyId));
		Company company = companyDao.getCompany(Integer.parseInt(companyId));
		System.out.println("bye"+companyJobPostings);
		model.addAttribute("jobs", companyJobPostings);
		model.addAttribute("company", company);
		
		return "companyjobs";
	}

}
