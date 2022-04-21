/**
 * 
 */
package com.baeldung.spring.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baeldung.spring.dao.CompanyDao;
import com.baeldung.spring.dao.InterestedDao;
import com.baeldung.spring.dao.JobPostingDao;
import com.baeldung.spring.dao.JobSeekerDao;
import com.baeldung.spring.dao.impl.JobSeekerDaoImpl;
import com.baeldung.spring.entity.Company;
import com.baeldung.spring.entity.Interested;
import com.baeldung.spring.entity.JobApplication;
import com.baeldung.spring.entity.JobPosting;
import com.baeldung.spring.entity.JobPostingsView;
import com.baeldung.spring.entity.JobSeeker;
import com.baeldung.spring.mail.EmailServiceImpl;

/**
 * @author ashay
 *
 */
@Controller

@RequestMapping(value = "/")
public class JobSeekerController {

	@Autowired
	JobSeekerDao jobSeekerDao;

	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	InterestedDao interestedDao;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @param searchString
	 * @param locations
	 * @param companies
	 * @param salary
	 * @return Jobs that match the filter criteria
	 */
	@RequestMapping(value = "/searchjobs", method = RequestMethod.GET)
	public String searchJobs(@RequestParam("userId") String userId,
			@RequestParam("searchString") Optional<String> searchString,
			@RequestParam("locations") Optional<String> locations,
			@RequestParam("companies") Optional<String> companies, 
			@RequestParam("min") Optional<String> min,
			@RequestParam("max") Optional<String> max, Model model) {
		JobPosting jpv = new JobPosting();
		Company c=new Company();
		String search = "";
		if (!searchString.equals(Optional.empty())) {
			search = searchString.get();
		}

		System.out.println("here");
		List<?> jobIds = jobSeekerDao.searchJobs(search);
		if(jobIds.size()==0){
			System.out.println("wow yo");
			String message="<div class=\"alert alert-danger\">No jobs available as per your requirements at the moment!</div>";
			model.addAttribute("message",message);
			return "userprofile";
		}

		if ((!locations.equals(Optional.empty())) && (locations.get()!="")) {
			System.out.println("location");
			jpv.setLocation(locations.get());
		}

		if (!companies.equals(Optional.empty()) && companies.get()!="") {
			/*System.out.println("company");
			jpv.getCompany().setCompanyName(companies.get());*/
			jpv.setCompany(c);
			jpv.getCompany().setCompanyName(companies.get());
		}

		if (!min.equals(Optional.empty()) && !max.equals(Optional.empty())) {
		String salary = min.get()+","+max.get();
		jpv.setSalary(salary);
		}

		List<?> jp = jobSeekerDao.filterJobs(jpv, jobIds);
		System.out.println(jp);

		JobSeeker jobseeker = jobSeekerDao.getJobSeeker(Integer.parseInt(userId));
		
		model.addAttribute("jobs", jp);
		model.addAttribute("seeker", jobseeker);


		//return userprofile
		return "jobsearch";
	}

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	JobPostingDao jobDao;
	
	@RequestMapping(value = "/showjob", method = RequestMethod.GET)
	public String showJob(@RequestParam("userId") String userId, @RequestParam("jobId") String jobId, Model model) {
		
		JobPosting job = jobDao.getJobPosting(Integer.parseInt(jobId));
		Company company = job.getCompany();
		JobSeeker seeker = jobSeekerDao.getJobSeeker(Integer.parseInt(userId));
		List<?> ij = interestedDao.getAllInterestedJobId(Integer.parseInt(userId));
		int i = 0,j=0;
		if(ij.contains(Integer.parseInt(jobId))){
			i = 1;
		}
		
		List<Integer> il = getAppliedJobs(userId);
		if(il.contains(Integer.parseInt(jobId))){
			j = 1;
		}

		
		
		model.addAttribute("job", job);
		model.addAttribute("seeker", seeker);
		model.addAttribute("company", company);
		model.addAttribute("interested", i);
		model.addAttribute("applied", j);
		
		return "userjobprofile";
	}



	/**
	 * @param name
	 * @param email
	 * @param password
	 * @param type
	 * @param model
	 * @return newly created job seeker
	 * @throws IOException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public String createJobSeeker(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("type") String type, Model model)
			throws IOException, SQLException {

		int randomPIN = (int) (Math.random() * 9000) + 1000;

		String[] splited = name.split("\\s+");
		try {

			if (type.equals("seeker")) {

				if(jobSeekerDao.getUserIdFromEmail(email).size()>0){

					String message1="<div class=\"alert alert-danger\">User with this Email <strong>already exists!</strong> Please <strong>login</strong> or use another email.</div>";

					model.addAttribute("message1",message1);
					return "register";
				}

				JobSeeker j = new JobSeeker();

				j.setFirstName(splited[0]);
				if(splited.length==1){
					j.setLastName("");
				}
				else j.setLastName(splited[1]);


				j.setPassword(password);
				j.setEmailId(email);
				j.setVerificationCode(randomPIN);
				j.setVerified(false);

				JobSeeker j1 = jobSeekerDao.createJobSeeker(j);

				String verificationUrl = "http://localhost:8095/register/verify?userId=" + j1.getJobseekerId() + "&pin="
						+ randomPIN + "&type=seeker";

				emailService.sendSimpleMessage(email, "Verification Pin", verificationUrl);
				model.addAttribute("name", j1.getFirstName());
				String message2="<div class=\"alert alert-success\">A verification link has been sent to your email address. Please verify! </div>";
				model.addAttribute("message2",message2);
				return "register";

			}

			else {

				Company c = new Company();
				c.setVerified(false);
				c.setVerificationCode(randomPIN);
				c.setCompanyName(name);
				c.setCompanyUser(email);
				c.setPassword(password);
				c.setHeadquarters("head");

				Company c1 = companyDao.createCompany(c);

				String verificationUrl = "http://localhost:8095/register/verify?userId=" + c1.getCompanyId() + "&pin="
						+ randomPIN + "&type=recruiter";


				emailService.sendSimpleMessage(email, "Verification Pin", verificationUrl);
				model.addAttribute("name", c1.getCompanyName());

				String message2="<div class=\"alert alert-success\">A verification link has been sent to your email address. Please verify! </div>";
				model.addAttribute("message2",message2);
				return "register";
			}

		} catch (SQLException se) {
			HttpHeaders httpHeaders = new HttpHeaders();
			Map<String, Object> message = new HashMap<String, Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			message.put("code", "400");
			message.put("msg", "Email Already Exists");
			response.put("BadRequest", message);
			JSONObject json_test = new JSONObject(response);
			String json_resp = json_test.toString();

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			return "error";

		} catch (Exception se) {
			HttpHeaders httpHeaders = new HttpHeaders();

			Map<String, Object> message = new HashMap<String, Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			message.put("code", "400");
			message.put("msg", "Error Occured");
			response.put("BadRequest", message);
			JSONObject json_test = new JSONObject(response);
			String json_resp = json_test.toString();

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			return "error";
		}
	}

	/**
	 * @param id
	 * @param model
	 * @return updated seeker view
	 */

	//Never called
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateSeekerPage(@RequestParam("id") String id, Model model) {

		JobSeeker j1 = new JobSeekerDaoImpl().getJobSeeker(Integer.parseInt(id));
		model.addAttribute("j", j1);

		return "updateSeeker";
	}

	@RequestMapping(value = "/userprofile/{id}", method = RequestMethod.GET)
	public String showJobSeeker(@PathVariable("id") int id, Model model){
		
		JobSeeker jobseeker = jobSeekerDao.getJobSeeker(id);
		
		model.addAttribute("seeker", jobseeker);
		return "userprofile"; 
	}

	/**
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param emailid
	 * @param highesteducation
	 * @param password
	 * @param skills
	 * @param workex
	 * @param model
	 * @return updated userprofile view
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateJobSeeker(@RequestParam("id") String id, @RequestParam("firstname") Optional<String> firstname,
			@RequestParam("lastname") Optional<String> lastname, @RequestParam("emailid") Optional<String> emailid,
			@RequestParam("highesteducation") Optional<String> highesteducation,
			@RequestParam("password") Optional<String> password, @RequestParam("skills") Optional<String> skills,
			@RequestParam("workex") Optional<String> workex, Model model) throws Exception {
		JobSeeker js = new JobSeeker();

		js.setJobseekerId(Integer.parseInt(id));

		if (!emailid.equals(Optional.empty())) {
			System.out.println("emailid done : " + emailid.get() + ":::: " + emailid);
			js.setEmailId(emailid.get());
		}
		if (!firstname.equals(Optional.empty())) {
			System.out.println("fname done");
			js.setFirstName(firstname.get());
		}
		if (!lastname.equals(Optional.empty())) {
			System.out.println("lname done");
			js.setLastName(lastname.get());
		}
		if (!highesteducation.equals(Optional.empty())) {
			System.out.println("highest edu");
			js.setHighestEducation(Integer.parseInt(highesteducation.get()));
		}
		if (!password.equals(Optional.empty())) {
			System.out.println("password");
			js.setPassword(password.get());
		}
		if (!skills.equals(Optional.empty())) {
			System.out.println("skills : " + skills);
			js.setSkills(skills.get());
			System.out.println("huhuhuh : " + skills.get());
		}

		if (!workex.equals(Optional.empty())) {
			System.out.println("workex : " + workex);
			js.setWorkEx(Integer.parseInt(workex.get()));
		}

		JobSeeker jobseeker = jobSeekerDao.getJobSeeker(Integer.parseInt(id));
		JobSeeker jobskr = null;
		if (jobseeker != null) {
			jobskr = jobSeekerDao.updateJobSeeker(js);
			System.out.println("updated");
		} else {
			jobskr = jobSeekerDao.createJobSeeker(js);
		}
		System.out.println("done");
		System.out.println("ashay");
		System.out.println(jobskr.getVerificationCode());

		model.addAttribute("seeker", jobskr);
		return "userprofile";

	}

	
	/**
	 * @param id
	 * @param name
	 * @param headquarters
	 * @param user
	 * @param description
	 * @param model
	 * @return updated company
	 */
	@RequestMapping(value = "/update/company", method = RequestMethod.POST)
	public String companyupdate(@RequestParam("id") String id, @RequestParam("companyName") Optional<String> name,
			@RequestParam("headquarters") Optional<String> headquarters,
			@RequestParam("companyUser") Optional<String> user,
			@RequestParam("description") Optional<String> description, Model model) {

		Company c = new Company();

		c.setCompanyId(Integer.parseInt(id));

		if (!name.equals(Optional.empty())) {

			c.setCompanyName(name.get());
		}
		if (!user.equals(Optional.empty())) {

			c.setCompanyUser(user.get());
		}
		if (!headquarters.equals(Optional.empty())) {
			c.setHeadquarters(headquarters.get());
		}
		if (!description.equals(Optional.empty())) {
			c.setDescription(description.get());
		}

		Company company = companyDao.getCompany(Integer.parseInt(id));
		Company c1 = null;
		if (company != null) {
			c1 = companyDao.updateCompany(c);

		} else {
			return "error";
		}
		System.out.println("done");
		model.addAttribute("company", c1);
		return "companyprofile";

	}

	@RequestMapping(value = "/interested", method = RequestMethod.POST)
	public String createInterest(@RequestParam("userId") String userId, @RequestParam("jobId") String jobId, Model model) {

		try {
			Interested in = new Interested();
			in.setJobId(Integer.parseInt(jobId));
			in.setJobSeekerId(Integer.parseInt(userId));
			Interested i1 = interestedDao.createInterest(in);
			
		} catch (Exception e) {

			HttpHeaders httpHeaders = new HttpHeaders();

			Map<String, Object> message = new HashMap<String, Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			message.put("code", "400");
			message.put("msg", "Error Occured");
			response.put("BadRequest", message);
			JSONObject json_test = new JSONObject(response);
			String json_resp = json_test.toString();

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			return "error";

		}
		JobPosting job = jobDao.getJobPosting(Integer.parseInt(jobId));
		Company company = job.getCompany();
		JobSeeker seeker = jobSeekerDao.getJobSeeker(Integer.parseInt(userId));
		List<?> ij = interestedDao.getAllInterestedJobId(Integer.parseInt(userId));
		int i = 0, j = 0;
		if(ij.contains(Integer.parseInt(jobId))){
			i = 1;
		}
		String message="<div class=\"alert alert-success\">This job has been <strong>Successfully added</strong> to your interests</div>";
		
		List<Integer> il = getAppliedJobs(userId);
		if(il.contains(Integer.parseInt(jobId))){
			j = 1;
		}
		
		model.addAttribute("job", job);
		model.addAttribute("seeker", seeker);
		model.addAttribute("company", company);
		model.addAttribute("interested", i);
		model.addAttribute("message", message);
		model.addAttribute("applied", j);
		
		
		return "userjobprofile";
	}

	/**
	 * @param userId
	 * @param jobId
	 * @return "deleted" if the interest is deleted
	 */
	@RequestMapping(value = "/interested/delete", method = RequestMethod.POST)
	public String deleteInterest(@RequestParam("userId") String userId, @RequestParam("jobId") String jobId, Model model) {

		try {
			List<?> querylist = interestedDao.getInterestedJobId(Integer.parseInt(jobId), Integer.parseInt(userId));
			boolean interestDeleted = interestedDao.deleteInterest(Integer.parseInt(querylist.get(0).toString()));
			if (interestDeleted) {
				JobPosting job = jobDao.getJobPosting(Integer.parseInt(jobId));
				Company company = job.getCompany();
				JobSeeker seeker = jobSeekerDao.getJobSeeker(Integer.parseInt(userId));
				List<?> ij = interestedDao.getAllInterestedJobId(Integer.parseInt(userId));
				int i = 0;
				if(ij.contains(Integer.parseInt(jobId))){
					i = 1;
				}

				String message="<div class=\"alert alert-danger\">This job has been <strong>Successfully removed</strong> from your interests</div>";
				
				model.addAttribute("job", job);
				model.addAttribute("seeker", seeker);
				model.addAttribute("company", company);
				model.addAttribute("interested", i);
				model.addAttribute("message", message);
				model.addAttribute("applied", 1);
				
				
				return "userjobprofile";

			} else {
				return "error";
			}

		} catch (Exception e) {

			HttpHeaders httpHeaders = new HttpHeaders();

			Map<String, Object> message = new HashMap<String, Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			message.put("code", "400");
			message.put("msg", "Error Occured");
			response.put("BadRequest", message);
			JSONObject json_test = new JSONObject(response);
			String json_resp = json_test.toString();

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			return "error";

		}

	}

	/**
	 * @param jobSeekerId
	 * @return List of the jobs the job seeker is interested in
	 */
	@RequestMapping(value = "/getinterestedjobs", method = RequestMethod.GET)
	public String getInterestedJobsForJobSeeker(@RequestParam("jobSeekerId") String jobSeekerId, Model model) {
		
		JobSeeker jobseeker = jobSeekerDao.getJobSeeker(Integer.parseInt(jobSeekerId));
		List<?> jobSeekerInterestsList = jobSeekerDao.getJobSeeker(Integer.parseInt(jobSeekerId)).getInterestedjobs();
		
		model.addAttribute("jobs", jobSeekerInterestsList);
		model.addAttribute("seeker", jobseeker);
		return "interestedjobs";
	}
	
	/**
	 * @param jobSeekerId
	 * @return Job applications list for the job seeker
	 */
	@RequestMapping(value="/getappliedjobs", method = RequestMethod.GET)
	public List<Integer> getAppliedJobs(@RequestParam("jobSeekerId") String jobSeekerId){
		List<?> jobSeekerAppliedList =jobSeekerDao.getJobSeeker(Integer.parseInt(jobSeekerId)).getJobApplicationList();
		List<Integer> jobIdList = new ArrayList<Integer>();
		for (Iterator iterator = jobSeekerAppliedList.iterator(); iterator.hasNext();) {
			JobApplication ja = (JobApplication) iterator.next();
			int jobId = ja.getJobPosting().getJobId();
			jobIdList.add(jobId);
		}
		return jobIdList;
	}
	
	

}