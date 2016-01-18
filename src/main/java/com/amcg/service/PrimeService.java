package com.amcg.service;




import com.amcg.web.request.PrimeNumberRequest;

import java.util.List;

public interface PrimeService {

    List<Integer> getPrimes(PrimeNumberRequest primeNumberRequest);
}
