package com.jinseok.projectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TestProjectBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestProjectBoardApplication.class, args);
    }

}
