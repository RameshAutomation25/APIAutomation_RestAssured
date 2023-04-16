package com.qa.api.stripe.genericTest.CustomerBalance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.response.Response;

public class RetrieveACustomerAllBalanceGenericTest {

	String baseUri = "https://api.stripe.com";
	String basePath;
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	Map<String, String> tokenMap = new HashMap<String, String>();
	Map<String, String> params = new HashMap<String, String>();
	Map<String, String> formPayload = new HashMap<String, String>();
	Response response;
	
	
	
	@Test
	public void retrieveACustomersAllBalancewith_ValidDetails() {
		
		tokenMap.put("Authorization", "Bearer " + token);
		basePath = "/v1/customers";

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", params, true, null);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());

		Assert.assertEquals(response.statusCode(), 200);
		String customerId = response.jsonPath().getString("id");
		System.out.println(customerId);

		System.out.println("-----------------------------------------------------------------------");

		basePath = "/v1/customers/{custId}/balance_transactions";

		params.put("custId", customerId);
		formPayload.put("amount", "5000");
		formPayload.put("currency", "inr");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", params, true, formPayload);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("customer"), customerId);
		Assert.assertEquals(response.jsonPath().getString("amount"), "5000");
		Assert.assertEquals(response.jsonPath().getString("currency"), "inr");

		String transactionId = response.jsonPath().getString("id");
		System.out.println(transactionId);

		System.out.println("-----------------------------------------------------------------------");
		
		basePath = "/v1/customers/{custId}/balance_transactions";
		params.put("custId", customerId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("data[0].id"), transactionId);
		
		List<Map<String, Object>> transactions = response.jsonPath().getList("data");
		Assert.assertEquals(transactions.size(), 1);
		
		
		System.out.println("-----------------------------------------------------------------------");

		basePath = "/v1/customers/{custId}/balance_transactions";

		params.put("custId", customerId);
		formPayload.put("amount", "6000");
		formPayload.put("currency", "usd");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", params, true, formPayload);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("customer"), customerId);
		Assert.assertEquals(response.jsonPath().getString("amount"), "6000");
		Assert.assertEquals(response.jsonPath().getString("currency"), "usd");

		String transactionId1 = response.jsonPath().getString("id");
		System.out.println(transactionId1);
		
		
		
		System.out.println("-----------------------------------------------------------------------");
		
		basePath = "/v1/customers/{custId}/balance_transactions";
		params.put("custId", customerId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("data[0].id"), transactionId1);
		
		List<Map<String, Object>> transactions1 = response.jsonPath().getList("data");
		Assert.assertEquals(transactions1.size(), 2);
	}
	
	
	@Test
	public void retrieveACustomersAllBalancewith_InvalidCustomerId() {
		
		basePath = "/v1/customers/{custId}/balance_transactions";
		String invalidCustomerId = "sfdsf";
		tokenMap.put("Authorization", "Bearer " + token);
		params.put("custId", invalidCustomerId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 404);
	}
	
	
	@Test
	public void retrieveACustomersAllBalancewith_InvalidToken() {
		
		String noToken = "";
		tokenMap.put("Authorization", "Bearer " + token);
		basePath = "/v1/customers";

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", params, true, null);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());

		Assert.assertEquals(response.statusCode(), 200);
		String customerId = response.jsonPath().getString("id");
		System.out.println(customerId);

		System.out.println("-----------------------------------------------------------------------");

		basePath = "/v1/customers/{custId}/balance_transactions";
		tokenMap.put("Authorization", "Bearer " + noToken);
		params.put("custId", customerId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 401);
	}
	
	
	@Test
	public void retrieveACustomersAllBalanceWithNoTransactions() {

		tokenMap.put("Authorization", "Bearer " + token);
		basePath = "/v1/customers";

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", params, true, null);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());

		Assert.assertEquals(response.statusCode(), 200);
		String customerId = response.jsonPath().getString("id");
		System.out.println(customerId);

		System.out.println("-----------------------------------------------------------------------");

		basePath = "/v1/customers/{custId}/balance_transactions";

		params.put("custId", customerId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("has_more"), "false");
	}
}
