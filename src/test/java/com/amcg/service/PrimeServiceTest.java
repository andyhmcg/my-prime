package com.amcg.service;
import com.amcg.exception.InvalidRequestException;
import com.amcg.generator.PrimeNumberGenerator;
import com.amcg.web.request.PrimeNumberRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrimeServiceTest {


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    PrimeNumberGenerator primeNumberGenerator;

    @InjectMocks
    private PrimeServiceImpl primeServiceImpl = new PrimeServiceImpl();


    /**
     * Should return the 25 primes less than 100
     */
    @Test
    public void testCanGetTheDefaultListOfPrimes(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 100, 25);
        List<Integer> expectedPrimes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Integer> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder( 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);
    }

    /**
     * Should return a limited list of   primes less than 100
     */
    @Test
    public void testCanGetTheFirstListOfPrimesUpToLimit(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 100, 10);
        List<Integer> expectedPrimes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Integer> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder( 2, 3, 5, 7, 11, 13, 17, 19, 23, 29));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }


    /**
     * Should return the first 10  primes greater than start less than end
     */
    @Test
    public void testCanGetTheRangeOfPrimesBetweenStartAndEndUpToLimit(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(10, 100, 10);
        List<Integer> expectedPrimes = Arrays.asList(11, 13, 17, 19, 23, 29, 31, 37, 41, 43);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Integer> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder(11, 13, 17, 19, 23, 29, 31, 37, 41, 43));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }

    /**
     * Should return the first 10  primes greater than start less than end
     */
    @Test
    public void confirmThatStartandEndValuesAreInclusive(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(11, 43, 10);
        List<Integer> expectedPrimes = Arrays.asList(11, 13, 17, 19, 23, 29, 31, 37, 41, 43);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Integer> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder(11, 13, 17, 19, 23, 29, 31, 37, 41, 43));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }


    /**
     * Should return the first prime number
     */
    @Test
    public void testCanGetTheFirstPrimeNumber(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 2, 1);
        List<Integer> expectedPrimes = Collections.singletonList(2);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Integer> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder(2));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }

    /**
     * Should return no prime numbers
     */
    @Test
    public void testCanReturnZeroPrimeNumbers(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 0, 10);
        List<Integer> expectedPrimes = Collections.emptyList();
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Integer> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, is(empty()));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }

    /**
     * Should return one prime number
     */
    @Test
    public void testCanReturnOnePrimeNumberWhenStartAndEndAreEqualAndAPrime(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(2, 2, 10);
        List<Integer> expectedPrimes = Collections.singletonList(2);

        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);
        List<Integer> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder(2L));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);
    }

    @Test
    public void canThrowExceptionWhenLimitIsInvalid(){
        expectedException.expect(InvalidRequestException.class);
        expectedException.expectMessage("PrimeNumberRequest.limit is less than 1");


        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 10, 0);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenThrow(new InvalidRequestException("PrimeNumberRequest.limit is less than 1"));
        primeServiceImpl.getPrimes(primeNumberRequest);

    }

    @Test
    public void canThrowExceptionWhenEndIsLessThanStart(){
        expectedException.expect(InvalidRequestException.class);
        expectedException.expectMessage("PrimeNumberRequest.end is greater than PrimeNumberRequest.start");


        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(20, 10, 1);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenThrow(new InvalidRequestException("PrimeNumberRequest.end is greater than PrimeNumberRequest.start"));

        primeServiceImpl.getPrimes(primeNumberRequest);



    }


}
