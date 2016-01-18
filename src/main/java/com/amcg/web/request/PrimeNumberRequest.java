package com.amcg.web.request;


import com.amcg.valid.PrimeNumberRequestValid;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@PrimeNumberRequestValid
public class PrimeNumberRequest {

    private final static String maxLongAsString = "9223372036854775807";

    @DecimalMax(value = maxLongAsString)
    @DecimalMin(value = "0")
    private long start;
    @DecimalMax(maxLongAsString)
    private long end;
    @DecimalMax(maxLongAsString)
    @DecimalMin("1")
    private long limit;

    public void setStart(long start) {
        this.start = start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getLimit() {
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

    public PrimeNumberRequest(long start, long end, long limit) {
        this.start = start;
        this.end = end;
        this.limit = limit;
    }
}
