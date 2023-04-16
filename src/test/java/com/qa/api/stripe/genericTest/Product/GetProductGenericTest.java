package com.qa.api.stripe.genericTest.Product;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.response.Response;

public class GetProductGenericTest {

	String baseUri = "https://api.stripe.com";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	Response response;
	Map<String, String> tokenMap = new HashMap<String, String>();
	Map<String, String> formBody = new HashMap<String, String>();
	Map<String, String> paramsMap = new HashMap<String, String>();
	String basePath;
	String responseBody;
	
	
	@Test
	public void getProductWithValidId() {
		basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("name", "product10");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String productID = response.jsonPath().getString("id");
		System.out.println(productID);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "product10");
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
		
		System.out.println("------------------------------------------------------------------");
		
		basePath = "/v1/products/{prodId}";
		
		paramsMap.put("prodId", productID);
		
		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", paramsMap, true);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("id"), productID);
		Assert.assertEquals(response.jsonPath().getString("name"), "product10");
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
	}
	
	
	
	@Test
	public void getProductWithInvalidId() {
		String basePath = "/v1/products/{prodId}";
		String invalidId = "test@#$";
		
		paramsMap.put("prodId", invalidId);
		tokenMap.put("Authorization", "Bearer " + token);
		
		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", paramsMap, true);
		
		responseBody = response.asString();
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(true, responseBody.contains("No such product"));
		Assert.assertEquals(response.jsonPath().getString("error.param"), "id");
	}
	
	
	
	@Test
	public void getProductWithNoToken() {
		basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("name", "product20");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String productID = response.jsonPath().getString("id");
		System.out.println(productID);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "product20");
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
		
		System.out.println("------------------------------------------------------------------");
		
		basePath = "/v1/products/{prodId}";
		String noToken = "";
		
		tokenMap.put("Authorization", "Bearer " + noToken);
		
		paramsMap.put("prodId", productID);
		
		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", paramsMap, true);
		
		responseBody = response.asString();
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(true, responseBody.contains("You did not provide an API key."));
	}
	
	
	
	
	@Test
	public void getProductWithInvalidToken() {
		basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("name", "product20");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String productID = response.jsonPath().getString("id");
		System.out.println(productID);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "product20");
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
		
		System.out.println("------------------------------------------------------------------");
		
		basePath = "/v1/products/{prodId}";
		String invalidToken = "test";
		
		tokenMap.put("Authorization", "Bearer " + invalidToken);
		
		paramsMap.put("prodId", productID);
		
		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", paramsMap, true);
		
		responseBody = response.asString();
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(true, responseBody.contains("Invalid API Key provided"));
	}
	
	
	
	@Test
	public void getProductWithDeletedProductId() {
		basePath = "/v1/products";
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("name", "product10");
		
		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String productID = response.jsonPath().getString("id");
		System.out.println(productID);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"), "product10");
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
		
		System.out.println("------------------------------------------------------------------");
		
		basePath = "/v1/products/{prodId}";
		paramsMap.put("prodId", productID);
		
		response = RestClient.doDelete(baseUri, basePath, tokenMap, "forms", paramsMap, true);
				
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("id"), productID);
		Assert.assertEquals(response.jsonPath().getString("object"), "product");
		Assert.assertEquals(response.jsonPath().getBoolean("deleted"), true);
		
		System.out.println("------------------------------------------------------------------");
		
		basePath = "/v1/products/{prodId}";
		
		paramsMap.put("prodId", productID);
		
		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", paramsMap, true);
		
		responseBody = response.asString();
		
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(true, responseBody.contains("No such product"));
		Assert.assertEquals(response.jsonPath().getString("error.param"), "id");
	}
	
	
	
	
	
	
}
