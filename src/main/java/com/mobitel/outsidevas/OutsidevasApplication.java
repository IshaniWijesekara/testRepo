package com.mobitel.outsidevas;
import com.mobitel.outsidevas.service.SubscribeService;
import com.mobitel.outsidevas.service.impl.SubscribeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class OutsidevasApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    SubscribeServiceImpl subscribeService;

    @Value("${service.endpoint}")
    private String path;

    @Override
    public void run(String... args) throws Exception {
        Endpoint.publish(path, subscribeService);
    }

    public static void main(String[] args) {
        SpringApplication.run(OutsidevasApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OutsidevasApplication.class);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");;
            }
        };
    }
}
