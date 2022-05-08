package com.jobseek.spring.dao;
import java.util.ArrayList;
import java.util.List;
import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.JobPosting;
import com.jobseek.spring.entity.JobSeeker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobSeekerDaoTest {

    @Autowired
    private JobSeekerDao jobSeekerDao;

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private JobPostingDao jobPosting;

    @Test
    @Transactional
    @Rollback(true)
    public void filterJobs() throws Exception {
        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        companyDao.createCompany(c);
        int cid=companyDao.getCompanyIdFromEmail("testcomp1@gmail.com").get(0);

        JobPosting jp=new JobPosting();
        jp.setCompany(c);
        jp.setState(0);
        jp.setTitle("SDE III");
        jp.setSalary("10000,1000000");
        jp.setResponsibilities("C++");
        jp.setLocation("Bangalore");
        jp.setDescription("Developement");

        int jpid=jobPosting.createJobPosting(jp,cid).getJobId();
        List<Integer> jobids=new ArrayList<>();
        jobids.add(jpid);
        assertNotNull("checking filter jobs",jobSeekerDao.filterJobs(jp,jobids).size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createJobSeeker() throws Exception {

        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        JobSeeker newjs=jobSeekerDao.createJobSeeker(js);
        assertEquals("checking create jobseeker",js.toString(),newjs.toString());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateJobSeeker() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);
        jobSeekerDao.createJobSeeker(js);
        js.setFirstName("jobseeker2");
        JobSeeker newjs=jobSeekerDao.updateJobSeeker(js);

        assertEquals("checking update jobseeker","jobseeker2",newjs.getFirstName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getJobSeeker() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        JobSeeker oldjs=jobSeekerDao.createJobSeeker(js);
        JobSeeker newjs=jobSeekerDao.getJobSeeker(oldjs.getJobseekerId());
        assertEquals("checking get jobseeker",oldjs.toString(),newjs.toString());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void passwordLookUp() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        JobSeeker oldjs=jobSeekerDao.createJobSeeker(js);
        String actual=jobSeekerDao.PasswordLookUp(js.getEmailId()).get(0);
        String expected=js.getPassword();
        assertEquals("checking passwordLookup",expected,actual);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void verify() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(false);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        js=jobSeekerDao.createJobSeeker(js);
        jobSeekerDao.verify(js);

        boolean actual=jobSeekerDao.getJobSeeker(js.getJobseekerId()).isVerified();
        assertTrue("checking verify",actual);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void searchJobs() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);
        JobSeeker oldjs=jobSeekerDao.createJobSeeker(js);

        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        companyDao.createCompany(c);
        int cid=companyDao.getCompanyIdFromEmail("testcomp1@gmail.com").get(0);

        JobPosting jp=new JobPosting();
        jp.setCompany(c);
        jp.setState(0);
        jp.setTitle("SDE III");
        jp.setSalary("1000000");
        jp.setResponsibilities("C++");
        jp.setLocation("Bangalore");
        jp.setKeywords("Trending");
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);

        String searchString="Trend";

        assertNotEquals("checking searchjobs",jobSeekerDao.searchJobs(searchString).size(),0);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void getUserIdFromEmail() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);
        JobSeeker oldjs=jobSeekerDao.createJobSeeker(js);
        int expected=oldjs.getJobseekerId();

        int actual=jobSeekerDao.getUserIdFromEmail(js.getEmailId()).get(0);
        assertEquals("checking getuseridfromemail",expected,actual);

    }
}