package com.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication

@EnableJpaRepositories
@EnableTransactionManagement
public class BoxApplication {

    public static void main(String[] args) {


        SpringApplication.run(BoxApplication.class, args);
    }

}
