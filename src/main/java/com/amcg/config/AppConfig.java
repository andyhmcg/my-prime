package com.amcg.config;

import com.amcg.generator.BruteForceGenerator;
import com.amcg.service.PrimeService;
import com.amcg.service.PrimeServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AppConfig {

    @Bean
    public PrimeService getPrimeService(){
        return new PrimeServiceImpl();
    }

    @Bean
    public BruteForceGenerator getBruteForceGenerator(){
        return new BruteForceGenerator();
    }

}
