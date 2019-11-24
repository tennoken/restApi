package com.example.example.repositoryTest;

import com.example.example.ExampleApplicationTests;
import com.example.example.model.entity.AdminUser;
import com.example.example.repository.AdminUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class AdminUserRepositoryTest extends ExampleApplicationTests {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create(){

        AdminUser adminUser = new AdminUser();

        adminUser.setAccount("AdminUser02");
        adminUser.setPassword("AdminUser02");
        adminUser.setStatus("Registered");
        adminUser.setRole("Partner");
        //adminUser.setCreatedAt(LocalDateTime.now());
        //adminUser.setCreatedBy("AdminServer");

        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        Assertions.assertNotNull(newAdminUser);

        newAdminUser.setAccount("Change");
        adminUserRepository.save(newAdminUser);

    }
}
