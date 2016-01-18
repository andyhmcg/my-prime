package com.amcg.generator;

import com.amcg.web.request.PrimeNumberRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class BruteForceGenerator extends AbstractPrimeNumberGenerator {

    @Override
    public List<Long> getPrimes(PrimeNumberRequest primeNumberRequest) {

        isRequestValid(primeNumberRequest);
        long rangeSize = (primeNumberRequest.getEnd() - primeNumberRequest.getStart()) + 1;

        return LongStream.iterate(primeNumberRequest.getStart(), i -> i + 1)
                .limit(rangeSize) // Limit  numbers to check
                .parallel()
                .filter(this::isPrime) // remove non prime numbers
                .limit(primeNumberRequest.getLimit()) // limit the number of primes returned
                .boxed()
                .collect(Collectors.toList());

    }

    /**
     * Brute force check for prime numbers
     * @param number the number to check
     * @return the result
     */
    private  boolean isPrime(long number){
        if (number < 2){
            return false;
        }
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 2; i < sqrt; i++) {
            if (number % i == 0) {
                return false; }
        }

        return true;
    }
}
