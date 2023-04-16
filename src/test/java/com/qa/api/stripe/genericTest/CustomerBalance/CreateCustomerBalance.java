package com.qa.api.stripe.genericTest.CustomerBalance;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateCustomerBalance {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";

	@Test
	public void createCustomerBalanceWithMandatoryFields() {

		Response response;
		JsonPath responseBody;
		int responseCode;

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, null);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		String customerId = responseBody.getString("id");
		System.out.println(customerId);
		Assert.assertEquals(responseCode, 200);

		System.out.println("---------------------------------------------------------------------------");

		String basePath = "/v1/customers/{custId}/balance_transactions";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("custId", customerId);

		Map<String, String> formData = new HashMap<String, String>();
		formData.put("amount", "1500");
		formData.put("currency", "usd");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", paramMap, true, formData);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		int amount = responseBody.getInt("amount");
		String currency = responseBody.getString("currency");
		String custtomerIdString = responseBody.getString("customer");
		System.out.println(amount);
		System.out.println(currency);
		System.out.println(custtomerIdString);

		Assert.assertEquals(responseCode, 200);
		Assert.assertEquals(custtomerIdString, customerId);
		Assert.assertEquals(amount, 1500);
		Assert.assertEquals(currency, "usd");

	}

	
	@Test
	public void createCustomerBalanceWithEmptyAmount() {

		Response response;
		JsonPath responseBody;
		int responseCode;

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, null);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		String customerId = responseBody.getString("id");
		System.out.println(customerId);
		Assert.assertEquals(responseCode, 200);

		System.out.println("---------------------------------------------------------------------------");

		String basePath = "/v1/customers/{custId}/balance_transactions";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("custId", customerId);

		Map<String, String> formData = new HashMap<String, String>();
		formData.put("amount", "");
		formData.put("currency", "usd");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", paramMap, true, formData);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		Assert.assertEquals(responseCode, 400);

	}
	
	
	
	@Test
	public void createCustomerBalanceWithEmptyCurrency() {

		Response response;
		JsonPath responseBody;
		int responseCode;

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, null);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		String customerId = responseBody.getString("id");
		System.out.println(customerId);
		Assert.assertEquals(responseCode, 200);

		System.out.println("---------------------------------------------------------------------------");

		String basePath = "/v1/customers/{custId}/balance_transactions";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("custId", customerId);

		Map<String, String> formData = new HashMap<String, String>();
		formData.put("amount", "500");
		formData.put("currency", "");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", paramMap, true, formData);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		Assert.assertEquals(responseCode, 400);

	}
	
	
	@Test
	public void createCustomerBalanceWithInvalidCurrency() {

		Response response;
		JsonPath responseBody;
		int responseCode;

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, null);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		String customerId = responseBody.getString("id");
		System.out.println(customerId);
		Assert.assertEquals(responseCode, 200);

		System.out.println("---------------------------------------------------------------------------");

		String basePath = "/v1/customers/{custId}/balance_transactions";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("custId", customerId);

		Map<String, String> formData = new HashMap<String, String>();
		formData.put("amount", "500");
		formData.put("currency", "sfsdfdsf");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", paramMap, true, formData);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		Assert.assertEquals(responseCode, 400);

	}
	
	
	
	@Test
	public void createCustomerBalanceWithStringAmount() {

		Response response;
		JsonPath responseBody;
		int responseCode;

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, null);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		String customerId = responseBody.getString("id");
		System.out.println(customerId);
		Assert.assertEquals(responseCode, 200);

		System.out.println("---------------------------------------------------------------------------");

		String basePath = "/v1/customers/{custId}/balance_transactions";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("custId", customerId);

		Map<String, String> formData = new HashMap<String, String>();
		formData.put("amount", "adder");
		formData.put("currency", "inr");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", paramMap, true, formData);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		Assert.assertEquals(responseCode, 400);

	}
	
	
	
	@Test
	public void createCustomerBalanceWithSpacesinAmountandCurrency() {

		Response response;
		JsonPath responseBody;
		int responseCode;

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, null);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		String customerId = responseBody.getString("id");
		System.out.println(customerId);
		Assert.assertEquals(responseCode, 200);

		System.out.println("---------------------------------------------------------------------------");

		String basePath = "/v1/customers/{custId}/balance_transactions";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("custId", customerId);

		Map<String, String> formData = new HashMap<String, String>();
		formData.put("amount", " ");
		formData.put("currency", " ");

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", paramMap, true, formData);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		Assert.assertEquals(responseCode, 400);

	}
	
	
	
	@Test
	public void createCustomerBalanceWith_InvalidToken() {

		Response response;
		JsonPath responseBody;
		int responseCode;

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, null);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		responseBody = response.jsonPath();
		String customerId = responseBody.getString("id");
		System.out.println(customerId);
		Assert.assertEquals(responseCode, 200);

		System.out.println("---------------------------------------------------------------------------");

		String basePath = "/v1/customers/{custId}/balance_transactions";
		Map<String, String> emptyTokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("custId", customerId);

		Map<String, String> formData = new HashMap<String, String>();
		formData.put("amount", "500");
		formData.put("currency", "inr");

		response = RestClient.doPost(baseUri, basePath, emptyTokenMap, "forms", paramMap, true, formData);

		responseCode = response.statusCode();
		System.out.println(responseCode);
		System.out.println(response.prettyPrint());

		Assert.assertEquals(responseCode, 401);

	}
}
