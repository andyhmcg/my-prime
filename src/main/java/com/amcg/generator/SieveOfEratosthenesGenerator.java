package com.amcg.generator;

import com.amcg.web.request.PrimeNumberRequest;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the Sieve Of Eratosthenes algorithm for generatin prime numbers
 */
public class SieveOfEratosthenesGenerator extends AbstractPrimeNumberGenerator {

    @Override
    public List<Integer> getPrimes(PrimeNumberRequest primeNumberRequest) {

        isRequestValid(primeNumberRequest);
        int  maxPrimeNumber = primeNumberRequest.getEnd();

        BitSet bitSet = new BitSet(maxPrimeNumber);


        // Set known primes less than 2
        bitSet.set(0, true);
        bitSet.set(1, true);

        // Set to first known prime
        int currentPrime = 2;

        while (currentPrime * currentPrime <= maxPrimeNumber ){
            int mark = currentPrime + currentPrime;
            while (mark > 0 && mark <= maxPrimeNumber) {
                bitSet.set(mark, true);
                mark += currentPrime;
            }

            /* Set currentPrime to next prime
            * This will be the firat unnchecked element in the bit set
            */
            bitSet.nextClearBit(currentPrime++);

        }

        final List<Integer> allPrimes = new ArrayList<>();

        // The index of unset bits is the prime number we are looking for
        for (int i = bitSet.nextClearBit(0); i < bitSet.length() + 1 ; i = bitSet.nextClearBit(i+1)) {

            allPrimes.add(i);

        }

        // Get primes by start, end and limit
        return allPrimes.stream()
                .filter(primeNumber ->  primeNumber >= primeNumberRequest.getStart() && primeNumber <= primeNumberRequest.getEnd())
                .limit(primeNumberRequest.getLimit())
                .collect(Collectors.toList());

    }
}
