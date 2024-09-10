package com.example.unirun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

/**
 * @author wzh
 */
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class UnirunApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnirunApplication.class, args);
    }

}
