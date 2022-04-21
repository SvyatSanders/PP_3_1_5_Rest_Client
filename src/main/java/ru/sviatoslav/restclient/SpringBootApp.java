package ru.sviatoslav.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.sviatoslav.restclient.entity.User;
import ru.sviatoslav.restclient.service.CommunicationService;

import java.util.List;

@SpringBootApplication
public class SpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
