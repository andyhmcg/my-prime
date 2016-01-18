package com.amcg.concurrency;

import java.util.concurrent.Callable;


public class PrimeNumberCallable implements Callable<PrimeNumberResult>{

    public PrimeNumberCallable(int primeNumberCandidate) {
        this.primeNumberCandidate = primeNumberCandidate;
    }

    private int primeNumberCandidate;

    @Override
    public  PrimeNumberResult call() throws Exception {

        boolean isPrime = isPrime(primeNumberCandidate);

        return new PrimeNumberResult(primeNumberCandidate, isPrime);
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
