/**
 * 
 */
package com.jobseek.spring.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.jobseek.spring.dao.CompanyDao;
import com.jobseek.spring.dao.JobSeekerDao;
import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.JobSeeker;
import com.jobseek.spring.mail.EmailServiceImpl;

/**
 * @author himank
 *
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

	/**
	 * @return Homepage
	 */
	@RequestMapping(value = "/findjobs", method = RequestMethod.GET)
	public String showHomePage() {
		return "index";
	}

	/**
	 * @return Register page
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterPage() {
		return "register";
	}

	@Autowired
	JobSeekerDao jobSeekerDao;
	
	@Autowired
	CompanyDao companyDao;

	@Autowired
	EmailServiceImpl emailService;

		/**
	 * @param emailId
	 * @param password
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("emailId") String emailId, @RequestParam("password") String password,
			@RequestParam("type") String type, Model model) {
		List<String> list = new ArrayList<String>();
		String email = emailId;
		String pwd = password;
		System.out.println(email + " : " + pwd);
		String message="<div class=\"alert alert-danger\">Invalid Login Credentials</div>";


		if (type.equals("recruiter")) {
			list = companyDao.PasswordLookUp(email);
			if (list.size() == 0) {
				
				model.addAttribute("message", message);
				return "index";
			} else {
				if (pwd.equals(list.get(0))) {
					List<Integer> cidl = new ArrayList<Integer>();
					cidl = companyDao.getCompanyIdFromEmail(email);
					Company cmp = companyDao.getCompany(cidl.get(0));
					if(!cmp.isVerified()){
						int randomPIN = (int) (Math.random() * 9000) + 1000;
						String message2="<div class=\"alert alert-danger\">Your email hasn't been verified! Please Verify the email before tyring to login. We have sent an email with new OTP.</div>";
						cmp.setVerificationCode(randomPIN);
						String verificationUrl = "Dear Recruiter,\n" + "One time password for registration at JobSeek is: " + randomPIN;

						emailService.sendSimpleMessage(email, "Verification Pin", verificationUrl);
						companyDao.updateCompany(cmp);
						model.addAttribute("pin",randomPIN);
						model.addAttribute("type","recruiter");
						model.addAttribute("userId",cmp.getCompanyId());
						model.addAttribute("message2",message2);

						return "otpregister";
					}
					model.addAttribute("company", cmp);
					
					return "companyprofile";
				}

			}
			
		} else if (type.equals("seeker")) {
			list = jobSeekerDao.PasswordLookUp(email);
			if (list.size() == 0) {
				model.addAttribute("message", message);
				return "index";
			} else {
				if (pwd.equals(list.get(0))) {
					List<Integer> jsl = new ArrayList<Integer>();
					jsl = jobSeekerDao.getUserIdFromEmail(email);
					JobSeeker js = jobSeekerDao.getJobSeeker(jsl.get(0));
					if(!js.isVerified()){
						int randomPIN = (int) (Math.random() * 9000) + 1000;
						String message2="<div class=\"alert alert-danger\">Your email hasn't been verified! Please Verify the email before tyring to login. We have sent an email with new OTP.</div>";
						js.setVerificationCode(randomPIN);
						String verificationUrl = "Dear Jobseeker,\n" + "One time password for registration at JobSeek is: " + randomPIN;

						emailService.sendSimpleMessage(email, "Verification Pin", verificationUrl);
						jobSeekerDao.updateJobSeeker(js);
						model.addAttribute("pin",randomPIN);
						model.addAttribute("type","seeker");
						model.addAttribute("userId",js.getJobseekerId());
						model.addAttribute("message2",message2);

						return "otpregister";
					}
					model.addAttribute("seeker", js);
					return "userprofile";
				}

			}
		}

		System.out.println(list);
		model.addAttribute("message", message);
		
		return "index";

	}

	/**
	 * @param type
	 * @param pin
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/register/verify", method = RequestMethod.POST)
	public String verification(@RequestParam("type") String type, @RequestParam("pin") String pin,
			@RequestParam("userId") int userId, Model model) {

		if (type.equals("seeker")) {

			JobSeeker j = jobSeekerDao.getJobSeeker(userId);
			String pass=j.getPassword();
			if (j.getVerificationCode() == Integer.parseInt(pin)) {
				j.setVerified(true);
				jobSeekerDao.verify(j);
				model.addAttribute("seeker", j);
				model.addAttribute("pass",pass);
				return "userregister";
			} else {
				return "error";
			}

		} else {

			Company j = companyDao.getCompany(userId);
			if (j.getVerificationCode() == Integer.parseInt(pin)) {
				j.setVerified(true);
				companyDao.verify(j);
				model.addAttribute("company", j);
				return "companyregister";
			} else {
				return "error";
			}

		}

		

	}
	
	
}