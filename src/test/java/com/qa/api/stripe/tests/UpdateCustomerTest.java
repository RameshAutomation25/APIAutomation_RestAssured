package com.qa.api.stripe.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

public class UpdateCustomerTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	String custID = "cus_NbrTFUxcmx3vX5";
	String deletedID = "cus_Nd1sZJOUhBhAC8";
	String noToken = "";
	
	@Test
	public void updateCustomerDetails() {
		
		RestAssured.baseURI=baseUri;
		
		given()
			.headers("Authorization", "Bearer "+token)
			.formParam("email", "testemail@gmail.com")
			.formParam("name", "testname")
			.formParam("phone", "1111111111")
		.when()
			.post("/v1/customers/"+custID)
		.then()
			.assertThat()
				.body("email", equalTo("testemail@gmail.com"))
				.body("name", equalTo("testname"))
				.body("phone", equalTo("1111111111"))
			.and()
				.statusCode(200);
				
				
	}
	
	
	
	@Test
	public void updateCustomerDetailsWithoutToken() {
		
		RestAssured.baseURI=baseUri;
		
		given()
			.headers("Authorization", "Bearer "+noToken)
			.formParam("email", "testemail@gmail.com")
			.formParam("name", "testname")
			.formParam("phone", "1111111111")
		.when()
			.post("/v1/customers/"+custID)
		.then()
			.assertThat()
				.statusCode(401);
				
				
	}
	
	
	
	@Test
	public void updateDeletedCustomerDetails() {
		
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		Map<String, String> formBody = new HashMap<String, String>();
		formBody.put("email", "kev1@gmail.com");
		formBody.put("name", "kev1");
		formBody.put("phone", "+110000000000");

		System.out.println("------------------------------------------------------------------");

		Response responseUpdate = RestClient.doPost(baseUri, basePath+"/"+deletedID, tokenMap, "forms", null, true, formBody);
	
		System.out.println(responseUpdate.statusCode());
		System.out.println(responseUpdate.prettyPrint());
		
		Assert.assertEquals(responseUpdate.statusCode(), 400);
		//"error.message", "No such customer"
		JsonPath responseBody = responseUpdate.body().jsonPath();
		String mesg = responseBody.getString("error.message");
		mesg.equals("No such customer: "+"'"+deletedID+"'");
	}
}
