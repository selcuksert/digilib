package com.corp.libapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DigilibApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigilibApplication.class, args);
    }

}
