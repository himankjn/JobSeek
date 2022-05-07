package com.jobseek.spring.dao;
import java.util.ArrayList;
import java.util.List;
import com.jobseek.spring.entity.Company;
import com.jobseek.spring.entity.JobPosting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyDaoTest {
    @Autowired
    private CompanyDao companyDao;
    @Autowired JobPostingDao jp;
    @Test
    @Transactional
    @Rollback(true)
    public void passwordLookUp() throws Exception {
        Company c=new Company();
        c.setVerified(true);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("testloc1");
        companyDao.createCompany(c);

        String actual=companyDao.PasswordLookUp("testcomp1@gmail.com").get(0);
        String expected="testpswd";
        assertEquals(expected,actual,"checking password fucntionality");
    }


    @Test
    @Transactional
    @Rollback(true)
    public void createCompany() throws Exception {
        Company c=new Company();
        c.setVerified(true);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("testloc1");
        Company c1=companyDao.createCompany(c);

        assertNotNull(c1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateCompany() throws Exception {
        Company c=new Company();
        c.setVerified(true);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        companyDao.createCompany(c);
        int id=companyDao.getCompanyIdFromEmail("testcomp1@gmail.com").get(0);
        c.setHeadquarters("newyork");
        companyDao.updateCompany(c);
        String expected="newyork";
        String actual=companyDao.getCompany(id).getHeadquarters();

        assertEquals(expected,actual,"check for updated company");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getCompany() throws Exception {

        Company c=new Company();
        c.setVerified(true);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        companyDao.createCompany(c);
        int id=companyDao.getCompanyIdFromEmail("testcomp1@gmail.com").get(0);
        Company c2=companyDao.getCompany(id);
        assertEquals(c.toString(),c2.toString(),"checking get company method");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void verify() throws Exception {
        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        companyDao.createCompany(c);
        int id=companyDao.getCompanyIdFromEmail("testcomp1@gmail.com").get(0);
        c.setVerified(true);
        companyDao.verify(c);
        boolean actual=companyDao.getCompany(id).isVerified();
        boolean expected=true;

        assertEquals(expected,actual,"Checking verify method");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void getJobsByCompany() throws Exception {
        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        companyDao.createCompany(c);

        int id=companyDao.getCompanyIdFromEmail("testcomp1@gmail.com").get(0);
        companyDao.getJobsByCompany(id);

        JobPosting j1=new JobPosting();
        j1.setCompany(c);
        j1.setLocation("bangalore");
        j1.setDescription("desc");
        j1.setSalary("100000");
        j1.setResponsibilities("C++");
        j1.setState(0);
        j1.setTitle("SDE 1");

        JobPosting j2=new JobPosting();
        j2.setCompany(c);
        j2.setLocation("bangalore");
        j2.setDescription("desc");
        j2.setSalary("100000");
        j2.setResponsibilities("C++");
        j2.setState(0);
        j2.setTitle("SDE 1");

        jp.createJobPosting(j1,id);
        jp.createJobPosting(j2,id);

        List<?> actual=companyDao.getJobsByCompany(id);
        assertEquals(2,actual.size(),"testing getjobsbycompany");

    }

    @Test
    @Transactional
    @Rollback(true)
    public void getCompanyIdFromEmail() throws Exception {
        Company c=new Company();
        c.setVerified(false);
        c.setVerificationCode(1234);
        c.setCompanyName("testcomp1");
        c.setCompanyUser("testcomp1@gmail.com");
        c.setPassword("testpswd");
        c.setHeadquarters("bangalore");
        companyDao.createCompany(c);
        int actual=companyDao.getCompanyIdFromEmail("testcomp1@gmail.com").get(0);
        int expected=c.getCompanyId();

        assertEquals(expected,actual,"check company id from email");
    }
}
