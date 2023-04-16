package com.qa.api.stripe.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class UpdateCustomerBalanceTest {

	String baseUri = "https://api.stripe.com";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	JsonPath responseBody;

	@Test
	public void updateCustomerBalanceWithValidOptionalDetails() {
		
		//Create Customer
		RestAssured.baseURI=baseUri;
		
		String customerId = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
		.when().log().all()
			.post("/v1/customers")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath().getString("id");
		
		System.out.println(customerId);
			
			System.out.println("----------------------------------------------------------------------");
			
		//Create Customer Balance	
		
		responseBody = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.formParam("amount", 2000)
			.formParam("currency", "inr")
		.when().log().all()
			.post("/v1/customers/{custId}/balance_transactions")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath();
		
		String transID = responseBody.getString("id");
		
		System.out.println(transID);
		Assert.assertEquals(responseBody.getString("customer"), customerId);
		
			
			System.out.println("----------------------------------------------------------------------");
			
		//Update Customer Balance
		
		responseBody =
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.pathParam("transactId", transID)
			.formParam("metadata[order_id]", 1000)
			.formParam("description", "Hello Testing")
		.when().log().all()
			.post("/v1/customers/{custId}/balance_transactions/{transactId}")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.assertThat()
					.extract().jsonPath();
					
		Assert.assertEquals(responseBody.getString("customer"), customerId);
		Assert.assertEquals(responseBody.getString("id"), transID);
			
		System.out.println("----------------------------------------------------------------------");
		
		//GET Customer Balance
		
		responseBody =
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.pathParam("transactId", transID)
		.when().log().all()
			.get("/v1/customers/{custId}/balance_transactions/{transactId}")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath();
		Assert.assertEquals(responseBody.getString("customer"), customerId);
		Assert.assertEquals(responseBody.getString("id"), transID);
		Assert.assertEquals(responseBody.getInt("metadata.order_id"), 1000);
		Assert.assertEquals(responseBody.getString("description"), "Hello Testing");
		
		System.out.println("----------------------------------------------------------------------");	
	}

	@Test
	public void updateCustomerBalanceWithInvalidCustomerID() {
		
		String invalidCustID = "test";
		//Create Customer
				RestAssured.baseURI=baseUri;
				
				String customerId = 
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + token)
				.when().log().all()
					.post("/v1/customers")
				.then().log().all()
					.assertThat()
						.statusCode(200)
					.and()
						.extract().jsonPath().getString("id");
				
				System.out.println(customerId);
					
					System.out.println("----------------------------------------------------------------------");
					
				//Create Customer Balance	
				
				responseBody = 
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + token)
					.pathParam("custId", customerId)
					.formParam("amount", 2000)
					.formParam("currency", "inr")
				.when().log().all()
					.post("/v1/customers/{custId}/balance_transactions")
				.then().log().all()
					.assertThat()
						.statusCode(200)
					.and()
						.extract().jsonPath();
				
				String transID = responseBody.getString("id");
				
				System.out.println(transID);
				Assert.assertEquals(responseBody.getString("customer"), customerId);
				
					
					System.out.println("----------------------------------------------------------------------");
					
				//Update Customer Balance
				
				
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + token)
					.pathParam("custId", invalidCustID)
					.pathParam("transactId", transID)
					.formParam("metadata[order_id]", 1000)
					.formParam("description", "Hello Testing")
				.when().log().all()
					.post("/v1/customers/{custId}/balance_transactions/{transactId}")
				.then().log().all()
					.assertThat()
						.statusCode(404)
					.and()
						.body("error.message", containsString("No such customer"));
					
				System.out.println("----------------------------------------------------------------------");
	}

	@Test
	public void updateCustomerBalanceWithInvalidTransactionID() {
		
		String invalidTransId = "test";
		//Create Customer
		RestAssured.baseURI=baseUri;
		
		String customerId = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
		.when().log().all()
			.post("/v1/customers")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath().getString("id");
		
		System.out.println(customerId);
			
			System.out.println("----------------------------------------------------------------------");
			
		//Create Customer Balance	
		
		responseBody = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.formParam("amount", 2000)
			.formParam("currency", "inr")
		.when().log().all()
			.post("/v1/customers/{custId}/balance_transactions")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath();
		
		String transID = responseBody.getString("id");
		
		System.out.println(transID);
		Assert.assertEquals(responseBody.getString("customer"), customerId);
		
			
			System.out.println("----------------------------------------------------------------------");
			
		//Update Customer Balance
		
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.pathParam("transactId", invalidTransId)
			.formParam("metadata[order_id]", 1000)
			.formParam("description", "Hello Testing")
		.when().log().all()
			.post("/v1/customers/{custId}/balance_transactions/{transactId}")
		.then().log().all()
			.assertThat()
				.statusCode(404)
			.and()
				.body("error.message", containsString("No such customer balance transaction"));
			
		System.out.println("----------------------------------------------------------------------");
	}

	@Test
	public void updateCustomerBalanceWithInvalidToken() {
		
		String invalidToken = "test";
				//Create Customer
				RestAssured.baseURI=baseUri;
				
				String customerId = 
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + token)
				.when().log().all()
					.post("/v1/customers")
				.then().log().all()
					.assertThat()
						.statusCode(200)
					.and()
						.extract().jsonPath().getString("id");
				
				System.out.println(customerId);
					
					System.out.println("----------------------------------------------------------------------");
					
				//Create Customer Balance	
				
				responseBody = 
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + token)
					.pathParam("custId", customerId)
					.formParam("amount", 2000)
					.formParam("currency", "inr")
				.when().log().all()
					.post("/v1/customers/{custId}/balance_transactions")
				.then().log().all()
					.assertThat()
						.statusCode(200)
					.and()
						.extract().jsonPath();
				
				String transID = responseBody.getString("id");
				
				System.out.println(transID);
				Assert.assertEquals(responseBody.getString("customer"), customerId);
				
					
					System.out.println("----------------------------------------------------------------------");
					
				//Update Customer Balance
				
				
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + invalidToken)
					.pathParam("custId", customerId)
					.pathParam("transactId", transID)
					.formParam("metadata[order_id]", 1000)
					.formParam("description", "Hello Testing")
				.when().log().all()
					.post("/v1/customers/{custId}/balance_transactions/{transactId}")
				.then().log().all()
					.assertThat()
						.statusCode(401)
					.and()
						.body("error.message", containsString("Invalid API Key provided"));
					
				System.out.println("----------------------------------------------------------------------");
	}

	
	
	@Test
	public void updateCustomerBalanceWithNoToken() {
		
		String invalidToken = "";
				//Create Customer
				RestAssured.baseURI=baseUri;
				
				String customerId = 
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + token)
				.when().log().all()
					.post("/v1/customers")
				.then().log().all()
					.assertThat()
						.statusCode(200)
					.and()
						.extract().jsonPath().getString("id");
				
				System.out.println(customerId);
					
					System.out.println("----------------------------------------------------------------------");
					
				//Create Customer Balance	
				
				responseBody = 
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + token)
					.pathParam("custId", customerId)
					.formParam("amount", 2000)
					.formParam("currency", "inr")
				.when().log().all()
					.post("/v1/customers/{custId}/balance_transactions")
				.then().log().all()
					.assertThat()
						.statusCode(200)
					.and()
						.extract().jsonPath();
				
				String transID = responseBody.getString("id");
				
				System.out.println(transID);
				Assert.assertEquals(responseBody.getString("customer"), customerId);
				
					
					System.out.println("----------------------------------------------------------------------");
					
				//Update Customer Balance
				
				
				RestAssured.given().log().all()
					.contentType(ContentType.URLENC)
					.header("Authorization", "Bearer " + invalidToken)
					.pathParam("custId", customerId)
					.pathParam("transactId", transID)
					.formParam("metadata[order_id]", 1000)
					.formParam("description", "Hello Testing")
				.when().log().all()
					.post("/v1/customers/{custId}/balance_transactions/{transactId}")
				.then().log().all()
					.assertThat()
						.statusCode(401)
					.and()
						.body("error.message", containsString("You did not provide an API key"));
					
				System.out.println("----------------------------------------------------------------------");
	}
	
	
	
	@Test
	public void updateCustomerBalanceWithoutOptionalDetails() {
		//Create Customer
		RestAssured.baseURI=baseUri;
		
		String customerId = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
		.when().log().all()
			.post("/v1/customers")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath().getString("id");
		
		System.out.println(customerId);
			
			System.out.println("----------------------------------------------------------------------");
			
		//Create Customer Balance	
		
		responseBody = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.formParam("amount", 2000)
			.formParam("currency", "inr")
		.when().log().all()
			.post("/v1/customers/{custId}/balance_transactions")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath();
		
		String transID = responseBody.getString("id");
		
		System.out.println(transID);
		Assert.assertEquals(responseBody.getString("customer"), customerId);
		
			
			System.out.println("----------------------------------------------------------------------");
			
		//Update Customer Balance
		
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("custId", customerId)
			.pathParam("transactId", transID)
		.when().log().all()
			.post("/v1/customers/{custId}/balance_transactions/{transactId}")
		.then().log().all()
			.assertThat()
				.statusCode(200);
			
		System.out.println("----------------------------------------------------------------------");
	}
}
