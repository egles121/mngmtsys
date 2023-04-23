package com.egles121.mngmtsys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@Slf4j
public class MngmtsysApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(MngmtsysApplication.class)
                .bannerMode(Banner.Mode.LOG)
                .run(args);

        ConfigurableEnvironment configurableEnvironment = run.getEnvironment();
        String serverPort = configurableEnvironment.getProperty("local.server.port");
        log.info("Management app: http://localhost:{}{}", serverPort, configurableEnvironment.getProperty("springdoc.management-app.path"));
        log.info("Swagger: http://localhost:{}{}", serverPort, configurableEnvironment.getProperty("springdoc.swagger-ui.path"));
    }

}
