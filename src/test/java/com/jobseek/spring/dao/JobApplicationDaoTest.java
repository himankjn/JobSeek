package com.jobseek.spring.dao;

import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.JobApplication;
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
public class JobApplicationDaoTest {

    @Autowired
    private JobApplicationDao jobApplicationDao;

    @Autowired
    private JobSeekerDao jobSeeker;
    @Autowired
    private JobPostingDao jobPosting;
    @Autowired
    private CompanyDao companyDao;

    @Test
    @Transactional
    @Rollback(true)
    public void apply() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        jobSeeker.createJobSeeker(js);
        int jsid=jobSeeker.getUserIdFromEmail("newjobseeker@gmail.com").get(0);

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
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);

        JobApplication ja=jobApplicationDao.apply(jsid,jpid,true,"home/documets/resumes");

        assertEquals("testing apply",ja.getResumePath(),"home/documets/resumes");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void cancel() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        jobSeeker.createJobSeeker(js);
        int jsid=jobSeeker.getUserIdFromEmail("newjobseeker@gmail.com").get(0);

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
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);
        JobApplication ja=jobApplicationDao.apply(jsid,jpid,true,"home/documets/resumes");

        boolean actual=jobApplicationDao.cancel(ja.getAppId());
        boolean expected=true;
        assertEquals("testing cancel",expected,actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getJobApplication() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        jobSeeker.createJobSeeker(js);
        int jsid=jobSeeker.getUserIdFromEmail("newjobseeker@gmail.com").get(0);

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
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);
        JobApplication ja=jobApplicationDao.apply(jsid,jpid,true,"home/documets/resumes");

        JobApplication janew=jobApplicationDao.getJobApplication(ja.getAppId());
        String actual=janew.getResumePath();
        String expected=ja.getResumePath();
        assertEquals("checking getJobApplication",expected,actual);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void getJobApplicationsbyjobId() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        jobSeeker.createJobSeeker(js);
        int jsid=jobSeeker.getUserIdFromEmail("newjobseeker@gmail.com").get(0);

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
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);
        JobApplication ja=jobApplicationDao.apply(jsid,jpid,true,"home/documets/resumes");

        int actual=jobApplicationDao.getJobApplicationsbyjobId(jpid).size();
        assertEquals("checking getjobapplicationbyjobid",1,actual);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void getJobApplicationsbyjobIdAndUserId() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        jobSeeker.createJobSeeker(js);
        int jsid=jobSeeker.getUserIdFromEmail("newjobseeker@gmail.com").get(0);

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
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);
        JobApplication ja=jobApplicationDao.apply(jsid,jpid,true,"home/documets/resumes");

        int actual=jobApplicationDao.getJobApplicationsbyjobIdAndUserId(jpid,jsid).size();
        assertEquals("checking getjobapplicationsbyjobidanduserid",1,actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void modifyJobApplicationStatus() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        jobSeeker.createJobSeeker(js);
        int jsid=jobSeeker.getUserIdFromEmail("newjobseeker@gmail.com").get(0);

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
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);
        JobApplication ja=jobApplicationDao.apply(jsid,jpid,true,"home/documets/resumes");

        jobApplicationDao.modifyJobApplicationStatus(ja.getAppId(),1);

        JobApplication newapp=jobApplicationDao.getJobApplication(ja.getAppId());
        int actual=newapp.getState();
        int expected=1;
        assertEquals("testing modify state",expected,actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateApplication() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);

        jobSeeker.createJobSeeker(js);
        int jsid=jobSeeker.getUserIdFromEmail("newjobseeker@gmail.com").get(0);

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
        jobPosting.createJobPosting(jp,cid);
        int jpid=jobPosting.getCompanyJobPostingIds(cid).get(0);
        JobApplication ja=jobApplicationDao.apply(jsid,jpid,true,"home/documets/resumes");

        ja.setState(2);
        jobApplicationDao.updateApplication(ja);
        int actual=jobApplicationDao.getJobApplication(ja.getAppId()).getState();
        int expected=2;
        assertEquals("testing update application",expected,actual);
    }
}