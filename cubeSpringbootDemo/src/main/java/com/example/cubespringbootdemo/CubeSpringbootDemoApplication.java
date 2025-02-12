package com.example.cubespringbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CubeSpringbootDemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CubeSpringbootDemoApplication.class, args);
        String port = context.getEnvironment().getProperty("server.port");
        System.out.println(String.format("Swagger UI : http://localhost:%s/swagger-ui/index.html",port));
    }

}
