package com.amcg.valid;

import com.amcg.web.request.PrimeNumberRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate a PrimeNumberRequest
 */
public class PrimeNumberRequestValidator implements ConstraintValidator<PrimeNumberRequestValid, PrimeNumberRequest> {

    @Override
    public void initialize(PrimeNumberRequestValid primeNumberRequestValid) {

    }

    @Override
    public boolean isValid(PrimeNumberRequest primeNumberRequest, ConstraintValidatorContext constraintValidatorContext) {

        if (primeNumberRequest.getEnd() < primeNumberRequest.getStart()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("end").addConstraintViolation();
            return false;
        }


        return true;
    }
}
