package com.amcg.generator;

import com.amcg.concurrency.PrimeNumberCallable;
import com.amcg.concurrency.PrimeNumberResult;
import com.amcg.web.request.PrimeNumberRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ExecutorGenerator  extends AbstractPrimeNumberGenerator{

    @Override
    public List<Integer> getPrimes(PrimeNumberRequest primeNumberRequest) {

        isRequestValid(primeNumberRequest);
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<PrimeNumberResult> executorCompletionService= new ExecutorCompletionService<>(executorService );

        final List<Future<PrimeNumberResult>> results = new ArrayList<>();
        int rangeSize = (primeNumberRequest.getEnd() - primeNumberRequest.getStart()) + 1;

        IntStream.iterate(primeNumberRequest.getStart(), i -> i + 1)
                .limit(rangeSize) // Limit  numbers to check
                .forEach(i -> {
                    Callable<PrimeNumberResult> callable = new PrimeNumberCallable(i);
                    Future<PrimeNumberResult> futureResult = executorCompletionService.submit(callable);
                    results.add(futureResult);
                });


        // Take the next available result
        List<Integer> primes = results.stream()
                .map(f -> {
                    try {
                        return executorCompletionService.take().get();
                    }catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    })
                .filter(PrimeNumberResult::isPrime)
                .limit(primeNumberRequest.getLimit())
                .map(PrimeNumberResult::getNumber)
                .sorted()
                .collect(Collectors.toList());

        executorService.shutdown();
        return primes;

    }

}
