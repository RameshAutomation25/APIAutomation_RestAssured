package com.qa.api.stripe.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RetrieveCustomerBalanceTransaction {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers/{custId}/balance_transactions/{transId}";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	String customerId = "cus_Na0OuG3s3gjvU0";
	String transactionId = "cbtxn_1MtAMsSFLfxbYOM9fhIxkrPd";
	
	@Test
	public void retrieveCustomerBalancewith_ValidDetails() {
		
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.headers("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.pathParam("transId", transactionId)
			.contentType(ContentType.URLENC)
		.when().log().all()
			.get(basePath)
		.then().log().all()
			.assertThat()
				.body("$", hasKey("customer"))
				.body("customer", equalTo(customerId))
				.body("customer", equalTo(customerId))
				.body("id", containsString(transactionId))
				.body("object", containsString("customer_balance_transaction"))
				.body("id", equalTo(transactionId))
				.body("object", equalTo("customer_balance_transaction"))
				.statusCode(200);
	}
	
	
	
	@Test
	public void retrieveCustomerBalancewith_InvalidCustomerId() {
		
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.headers("Authorization", "Bearer " + token)
			.pathParam("custId", customerId+"s")
			.pathParam("transId", transactionId)
			.contentType(ContentType.URLENC)
		.when().log().all()
			.get(basePath)
		.then().log().all()
			.assertThat()
			.and()
				.statusCode(404);
			
	}
	
	
	
	@Test
	public void retrieveCustomerBalancewith_InvalidTransactionId() {
		
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.headers("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.pathParam("transId", transactionId+"s")
			.contentType(ContentType.URLENC)
		.when().log().all()
			.get(basePath)
		.then().log().all()
			.assertThat()
			.and()
				.statusCode(404);
			
	}
	
	
	
	@Test
	public void retrieveCustomerBalancewith_InvalidToken() {
		
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.headers("Authorization", "Bearer " + token+"s")
			.pathParam("custId", customerId)
			.pathParam("transId", transactionId)
			.contentType(ContentType.URLENC)
		.when().log().all()
			.get(basePath)
		.then().log().all()
			.assertThat()
			.and()
				.statusCode(401);
			
	}
	
	
	
}
