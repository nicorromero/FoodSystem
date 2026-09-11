package com.foodSystem.tromer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TromerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TromerApplication.class, args);
    }
}
