package ru.netology.tests;

import ru.netology.dto.RegistrationDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class BasicApiTests {
    private static RequestSpecification requestSpec;
    
    @BeforeAll
    static void setUpAll() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
    
    @Test
    void shouldCreateActiveUser() {
        RegistrationDto user = new RegistrationDto(
            "active_user_" + System.currentTimeMillis(),
            "password123",
            "active"
        );
        
        given()
                .spec(requestSpec)
                .body(user)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
    }
    
    @Test
    void shouldCreateBlockedUser() {
        RegistrationDto user = new RegistrationDto(
            "blocked_user_" + System.currentTimeMillis(),
            "password456",
            "blocked"
        );
        
        given()
                .spec(requestSpec)
                .body(user)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
    }
    
    @Test
    void shouldOverwriteUserWithSameLogin() {
        String login = "same_login_" + System.currentTimeMillis();
        
        // ?????? ????????
        RegistrationDto user1 = new RegistrationDto(login, "first_pass", "active");
        given()
                .spec(requestSpec)
                .body(user1)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
        
        // ??????????
        RegistrationDto user2 = new RegistrationDto(login, "second_pass", "blocked");
        given()
                .spec(requestSpec)
                .body(user2)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
    }
}
