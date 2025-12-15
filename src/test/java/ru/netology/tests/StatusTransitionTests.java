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

public class StatusTransitionTests {
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
    void shouldChangeUserStatusFromActiveToBlocked() {
        String login = "status_change_" + System.currentTimeMillis();
        
        // ??????? ????????? ????????????
        RegistrationDto activeUser = new RegistrationDto(login, "password", "active");
        given()
                .spec(requestSpec)
                .body(activeUser)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
        
        // ?????? ?? ????????????????
        RegistrationDto blockedUser = new RegistrationDto(login, "password", "blocked");
        given()
                .spec(requestSpec)
                .body(blockedUser)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
        
        // ?????? ??????? ?? ?????????
        RegistrationDto activeAgainUser = new RegistrationDto(login, "password", "active");
        given()
                .spec(requestSpec)
                .body(activeAgainUser)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
    }
    
    @Test
    void shouldUpdatePasswordForExistingUser() {
        String login = "password_change_" + System.currentTimeMillis();
        
        // ??????? ????????????
        RegistrationDto user1 = new RegistrationDto(login, "old_password", "active");
        given()
                .spec(requestSpec)
                .body(user1)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
        
        // ????????? ??????
        RegistrationDto user2 = new RegistrationDto(login, "new_password", "active");
        given()
                .spec(requestSpec)
                .body(user2)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
        
        // ????????? ??? ???
        RegistrationDto user3 = new RegistrationDto(login, "latest_password", "blocked");
        given()
                .spec(requestSpec)
                .body(user3)
        .when()
                .post("/api/system/users")
        .then()
                .statusCode(200);
    }
}
