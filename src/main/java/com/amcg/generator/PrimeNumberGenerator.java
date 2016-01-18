package com.amcg.generator;


import com.amcg.web.request.PrimeNumberRequest;

import java.util.List;

public interface PrimeNumberGenerator {

    List<Long> getPrimes(PrimeNumberRequest primeNumberRequest);

}
