package com.amcg.boot;

import com.amcg.generator.ExecutorGenerator;
import com.amcg.generator.PrimeNumberGenerator;
import com.amcg.service.PrimeService;
import com.amcg.service.PrimeServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan(basePackages = {"com.amcg.web.controller"})
@Configuration
@EnableAutoConfiguration
public class AppConfig {

    @Bean
    public PrimeService getPrimeService(){
        return new PrimeServiceImpl();
    }

    @Bean
    public PrimeNumberGenerator getBruteForceGenerator(){
        return new ExecutorGenerator();
    }

}
