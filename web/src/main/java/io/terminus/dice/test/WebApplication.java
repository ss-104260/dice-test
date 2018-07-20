package io.terminus.dice.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebApplication.class);
        application.run(args);
    }

}
