package com.amcg.service;
import com.amcg.boot.AppConfig;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@IntegrationTest
public class PrimeServiceIntegrationTest {


    RequestSpecification requestSpecification;

    @Before
    public void setup(){
        requestSpecification = getSpec();
    }

    @Test
    public void getPrimeNumbers() {


        given(requestSpecification).when().get("prime").prettyPeek().then()
                .statusCode(200)
                .body("data", hasItems(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97));

    }

    public RequestSpecification getSpec() {

        return new RequestSpecBuilder() //
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost") //
                .setPort(8080) //
                .setBasePath("") //
                .setRelaxedHTTPSValidation()
                .build().log().all();
    }


}
