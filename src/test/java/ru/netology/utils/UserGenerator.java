package ru.netology.utils;

import ru.netology.dto.RegistrationDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Random;

public class UserGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    
    private static final Random random = new Random();
    
    public static RegistrationDto registerRandomUser() {
        String login = "user_" + System.currentTimeMillis() + random.nextInt(1000);
        String password = "pass_" + System.currentTimeMillis();
        String status = random.nextBoolean() ? "active" : "blocked";
        
        RegistrationDto user = new RegistrationDto(login, password, status);
        
        RestAssured.given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        
        return user;
    }
    
    public static RegistrationDto registerUser(RegistrationDto user) {
        RestAssured.given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        
        return user;
    }
    
    public static RegistrationDto generateRandomUserData() {
        String login = "user_" + System.currentTimeMillis() + random.nextInt(1000);
        String password = "pass_" + System.currentTimeMillis();
        String status = random.nextBoolean() ? "active" : "blocked";
        
        return new RegistrationDto(login, password, status);
    }
}
