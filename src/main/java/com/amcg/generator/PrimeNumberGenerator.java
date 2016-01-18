package com.amcg.generator;


import com.amcg.web.request.PrimeNumberRequest;

import java.util.List;

public interface PrimeNumberGenerator {

    List<Integer> getPrimes(PrimeNumberRequest primeNumberRequest);

}
