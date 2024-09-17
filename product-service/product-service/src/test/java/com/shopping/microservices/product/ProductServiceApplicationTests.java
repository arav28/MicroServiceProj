package com.shopping.microservices.product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class ProductServiceApplicationTests {

	@ServiceConnection
	final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup()
	{
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void testCreateprod() {
		String reqBody = """
				{
				    "name": "Moto G2",
				    "description": "My first phone in college",
				    "price": 1000
				}""";

		RestAssured.given()
				.contentType("application/json")
				.body(reqBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201);
	}
}
