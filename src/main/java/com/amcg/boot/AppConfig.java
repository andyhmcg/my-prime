package com.amcg.boot;

import com.amcg.generator.BruteForceGenerator;
import com.amcg.generator.ExecutorGenerator;
import com.amcg.generator.PrimeNumberGenerator;
import com.amcg.generator.SieveOfEratosthenesGenerator;
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


    /**
     * Implementations of PrimeNumberGenerator. Can only use one at a time
     * @return implementation PrimeNumberGenerator
     */
    
    //@Bean()
    public PrimeNumberGenerator getSieveOfEratosthenesGenerator(){

        return new SieveOfEratosthenesGenerator();
    }

    //@Bean()
    public PrimeNumberGenerator getBruteForceGenerator(){
        return new BruteForceGenerator();
    }

    @Bean()
    public PrimeNumberGenerator getExecutorGenerator(){
       return new ExecutorGenerator();
    }

}
