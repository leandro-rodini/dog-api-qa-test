package com.dogapi.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.junit5.AllureJunit5;

@ExtendWith(AllureJunit5.class)
public abstract class BaseTest {

    private static final String DEFAULT_BASE_URI = "https://dog.ceo/api";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = resolveBaseUri();
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .build();
    }

    private static String resolveBaseUri() {
        String baseUri = System.getProperty("dog.api.base-url");

        if (baseUri == null || baseUri.isBlank()) {
            baseUri = System.getenv("DOG_API_BASE_URL");
        }

        return (baseUri == null || baseUri.isBlank()) ? DEFAULT_BASE_URI : baseUri;
    }
}
