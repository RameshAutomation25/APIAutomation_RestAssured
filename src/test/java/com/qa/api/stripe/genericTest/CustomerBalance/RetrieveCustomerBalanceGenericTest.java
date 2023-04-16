package com.qa.api.stripe.genericTest.CustomerBalance;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.response.Response;

public class RetrieveCustomerBalanceGenericTest {

	String baseUri = "https://api.stripe.com";
	String basePath;
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	Map<String, String> tokenMap = new HashMap<String, String>();
	Map<String, String> params = new HashMap<String, String>();
	Map<String, String> formPayload = new HashMap<String, String>();
	Response response;

	@Test
	public void retrieveCustomerBalancewith_ValidDetails() {

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

		basePath = "/v1/customers/{custId}/balance_transactions/{transId}";

		params.put("custId", customerId);
		params.put("transId", transactionId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("id"), transactionId);
		Assert.assertEquals(response.jsonPath().getString("customer"), customerId);
		Assert.assertEquals(response.jsonPath().getString("amount"), "5000");
		Assert.assertEquals(response.jsonPath().getString("currency"), "inr");
	}

	@Test
	public void retrieveCustomerBalancewith_InvalidCustomerId() {

		basePath = "/v1/customers/{custId}/balance_transactions/{transId}";

		String invalidCustId = "sdfsdf";
		String transactionId = "cbtxn_1MtAMsSFLfxbYOM9fhIxkrPd";
		
		tokenMap.put("Authorization", "Bearer " + token);
		params.put("custId", invalidCustId);
		params.put("transId", transactionId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 404);
	}

	@Test
	public void retrieveCustomerBalancewith_InvalidTransactionId() {

		basePath = "/v1/customers/{custId}/balance_transactions/{transId}";

		String customerID = "cus_Na0OuG3s3gjvU0";
		String invalidTransactionId = "cbtxn_1MtAMsSFLfxbYOM9fhIxkrPddsf";
		
		tokenMap.put("Authorization", "Bearer " + token);
		params.put("custId", customerID);
		params.put("transId", invalidTransactionId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 404);
	}

	@Test
	public void retrieveCustomerBalancewith_InvalidToken() {

		basePath = "/v1/customers/{custId}/balance_transactions/{transId}";

		String noToken = "";
		String customerID = "cus_Na0OuG3s3gjvU0";
		String transactionId = "cbtxn_1MtAMsSFLfxbYOM9fhIxkrPd";
		
		tokenMap.put("Authorization", "Bearer " + noToken);
		params.put("custId", customerID);
		params.put("transId", transactionId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 401);
	}

	public void retrieveCustomerBalanceWithNoTransactions() {

		tokenMap.put("Authorization", "Bearer " + token);
		basePath = "/v1/customers";

		response = RestClient.doPost(baseUri, basePath, tokenMap, "forms", params, true, null);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());

		Assert.assertEquals(response.statusCode(), 200);
		String customerId = response.jsonPath().getString("id");
		System.out.println(customerId);

		System.out.println("-----------------------------------------------------------------------");

		basePath = "/v1/customers/{custId}/balance_transactions/{transId}";

		params.put("custId", customerId);
//		params.put("transId", transactionId);

		response = RestClient.doGet(baseUri, basePath, tokenMap, "forms", params, true);
		System.out.println(response.statusCode());
		System.out.println(response.prettyPrint());
		Assert.assertEquals(response.statusCode(), 200);
	}

}
