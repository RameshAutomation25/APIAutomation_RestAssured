package com.qa.api.stripe.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;
import com.qa.api.stripe.util.ResponseCheck;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetCustomerTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	String customerId = "cus_NbrUFPkTL3ANyL";
	String deletedcustomerId = "cus_NaMdwZLUgEwvDI";
	String invalidcustomerId = "cus_NaMdwZLUgEwvDI123";
	String deleted = "true";
	String noToken = "";
	
	@Test
	public void getCustomerWithId() {
		
		RestAssured.baseURI=baseUri;
		
		Response response =
		
		given().log().all()
			.pathParam("cID", customerId)
			.header("Authorization", "Bearer "+token)
		.when()
			.get("/v1/customers/{cID}");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	
	@Test
	public void getWithDeletedCustomerId() {
		
		RestAssured.baseURI=baseUri;
		
		Response response =
		
		given().log().all()
			.pathParam("cID", deletedcustomerId)
			.header("Authorization", "Bearer "+token)
		.when()
			.get("/v1/customers/{cID}");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		String customerIdresp = response.jsonPath().getString("id");
		String isDeleted = response.jsonPath().getString("deleted");
		ResponseCheck.validationEqualsCheck(customerIdresp, deletedcustomerId);
		ResponseCheck.validationEqualsCheck(isDeleted, deleted);
		
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	
	
	@Test
	public void getWithInvalidCustomerId() {
		
		RestAssured.baseURI=baseUri;
		
		Response response =
		
		given().log().all()
			.pathParam("cID", invalidcustomerId)
			.header("Authorization", "Bearer "+token)
		.when()
			.get("/v1/customers/{cID}");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		String mesg = response.jsonPath().getString("error.message");
		ResponseCheck.validationEqualsCheck(mesg, "No such customer: 'cus_NaMdwZLUgEwvDI123'");
		Assert.assertEquals(response.statusCode(), 404);
	}
	
	
	
	@Test
	public void getWithInvalidToken() {
		
		RestAssured.baseURI=baseUri;
		
		Response response =
		
		given().log().all()
			.pathParam("cID", invalidcustomerId)
			.header("Authorization", "Bearer "+noToken)
		.when()
			.get("/v1/customers/{cID}");
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		String mesg = response.jsonPath().getString("error.message");
		ResponseCheck.validationContainsCheck(mesg, "You did not provide an API key");
		Assert.assertEquals(response.statusCode(), 401);
	}
}
