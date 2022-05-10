/*
package com.jobseek.spring.dao;

import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.Interested;
import com.jobseek.spring.entity.JobPosting;
import com.jobseek.spring.entity.JobSeeker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterestedDaoTest {

    @Autowired
    private InterestedDao interestedDao;

    @Test
    @Transactional
    @Rollback(true)
    public void createInterest() throws Exception {
        Interested in=new Interested();
        in.setJobId(1);
        in.setJobSeekerId(1);
        Interested newin=interestedDao.createInterest(in);
        assertNotNull("testing create interested",newin);
    }

    @Autowired
    private JobSeekerDao jobSeeker;
    @Autowired
    private JobPostingDao jobPosting;
    @Autowired
    private CompanyDao companyDao;


    @Test
    @Transactional
    @Rollback(true)
    public void deleteInterest() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);
        js=jobSeeker.createJobSeeker(js);
        int jsid=js.getJobseekerId();

        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        c=companyDao.createCompany(c);
        int cid=c.getCompanyId();

        JobPosting jp=new JobPosting();
        jp.setCompany(c);
        jp.setState(0);
        jp.setTitle("SDE III");
        jp.setSalary("1000000");
        jp.setResponsibilities("C++");
        jp.setLocation("Bangalore");
        jp=jobPosting.createJobPosting(jp,cid);
        int jpid=jp.getJobId();

        Interested in=new Interested();
        in.setJobId(jpid);
        in.setJobSeekerId(jsid);
        in=interestedDao.createInterest(in);
        int inid=in.getId();
        boolean actual=interestedDao.deleteInterest(inid);
        assertEquals("testing delete interested",true,actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getInterest() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);
        js=jobSeeker.createJobSeeker(js);
        int jsid=js.getJobseekerId();

        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        c=companyDao.createCompany(c);
        int cid=c.getCompanyId();

        JobPosting jp=new JobPosting();
        jp.setCompany(c);
        jp.setState(0);
        jp.setTitle("SDE III");
        jp.setSalary("1000000");
        jp.setResponsibilities("C++");
        jp.setLocation("Bangalore");
        jp=jobPosting.createJobPosting(jp,cid);
        int jpid=jp.getJobId();

        Interested in=new Interested();
        in.setJobId(jpid);
        in.setJobSeekerId(jsid);
        int inid=interestedDao.createInterest(in).getId();

        Interested newin=interestedDao.getInterest(inid);

        assertEquals(newin.getJobId(),jpid);
        assertEquals(newin.getJobSeekerId(),jsid);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getInterestedfromJobIdUserId() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);
        js=jobSeeker.createJobSeeker(js);
        int jsid=js.getJobseekerId();

        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        c=companyDao.createCompany(c);
        int cid=c.getCompanyId();

        JobPosting jp=new JobPosting();
        jp.setCompany(c);
        jp.setState(0);
        jp.setTitle("SDE III");
        jp.setSalary("1000000");
        jp.setResponsibilities("C++");
        jp.setLocation("Bangalore");
        jp=jobPosting.createJobPosting(jp,cid);
        int jpid=jp.getJobId();

        Interested in=new Interested();
        in.setJobId(jpid);
        in.setJobSeekerId(jsid);
        interestedDao.createInterest(in);

        assertNotNull("testing get interested from userid,jobid",interestedDao.getInterestedfromJobIdUserId(jpid,jsid));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void getAllInterestedJobId() throws Exception {
        JobSeeker js=new JobSeeker();
        js.setEmailId("newjobseeker@gmail.com");
        js.setVerified(true);
        js.setFirstName("jobseeker1");
        js.setPassword("123");
        js.setVerificationCode(1111);
        js=jobSeeker.createJobSeeker(js);
        int jsid=js.getJobseekerId();

        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        c=companyDao.createCompany(c);
        int cid=c.getCompanyId();

        JobPosting jp=new JobPosting();
        jp.setCompany(c);
        jp.setState(0);
        jp.setTitle("SDE III");
        jp.setSalary("1000000");
        jp.setResponsibilities("C++");
        jp.setLocation("Bangalore");
        jp=jobPosting.createJobPosting(jp,cid);
        int jpid=jp.getJobId();

        Interested in=new Interested();
        in.setJobId(jpid);
        in.setJobSeekerId(jsid);
        interestedDao.createInterest(in);

        assertNotNull("checking get all interested",interestedDao.getAllInterestedJobId(jsid));
    }
}*/
