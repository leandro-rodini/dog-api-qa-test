package com.dogapi.tests;

import com.dogapi.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Imagem aleatória")
public class RandomImageTest extends BaseTest {

    @Test
    @DisplayName("Imagem aleatória retorna 200")
    void randomImageReturnsOk() {
        given()
        .when()
            .get("/breeds/image/random")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"));
    }

    @Test
    @DisplayName("Imagem aleatória retorna URL válida")
    void randomImageUrlIsValid() {
        given()
        .when()
            .get("/breeds/image/random")
        .then()
            .statusCode(200)
            .body("message", matchesPattern("https://.*\\.(jpg|jpeg|png)$"));
    }

    @Test
    @DisplayName("Imagem aleatória muda entre requisições")
    void randomImageChangesAcrossRequests() {
        Set<String> images = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            String imageUrl = given()
                .when()
                    .get("/breeds/image/random")
                .then()
                    .statusCode(200)
                    .extract()
                    .path("message");

            images.add(imageUrl);
        }

        assertTrue(images.size() > 1, "Esperava imagens diferentes em requisições sequenciais");
    }
}
