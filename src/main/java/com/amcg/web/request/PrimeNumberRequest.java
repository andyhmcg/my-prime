package com.amcg.web.request;


import com.amcg.valid.PrimeNumberRequestValid;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@PrimeNumberRequestValid
public class PrimeNumberRequest {

    private final static String maxIntValue = "2147483647";

    @DecimalMax(value = maxIntValue)
    @DecimalMin(value = "0")
    private int start;
    @DecimalMax(maxIntValue)
    @DecimalMin(value = "0")
    private int end;
    @DecimalMax(maxIntValue)
    @DecimalMin("1")
    private int limit;

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PrimeNumberRequest that = (PrimeNumberRequest) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(start, that.start)
                .append(end, that.end)
                .append(limit, that.limit)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(start)
                .append(end)
                .append(limit)
                .toHashCode();
    }

    public PrimeNumberRequest(int start, int end, int limit) {
        this.start = start;
        this.end = end;
        this.limit = limit;
    }
}
