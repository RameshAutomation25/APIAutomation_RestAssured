package com.qa.api.stripe.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class DeleteCustomerTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers/{custID}";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	String customerId = "cus_NbrUFPkTL3ANyL";
	String noToken = "";
	
	@Test
	public void deleteCustomerDetails() {
		
		RestAssured.baseURI=baseUri;
		
		given()
			.pathParam("custID", customerId)
			.headers("Authorization", "Bearer "+token)
		.when()
			.delete(basePath)
		.then()
			.assertThat()
				.statusCode(200);
				
	}
	
	
	
	@Test
	public void deleteCustomerDetailsWithoutToken() {
		
		RestAssured.baseURI=baseUri;
		
		given()
			.pathParam("custID", customerId)
			.headers("Authorization", "Bearer "+noToken)
		.when()
			.post(basePath)
		.then()
			.assertThat()
				.statusCode(401);
				
				
	}
	
	
	
	@Test
	public void deleteAlreadyDeletedCustomer() {
		
		RestAssured.baseURI=baseUri;
		
		given().log().all()
			.pathParam("custID", customerId)
			.headers("Authorization", "Bearer "+token)
		.when().log().all()
			.post(basePath)
		.then().log().all()
			.assertThat()
				.statusCode(400)
				.body("error.message", equalTo("No such customer: 'cus_NbrUFPkTL3ANyL'"));
				
				
	}
}
