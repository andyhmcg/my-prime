# my-prime

REST Implementation of Prime Number Algorithms

To get a copy of this repo use
https://github.com/andyhmcg/my-prime.git

To build the application and run unit tests run
mvn clean install

To Buld and run unit tests and integration test
mnc clean install -Pintegration-test

To starte the application use

mvn clean package spring-booy:run

REST API usage

The endpoint is
http://localhost/8080/prime

The endpoint can take 3 optional parameters

start. The start of the number range to look for primes. This can be in the range 0 - Integer.MAX. Default value 0
end. The end of the range to look for primes. This can be in the range 0 - Integer.MAX. And must not be less than start. Default value 100
limit. The number of primes to return. can be in the range 1 - Integer.MAX. Default value 25

Sample usage
http://localhost/8080/prime
will return the 25 primes between 0 1nd 100. The default parameter values
{
	"data": [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
}

http://http://localhost:8080/prime?start=200&&end=300&limit=5
will return
{
	"data": [211, 223, 227, 229, 233]
}


There are 3 implementations of the prime number generator
To enable a particular implementation enable the appropriate @Bean in
com.amcg.boot.AppConfig.

