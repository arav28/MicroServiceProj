package com.shopping.microservices.inventory;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldGetInventory() {

		var res = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=Moto_G2&quantity=101")
				.then()
				.log().all()
				.statusCode(200)
				.extract().response().as(Boolean.class);
		assertFalse(res);

	}

}
