package com.amcg.generator;

import com.amcg.exception.InvalidRequestException;
import com.amcg.web.request.PrimeNumberRequest;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class PrimeNumberGeneratorTest {

    private PrimeNumberGenerator primeNumberGenerator;

    public PrimeNumberGeneratorTest(PrimeNumberGenerator primeNumberGenerator){
        this.primeNumberGenerator = primeNumberGenerator;
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters()
    {
        return Arrays.asList(new Object[][] {
                { new BruteForceGenerator() },
                { new SieveOfEratosthenesGenerator() }
        });
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Get Prime Numbers when the limit is less than the number of primes available
     */
    @Test
    public void testCanGetPrimeNumbersWhenLimitIsLessThanAvailablePrimes(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 100, 10);
        List<Long> primes = primeNumberGenerator.getPrimes(primeNumberRequest);

        assertThat("Prime Numbers", primes, containsInAnyOrder(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L));

    }

    /**
     * Get Prime Numbers when the limit is more than the number of primes available
     */
    @Test
    public void testCanGetPrimeNumbersWhenLimitIsMoreThanAvailablePrimes(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 12, 10);
        List<Long> primes = primeNumberGenerator.getPrimes(primeNumberRequest);

        assertThat("Prime Numbers", primes, containsInAnyOrder(2L, 3L, 5L, 7L, 11L));

    }

    /**
     * Get Prime numbers where range requested is bounded by non primes
     */
    @Test
    public void testCanGetPrimeNumbersWhenRangeIsBoundedByNonPrimes(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(21, 100, 5);
        List<Long> primes = primeNumberGenerator.getPrimes(primeNumberRequest);

        assertThat("Prime Numbers", primes, containsInAnyOrder(23L, 29L, 31L, 37L, 41L));

    }

    /**
     * Get Prime numbers where range requested is bounded by  primes
     */
    @Test
    public void testCanGetPrimeNumbersWhenRequestedRangeIsBoundedByPrimes(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(23, 41, 10);
        List<Long> primes = primeNumberGenerator.getPrimes(primeNumberRequest);

        assertThat("Prime Numbers", primes, containsInAnyOrder(23L, 29L, 31L, 37L, 41L));

    }

    /**
     * Get a single prime number when the start and end parameters are equal and a prime number
     */
    @Test
    public void testCanGetSinglePrimeNumberWhenStartAndEndAreEqual(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(5, 5, 10);
        List<Long> primes = primeNumberGenerator.getPrimes(primeNumberRequest);

        assertThat("Prime Numbers", primes, containsInAnyOrder(5L));

    }

    /**
     * Get a single prime number when the start and end parameters are equal and not a prime number
     */
    @Test
    public void testCanGetZeroPrimeNumberWhenStartAndEndAreEqualButNotPrime(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(4, 4, 10);
        List<Long> primes = primeNumberGenerator.getPrimes(primeNumberRequest);

        assertThat("Prime Numbers", primes, is(empty()));

    }

    /**
     * Throw an exception when limit is less than one
     */
    @Test
    public void testCanThrowExceptionWhenLimitisLessThanOne(){

        expectedException.expect(InvalidRequestException.class);
        expectedException.expectMessage("PrimeNumberRequest.limit is less than 1");
        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 10, 0);
        primeNumberGenerator.getPrimes(primeNumberRequest);

    }

    /**
     * Throw an exception when start parameter id greater than end
     */
    @Test
    public void testCanThrowExceptionWhenStartIsGreaterThanEnd(){

        expectedException.expect(InvalidRequestException.class);
        expectedException.expectMessage("PrimeNumberRequest.end is greater than PrimeNumberRequest.start");
        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(20, 10, 10);
        primeNumberGenerator.getPrimes(primeNumberRequest);

    }

    /**
     * Can Handle max end value
     */
    @Test
    @Ignore
    public void testCanHandleMaximumEndValuerThanEnd(){

        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(Integer.MAX_VALUE - 1000, Integer.MAX_VALUE, 10);
        List<Long> primes = primeNumberGenerator.getPrimes(primeNumberRequest);

        assertThat("Prime Numbers", primes, hasSize(10));

    }

}
