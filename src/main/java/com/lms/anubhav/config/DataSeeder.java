package com.lms.anubhav.config;

import com.lms.anubhav.model.Role;
import com.lms.anubhav.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            Arrays.asList("ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN").forEach(roleName -> {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                }
            });
        };
    }
}
