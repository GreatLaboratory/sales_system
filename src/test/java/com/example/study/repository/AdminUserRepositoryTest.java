package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class AdminUserRepositoryTest extends StudyApplicationTests {
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create() {
        AdminUser adminUser = new AdminUser();

        adminUser.setAccount("adminUser01");
        adminUser.setPassword("adminUser01");
        adminUser.setStatus("registered");
        adminUser.setRole("partner");
        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setCreatedBy("AdminServer");

        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        Assert.assertNotNull(newAdminUser);
    }

    @Test
    public void read() {
        Optional<AdminUser> adminUser = adminUserRepository.findById(1L);

        adminUser.ifPresent(thisUser-> {
            System.out.println("첫번째 관리자 : " + thisUser);
        });
    }
}
