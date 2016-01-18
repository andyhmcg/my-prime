package com.amcg.concurrency;


public class PrimeNumberResult {
    private int number;

    public boolean isPrime() {
        return isPrime;
    }

    public void setIsPrime(boolean isPrime) {
        this.isPrime = isPrime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private boolean isPrime;

    public PrimeNumberResult(int number, boolean isPrime) {
        this.number = number;
        this.isPrime = isPrime;
    }
}
