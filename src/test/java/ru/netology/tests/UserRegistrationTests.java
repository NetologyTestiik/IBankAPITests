package ru.netology.tests;

import ru.netology.dto.RegistrationDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static io.restassured.RestAssured.given;

public class UserRegistrationTests {
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
    void shouldCreateMultipleUsers() {
        for (int i = 0; i < 3; i++) {
            RegistrationDto user = new RegistrationDto(
                "multi_user_" + UUID.randomUUID().toString().substring(0, 8),
                "pass_" + i,
                i % 2 == 0 ? "active" : "blocked"
            );
            
            given()
                    .spec(requestSpec)
                    .body(user)
            .when()
                    .post("/api/system/users")
            .then()
                    .statusCode(200);
        }
    }
    
    @Test
    void shouldAcceptVariousLoginFormats() {
        String[] logins = {
            "simplelogin",
            "login-with-dash",
            "login.with.dots",
            "login_with_underscore",
            "LoginWithCaps",
            "login123",
            "123login"
        };
        
        for (String login : logins) {
            RegistrationDto user = new RegistrationDto(
                login + "_" + System.currentTimeMillis(),
                "password",
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
    }
    
    @Test
    void shouldAcceptVariousPasswordFormats() {
        String[] passwords = {
            "simple",
            "123456",
            "password123",
            "PASSWORD123",
            "PassWord123",
            "special!@#",
            "with spaces password"
        };
        
        for (String password : passwords) {
            RegistrationDto user = new RegistrationDto(
                "user_" + UUID.randomUUID().toString().substring(0, 6),
                password,
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
    }
}
