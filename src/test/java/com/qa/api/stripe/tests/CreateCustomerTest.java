package com.qa.api.stripe.tests;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class CreateCustomerTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	String notoken = "";
	
	@Test
	public void createCustomerAPITest() {
		
		Map<String, String> tokenMap = new HashMap<String, String>();
		
		tokenMap.put("Authorization", "Bearer "+token);
		
		RestAssured.baseURI = baseUri;
		
		RestAssured.given().log().all()
			.headers(tokenMap)
			.formParam("description", "create new1 Customer")
			.formParam("balance", 2000)
			.formParam("email", "test1@gmail.com")
			.formParam("name", "James1")
		.when().log().all()
			.post(basePath)
		.then().log().all()
			.body("balance", equalTo(2000))
			.body("description", equalTo("create new1 Customer"))
			.body("email", equalTo("test1@gmail.com"))
			.body("name", equalTo("James1"));
		
	}
	
	
	@Test
	public void createCustomerAPITestWithNoToken() {
		
		Map<String, String> tokenMap = new HashMap<String, String>();
		
		tokenMap.put("Authorization", "Bearer "+notoken);
		
		RestAssured.baseURI = baseUri;
		
		RestAssured.given().log().all()
			.headers(tokenMap)
			.formParam("description", "create new1 Customer")
			.formParam("balance", 2000)
			.formParam("email", "test1@gmail.com")
			.formParam("name", "James1")
		.when().log().all()
			.post(basePath)
		.then().log().all()
			.assertThat().statusCode(401);
		
	}
	
}
