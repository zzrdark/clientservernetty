package com.zkja.clientservernetty;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class ClientservernettyApplication {

    public static void main(String[] args) {
        //SpringApplication.run(ClientservernettyApplication.class, args);
        new SpringApplicationBuilder().sources(ClientservernettyApplication.class).web(false).run(args);
    }
}
