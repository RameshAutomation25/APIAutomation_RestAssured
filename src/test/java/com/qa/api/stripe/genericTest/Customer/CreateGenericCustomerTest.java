package com.qa.api.stripe.genericTest.Customer;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.response.Response;

public class CreateGenericCustomerTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	
	@Test
	public void createUserAPITestWithInvalidContentType() {
		
		Map<String, String> tokenMap = new HashMap<String, String>();
		
		tokenMap.put("Authorization", "Bearer "+token);
		
		Map<String, Object> formParameters = new HashMap<String, Object>();
		formParameters.put("description", "create Customer");
		formParameters.put("balance", 2000);
		formParameters.put("email", "test1@gmail.com");
		formParameters.put("name", "Kevin");
		
		Response response = RestClient.doPost(baseUri, basePath, tokenMap, "JSON", null, true, formParameters);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 400);
	}
	
	
	
	@Test
	public void createUserAPITest() {
		
		Map<String, String> tokenMap = new HashMap<String, String>();
		
		tokenMap.put("Authorization", "Bearer "+token);
		
		Map<String, Object> formParameters = new HashMap<String, Object>();
		formParameters.put("description", "create Customers");
		formParameters.put("balance", 3000);
		formParameters.put("email", "test2@gmail.com");
		formParameters.put("name", "Kevin mayer");
		
		Response response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formParameters);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "Kevin mayer");
		Assert.assertEquals(response.jsonPath().getString("email"), "test2@gmail.com");
		Assert.assertEquals(response.jsonPath().getString("balance"), "3000");
		
		
		System.out.println("----------------------------------------------------------------------------");
		
		Response responseGet = RestClient.doGet(baseUri, basePath, tokenMap, "JSON", null, true);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "Kevin mayer");
		Assert.assertEquals(response.jsonPath().getString("email"), "test2@gmail.com");
		Assert.assertEquals(response.jsonPath().getString("balance"), "3000");
	}
}
