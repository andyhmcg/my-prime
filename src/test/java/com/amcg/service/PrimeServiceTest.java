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
        List<Long> expectedPrimes = Arrays.asList(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L, 79L, 83L, 89L, 97L);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Long> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder( 2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L, 79L, 83L, 89L, 97L));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);
    }

    /**
     * Should return a limited list of   primes less than 100
     */
    @Test
    public void testCanGetTheFirstListOfPrimesUpToLimit(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 100, 10);
        List<Long> expectedPrimes = Arrays.asList(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Long> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder( 2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }


    /**
     * Should return the first 10  primes greater than start less than end
     */
    @Test
    public void testCanGetTheRangeOfPrimesBetweenStartAndEndUpToLimit(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(10, 100, 10);
        List<Long> expectedPrimes = Arrays.asList(11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Long> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder(11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }

    /**
     * Should return the first 10  primes greater than start less than end
     */
    @Test
    public void confirmThatStartandEndValuesAreInclusive(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(11, 43, 10);
        List<Long> expectedPrimes = Arrays.asList(11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Long> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder(11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }


    /**
     * Should return the first prime number
     */
    @Test
    public void testCanGetTheFirstPrimeNumber(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 2, 1);
        List<Long> expectedPrimes = Collections.singletonList(2L);
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Long> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, containsInAnyOrder(2L));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }

    /**
     * Should return no prime numbers
     */
    @Test
    public void testCanReturnZeroPrimeNumbers(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 0, 10);
        List<Long> expectedPrimes = Collections.emptyList();
        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);

        List<Long> primes = primeServiceImpl.getPrimes(primeNumberRequest);
        assertThat("Prime Numbers", primes, is(empty()));

        verify(primeNumberGenerator).getPrimes(primeNumberRequest);

    }

    /**
     * Should return one prime number
     */
    @Test
    public void testCanReturnOnePrimeNumberWhenStartAndEndAreEqualAndAPrime(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(2, 2, 10);
        List<Long> expectedPrimes = Collections.singletonList(2L);

        when(primeNumberGenerator.getPrimes(primeNumberRequest)).thenReturn(expectedPrimes);
        List<Long> primes = primeServiceImpl.getPrimes(primeNumberRequest);
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
