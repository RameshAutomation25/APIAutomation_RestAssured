package com.qa.api.stripe.genericTest.Product;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.core.IsNot;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class CreateProductGenericTest {

	
	String baseUri = "https://api.stripe.com";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	Response response;
	Map<String, String> tokenMap = new HashMap<String, String>();
	Map<String, String> formBody = new HashMap<String, String>();
	
	@Test
	public void createProductWithValidMandatoryNameField() {
		
		String basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("name", "product");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "product");
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
	}
	
	
	
	@Test
	public void createProductWithDuplicateId() {
		String basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("name", "product1");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String productID = response.jsonPath().getString("id");
		System.out.println(productID);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "product1");
		
		System.out.println("--------------------------------------------------------------");
		//Duplicate Id
		
		formBody.put("name", "product2");
		formBody.put("id", productID);
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.jsonPath().getString("error.message"), "Product already exists.");
		Assert.assertEquals(response.jsonPath().getString("error.param"), "id");
	}
	
	
	@Test
	public void createProductWithoutName() {
		String basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.jsonPath().getString("error.message"), "Missing required param: name.");
		Assert.assertEquals(response.jsonPath().getString("error.param"), "name");
	}
	
	
	
	@Test
	public void createProductWithoutToken() {
		String basePath = "/v1/products";
		String noToken = "";
		
		tokenMap.put("Authorization", "Bearer " + noToken);
		
		formBody.put("name", "product");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		String responseBody = response.asString();
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(true, responseBody.contains("You did not provide an API key."));
	}
	
	
	
	@Test
	public void createProductWithInvalidToken() {
		String basePath = "/v1/products";
		String invalidToken = "test";
		
		tokenMap.put("Authorization", "Bearer " + invalidToken);
		
		formBody.put("name", "product");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		String responseBody = response.asString();
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(true, responseBody.contains("Invalid API Key provided"));
	}
	
	
	
	@Test
	public void createProductWithValidOptionalDetails() {
		String basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("name", "product10");
		formBody.put("active", "false");
		formBody.put("description", "This is testing");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String productID = response.jsonPath().getString("id");
		System.out.println(productID);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "product10");
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
		Assert.assertEquals(response.jsonPath().getString("active"), "false");
		Assert.assertEquals(response.jsonPath().getString("description"), "This is testing");
	}
}
