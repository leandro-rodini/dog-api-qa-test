package com.dogapi.tests;

import com.dogapi.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Lista de raças")
public class ListAllBreedsTest extends BaseTest {

    @Test
    @DisplayName("Lista todas as raças retorna 200")
    void listAllBreedsReturnsOk() {
        given()
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Lista todas as raças valida schema básico")
    void listAllBreedsSchemaIsValid() {
        given()
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message", instanceOf(Map.class));
    }

    @Test
    @DisplayName("Lista todas as raças não está vazia")
    void listAllBreedsIsNotEmpty() {
        given()
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("message", notNullValue())
            .body("message.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Lista todas as raças responde rápido")
    void listAllBreedsRespondsFast() {
        given()
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .time(lessThan(2000L));
    }
}
