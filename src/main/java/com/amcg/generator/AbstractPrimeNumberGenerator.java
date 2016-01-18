package com.amcg.generator;


import com.amcg.exception.InvalidRequestException;
import com.amcg.web.request.PrimeNumberRequest;

public abstract class AbstractPrimeNumberGenerator implements PrimeNumberGenerator{

    /**
     * Sanity check on validity of request
     * @param primeNumberRequest request params
     * @return true if request is valid other wise false
     */
    protected boolean isRequestValid(PrimeNumberRequest primeNumberRequest){
        if (primeNumberRequest.getLimit() < 1){
            throw new InvalidRequestException("PrimeNumberRequest.limit is less than 1");
        }

        if (primeNumberRequest.getEnd() < primeNumberRequest.getStart()){
            throw new InvalidRequestException("PrimeNumberRequest.end is greater than PrimeNumberRequest.start");
        }
        return true;
    }
}
