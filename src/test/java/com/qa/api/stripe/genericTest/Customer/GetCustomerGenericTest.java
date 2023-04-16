package com.qa.api.stripe.genericTest.Customer;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.response.Response;

public class GetCustomerGenericTest {

	String baseURI="https://api.stripe.com";
	String basePath="/v1/customers/{custId}";
	String token="sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrw"
			+ "jeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	String customerId = "cus_Na0OuG3s3gjvU012";
	String noToken = "";
	
	
	@Test
	public void getCustomerWithId() {
		
		Map<String, String> authToken = new HashMap<String, String>();
		authToken.put("Authorization", "Bearer "+token);
		
		Map<String, String> pathparam = new HashMap<String, String>();
		pathparam.put("custId", customerId);
		
		Response response = RestClient.doGet(baseURI, basePath, authToken, "JSON", pathparam, true);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("id"), customerId);
	}
	
	
	@Test
	public void getWithDeletedCustomerId() {
		
		Map<String, String> authToken = new HashMap<String, String>();
		authToken.put("Authorization", "Bearer "+token);
		
		Map<String, String> pathparam = new HashMap<String, String>();
		pathparam.put("custId", "cus_NaMdwZLUgEwvDI");
		
		Response response = RestClient.doGet(baseURI, basePath, authToken, "JSON", pathparam, true);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("id"), "cus_NaMdwZLUgEwvDI");
		Assert.assertEquals(response.jsonPath().getString("deleted"), "true");
	}
	
	
	@Test
	public void getWithInvalidCustomerId() {
		
		Map<String, String> authToken = new HashMap<String, String>();
		authToken.put("Authorization", "Bearer "+token);
		
		Map<String, String> pathparam = new HashMap<String, String>();
		pathparam.put("custId", customerId);
		
		Response response = RestClient.doGet(baseURI, basePath, authToken, "JSON", pathparam, true);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.statusCode(), 404);
		Assert.assertEquals(response.jsonPath().getString("error.message"), "No such customer: "+"'"+customerId+"'");
	}
	
	
	
	@Test
	public void getWithInvalidToken() {
		
		Map<String, String> authToken = new HashMap<String, String>();
		authToken.put("Authorization", "Bearer "+noToken);
		
		Map<String, String> pathparam = new HashMap<String, String>();
		pathparam.put("custId", customerId);
		
		Response response = RestClient.doGet(baseURI, basePath, authToken, "JSON", pathparam, true);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(response.statusCode(), 401);
	}
}
