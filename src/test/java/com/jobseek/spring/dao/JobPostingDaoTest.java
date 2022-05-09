package com.jobseek.spring.dao;

import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.JobPosting;
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
public class JobPostingDaoTest {

    @Autowired
    private JobPostingDao jobPostingDao;

    @Autowired
    private CompanyDao companyDao;

    @Test
    @Transactional
    @Rollback(true)
    public void createJobPosting() throws Exception {

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
        JobPosting jp2=jobPostingDao.createJobPosting(jp,cid);
        String actual=jp2.getTitle();
        String expected="SDE III";
        assertEquals("checking create job posting",expected,actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getCompanyJobPostingIds() throws Exception {
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
        JobPosting jp2=jobPostingDao.createJobPosting(jp,cid);

        int actual=jobPostingDao.getCompanyJobPostingIds(cid).get(0);
        int expected=jp2.getJobId();
        assertEquals("checking get companyjobpostingids",expected,actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getJobPosting() throws Exception {
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
        JobPosting jp2=jobPostingDao.createJobPosting(jp,cid);

        JobPosting newjp=jobPostingDao.getJobPosting(jp2.getJobId());
        String actual=newjp.getTitle();
        String expected=jp.getTitle();

        assertEquals("checking getjobposting",expected,actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteJobPosting() throws Exception {
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
        JobPosting jp2=jobPostingDao.createJobPosting(jp,cid);

        boolean actual=jobPostingDao.deleteJobPosting(jp2.getJobId());
        assertTrue("checking delete jobposting",actual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateJobPosting() throws Exception {
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
        JobPosting jp2=jobPostingDao.createJobPosting(jp,cid);
        jp.setLocation("Hyderabad");
        jp=jobPostingDao.updateJobPosting(jp);

        String actual=jp.getLocation();
        String expected="Hyderabad";
        assertEquals("checking update jobposting",actual,expected);
    }
}