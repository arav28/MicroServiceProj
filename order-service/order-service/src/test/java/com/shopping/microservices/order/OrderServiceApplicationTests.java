package com.shopping.microservices.order;

import com.shopping.microservices.order.Stubs.InventoryClientStub;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.restassured.RestAssured;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	final static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0").asCompatibleSubstituteFor("mysql"));

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
    }

	static {
		mySQLContainer.start();
	}

	@Test
	void testCreateOrder() {

		String reqBody = """
				{
				     "skuCode": "Moto_G2",
				     "price": 1000,
				     "quantity": 100
				 }""";

		//Stub Does Not Mock the Controller: The stub doesn't mock the order controller. Instead, it mocks the external dependency
		// that the controller (or some internal service in the order service) interacts with (the inventory service).
		InventoryClientStub.stubInventoryCall("Moto_G2",100);

		var response = RestAssured.given()
				.contentType("application/json")
				.body(reqBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(201)
				.extract()
				.body().asString();

		assertThat(response, Matchers.is("Order Successfully Placed"));

	}

}
