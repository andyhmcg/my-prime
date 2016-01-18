package com.amcg.service;


import com.amcg.generator.PrimeNumberGenerator;
import com.amcg.web.request.PrimeNumberRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PrimeServiceImpl implements PrimeService {

    @Autowired
    PrimeNumberGenerator primeNumberGenerator;

    @Override
    public List<Long> getPrimes(PrimeNumberRequest primeNumberRequest) {

        return primeNumberGenerator.getPrimes(primeNumberRequest);
    }
}
