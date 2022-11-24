package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.example.demo.utils.PropertyLoader;

@SpringBootApplication
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories()
public class LhvApplication {

    public static void main(String[] args) {
        displayDbGui();
        SpringApplication.run(LhvApplication.class, args);
    }

    private static void displayDbGui() {
        new org.hsqldb.util.DatabaseManagerSwing().main(new String[]{"--url", PropertyLoader.getProperty("javax.persistence.jdbc.url"), "--user", "sa", "--password", ""});
    }

}