package com.amcg.web.controller;

import com.amcg.exception.GlobalExceptionHandler;
import com.amcg.exception.InvalidRequestException;
import com.amcg.service.PrimeService;
import com.amcg.web.request.PrimeNumberRequest;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PrimeControllerTest {

    private MockMvcRequestSpecification requestSpec;

    @Mock
    private PrimeService primeService;

    @InjectMocks
    private PrimeController controller;


    @Before
    public void  setup(){
        ExceptionHandlerExceptionResolver resolvers = createExceptionResolver();
        RestAssuredMockMvc.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setHandlerExceptionResolvers(resolvers)
                .build();
        requestSpec = given().header(HttpHeaders.ACCEPT, ContentType.JSON).contentType(ContentType.JSON);

    }

    /**
     *  Scenario: hit /prime with no parameters
     *  Should use default values for start (0), end (100) and limit (25) and return all prime numbers up to 100
     */
    @Test
    public void testHappyPath(){

        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 100, 25);

        when(primeService.getPrimes(primeNumberRequest)).thenReturn(primes);
        requestSpec.get("/prime").prettyPeek()
                .then().statusCode(200)
                .body("data", containsInAnyOrder(2, 3, 5, 7, 11, 13, 17, 19, 23, 29));

        verify(primeService).getPrimes(primeNumberRequest);
    }

    /**
     * Scenario: hit /prime?start=10.
     * Should return values between 10 and 100;
     */
    @Test
    public void testCanSupplyValueForStartRange(){

        List<Integer> primes = Arrays.asList(11, 13, 17, 19, 23, 29);
        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(10, 100, 25);

        when(primeService.getPrimes(primeNumberRequest)).thenReturn(primes);
        requestSpec.get("/prime?start=10").prettyPeek()
                .then().statusCode(200)
                .body("data", containsInAnyOrder(11, 13, 17, 19, 23, 29));

        verify(primeService).getPrimes(primeNumberRequest);
    }

    /**
     * Scenario: hit /prime?start=10.
     * Should return values between 0 and 50;
     */
    @Test
    public void testCanSupplyValueForEndRange(){

        List<Integer> primes = Arrays.asList(2, 3, 5, 7);
        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 10, 25);

        when(primeService.getPrimes(primeNumberRequest)).thenReturn(primes);
        requestSpec.get("/prime?end=10").prettyPeek()
                .then().statusCode(200)
                .body("data", containsInAnyOrder(2, 3, 5, 7));

        verify(primeService).getPrimes(primeNumberRequest);
    }

    /**
     * Scenario: hit /prime?limit=2
     * Should call service with 0, 100, 2. and return 2 prime numbers
     */
    @Test
    public void testCanSupplyValueForLimit(){

        List<Integer> primes = Arrays.asList(2, 3);
        PrimeNumberRequest primeNumberRequest = new PrimeNumberRequest(0, 100, 2);

        when(primeService.getPrimes(primeNumberRequest)).thenReturn(primes);
        requestSpec.get("/prime?limit=2").prettyPeek()
                .then().statusCode(200)
                .body("data", containsInAnyOrder(2, 3));

        verify(primeService).getPrimes(primeNumberRequest);
    }


    /**
     * Scenario: end parameter is less than start parameter
     * Should throw a 400 - bad request
     */
    @Test
    public void testEndIsLessThanStart(){

        requestSpec.get("/prime?start=10&&end=1").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("end"))
                .body("errors.message", containsInAnyOrder("Not a valid prime number request"))
                .body("errors.value", containsInAnyOrder("1"));
    }


    /**
     * Scenario: limit parameter must be greater than 1
     * Should throw a 400 - bad request
     */
    @Test
    public void testLimitNotGreaterThan1(){

        requestSpec.get("/prime?limit=0").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("limit"))
                .body("errors.message", containsInAnyOrder("must be greater than or equal to 1"))
                .body("errors.value", containsInAnyOrder("0"));
    }


    /**
     * Scenario: parameters start type long
     * Should throw a 400 - bad request
     */
    @Test
    public void testStartParametersShouldBeParsableAsInt(){

        requestSpec.get("/prime?start=A").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("start"))
                .body("errors.message", containsInAnyOrder("Failed to convert value of type [java.lang.String] to required type [int]; nested exception is java.lang.NumberFormatException: For input string: \"A\""))
                .body("errors.value", containsInAnyOrder("A"));
    }

    /**
     * Scenario: parameter start should be greater than -1
     * Should throw a 400 - bad request
     */
    @Test
    public void testStartParameterShouldBeGreaterThanMinusOne(){

        requestSpec.get("/prime?start=-1").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("start"))
                .body("errors.message", containsInAnyOrder("must be greater than or equal to 0"))
                .body("errors.value", containsInAnyOrder("-1"));
    }

    /**
     * Scenario: parameters end type long
     * Should throw a 400 - bad request
     */
    @Test
    public void testEndParametersShouldBeParseableAsInt(){

        requestSpec.get("/prime?end=A").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("end"))
                .body("errors.message", containsInAnyOrder("Failed to convert value of type [java.lang.String] to required type [int]; nested exception is java.lang.NumberFormatException: For input string: \"A\""))
                .body("errors.value", containsInAnyOrder("A"));
    }

    /**
     * Scenario: parameter end should be less that Intege.MAX
     * Should throw a 400 - bad request
     */
    @Test
    public void testEndParametersShouldBeLessThanIIntMax(){

        requestSpec.get("/prime?end=2147483648").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("end"))
                .body("errors.message", containsInAnyOrder("Failed to convert value of type [java.lang.String] to required type [int]; nested exception is java.lang.NumberFormatException: For input string: \"2147483648\""))
                .body("errors.value", containsInAnyOrder("2147483648"));
    }

    /**
     * Scenario: parameter end should be greater than -1
     * Should throw a 400 - bad request
     */
    @Test
    public void testEndParametersShouldBeGreaterThanMinusOne(){

        requestSpec.get("/prime?end=-1").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("end", "end"))
                .body("errors.message", containsInAnyOrder("Not a valid prime number request", "must be greater than or equal to 0"))
                .body("errors.value", containsInAnyOrder("-1", "-1"));
    }

    /**
     * Scenario: parameter start should be greater or equal than 0
     * Should throw a 400 - bad request
     */
    @Test
    public void testStartParametersShouldBeGreaterThanMinusOne(){

        requestSpec.get("/prime?start=-1").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("start"))
                .body("errors.message", containsInAnyOrder("must be greater than or equal to 0"))
                .body("errors.value", containsInAnyOrder("-1"));
    }

    /**
     * Scenario: parameter limit should be greater than Integer.MIN
     * Should throw a 400 - bad request
     */
    @Test
    public void testLimitParametersShouldBeGreaterThan0(){

        requestSpec.get("/prime?limit=-1").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("limit"))
                .body("errors.message", containsInAnyOrder("must be greater than or equal to 1"))
                .body("errors.value", containsInAnyOrder("-1"));
    }



    /**
     * Scenario: parameter limit  should be type integer
     * Should throw a 400 - bad request
     */
    @Test
    public void testLimitParametersShouldBeParseableAsInt(){

        requestSpec.get("/prime?limit=A").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("limit"))
                .body("errors.message", containsInAnyOrder("Failed to convert value of type [java.lang.String] to required type [int]; nested exception is java.lang.NumberFormatException: For input string: \"A\""))
                .body("errors.value", containsInAnyOrder("A"));
    }

    /**
     * Scenario: parameter limit should be less than Integer.MAX
     * Should throw a 400 - bad request
     */
    @Test
    public void testParametersShouldBeLessThanIntMax(){

        requestSpec.get("/prime?limit=2147483648").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("limit"))
                .body("errors.message", containsInAnyOrder("Failed to convert value of type [java.lang.String] to required type [int]; nested exception is java.lang.NumberFormatException: For input string: \"2147483648\""))
                .body("errors.value", containsInAnyOrder("2147483648"));
    }

    /**
     * Scenario: Service throws a InvalidRequestException
     * Should throw a 500 -
     */
    @Test
    public void testCanHandleAInvalidRequestException(){

        when(primeService.getPrimes(any(PrimeNumberRequest.class))).thenThrow(new InvalidRequestException("Test InvalidRequestException"));
        requestSpec.get("/prime").prettyPeek()
                .then().statusCode(400)
                .body("errors.id", containsInAnyOrder("Invalid Request"))
                .body("errors.message", containsInAnyOrder("Test InvalidRequestException"))
                .body("errors.value", containsInAnyOrder(""));
    }

    /**
     * Scenario: Service throws a RuntimeException
     * Should throw a 500 -
     */
    @Test
    public void testCanHandleARuntimeException(){

        when(primeService.getPrimes(any(PrimeNumberRequest.class))).thenThrow(new RuntimeException("Test RuntimeException"));
        requestSpec.get("/prime").prettyPeek()
                .then().statusCode(500)
                .body("errors.id", containsInAnyOrder("Internal Server Error"))
                .body("errors.message", containsInAnyOrder("Test RuntimeException"))
                .body("errors.value", containsInAnyOrder(""));
    }


    /**
     * Register Exception Handlers with the Standalone MockMvc instance.
     * @return the excepation handler resolver
     */
    protected ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalExceptionHandler(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

}
