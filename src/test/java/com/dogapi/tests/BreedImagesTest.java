package com.dogapi.tests;

import com.dogapi.base.BaseTest;
import com.dogapi.utils.DataProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;

@DisplayName("Imagens por raca")
public class BreedImagesTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("com.dogapi.utils.DataProvider#validBreeds")
    @DisplayName("Lista imagens por raca retorna 200")
    void listImagesReturnsOkForValidBreed(String breed) {
        given()
            .pathParam("breed", breed)
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"));
    }

    @ParameterizedTest
    @MethodSource("com.dogapi.utils.DataProvider#validBreeds")
    @DisplayName("Lista imagens por raca valida schema")
    void listImagesSchemaIsValid(String breed) {
        given()
            .pathParam("breed", breed)
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(200)
            .body("message", instanceOf(List.class))
            .body("message.size()", greaterThan(0));
    }

    @ParameterizedTest
    @MethodSource("com.dogapi.utils.DataProvider#validBreeds")
    @DisplayName("Lista imagens por raca contem o nome")
    void listImagesContainBreedName(String breed) {
        given()
            .pathParam("breed", breed)
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(200)
            .body("message", everyItem(anyOf(
                containsString("/" + breed + "/"),
                containsString("/" + breed + "-")
            )));
    }

    @ParameterizedTest
    @MethodSource("com.dogapi.utils.DataProvider#invalidBreeds")
    @DisplayName("Lista imagens por raca invalida retorna 404")
    void listImagesReturnsNotFoundForInvalidBreed(String breed) {
        given()
            .pathParam("breed", breed)
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(404)
            .body("status", equalTo("error"))
            .body("message", containsString("not found"));
    }
}
