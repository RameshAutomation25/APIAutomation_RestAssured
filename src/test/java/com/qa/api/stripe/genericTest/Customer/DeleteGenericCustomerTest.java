package com.qa.api.stripe.genericTest.Customer;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.response.Response;

public class DeleteGenericCustomerTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";

	@Test
	public void deleteCustomerWithValidID() {

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		Map<String, String> formBody = new HashMap<String, String>();
		formBody.put("email", "mas1@gmail.com");
		formBody.put("name", "mas1");
		formBody.put("phone", "+11100000000");

		System.out.println("------------------------------------------------------------------");

		Response responsePost = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);

		System.out.println(responsePost.statusCode());
		System.out.println(responsePost.prettyPrint());

		Assert.assertEquals(responsePost.statusCode(), 200);
		Assert.assertEquals(responsePost.body().jsonPath().getString("email"), "mas1@gmail.com");
		Assert.assertEquals(responsePost.body().jsonPath().getString("name"), "mas1");
		Assert.assertEquals(responsePost.body().jsonPath().getString("phone"), "+11100000000");
		String custId = responsePost.jsonPath().getString("id");

		System.out.println("------------------------------------------------------------------");

		Response responseGet = RestClient.doGet(baseUri, basePath +"/"+ custId, tokenMap, "JSON", null, true);

		System.out.println(responseGet.statusCode());
		System.out.println(responseGet.prettyPrint());

		Assert.assertEquals(responseGet.statusCode(), 200);
		Assert.assertEquals(responsePost.body().jsonPath().getString("email"), "mas1@gmail.com");
		Assert.assertEquals(responsePost.body().jsonPath().getString("name"), "mas1");
		Assert.assertEquals(responsePost.body().jsonPath().getString("phone"), "+11100000000");

		System.out.println("------------------------------------------------------------------");

		Response responseUpdate = RestClient.doDelete(baseUri, basePath +"/"+ custId, tokenMap, "JSON", null, true);

		System.out.println(responseUpdate.statusCode());
		System.out.println(responseUpdate.prettyPrint());

		Assert.assertEquals(responseUpdate.statusCode(), 200);
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("id"), custId);
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("deleted"), "true");

		System.out.println("------------------------------------------------------------------");

		Response responseGetUpdated = RestClient.doGet(baseUri, basePath +"/"+ custId, tokenMap, "JSON", null, true);

		System.out.println(responseGetUpdated.statusCode());
		System.out.println(responseGetUpdated.prettyPrint());

		Assert.assertEquals(responseGetUpdated.statusCode(), 200);
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("id"), custId);
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("deleted"), "true");
	}
}
