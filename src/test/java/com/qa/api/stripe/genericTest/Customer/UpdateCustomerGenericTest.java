package com.qa.api.stripe.genericTest.Customer;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.stripe.restclient.RestClient;

import io.restassured.response.Response;

public class UpdateCustomerGenericTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";

	@Test
	public void updateCustomerWithValidID() {

		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Authorization", "Bearer " + token);

		Map<String, String> formBody = new HashMap<String, String>();
		formBody.put("email", "kev@gmail.com");
		formBody.put("name", "kev");
		formBody.put("phone", "+10000000000");

		System.out.println("------------------------------------------------------------------");

		Response responsePost = RestClient.doPost(baseUri, basePath, tokenMap, "forms", null, true, formBody);

		System.out.println(responsePost.statusCode());
		System.out.println(responsePost.prettyPrint());

		Assert.assertEquals(responsePost.statusCode(), 200);
		Assert.assertEquals(responsePost.body().jsonPath().getString("email"), "kev@gmail.com");
		Assert.assertEquals(responsePost.body().jsonPath().getString("name"), "kev");
		Assert.assertEquals(responsePost.body().jsonPath().getString("phone"), "+10000000000");
		String custId = responsePost.jsonPath().getString("id");

		System.out.println("------------------------------------------------------------------");

		Response responseGet = RestClient.doGet(baseUri, basePath +"/"+ custId, tokenMap, "JSON", null, true);

		System.out.println(responseGet.statusCode());
		System.out.println(responseGet.prettyPrint());

		Assert.assertEquals(responseGet.statusCode(), 200);
		Assert.assertEquals(responsePost.body().jsonPath().getString("email"), "kev@gmail.com");
		Assert.assertEquals(responsePost.body().jsonPath().getString("name"), "kev");
		Assert.assertEquals(responsePost.body().jsonPath().getString("phone"), "+10000000000");

		System.out.println("------------------------------------------------------------------");

		Map<String, String> formBodyUpdate = new HashMap<String, String>();
		formBodyUpdate.put("email", "jameskev@gmail.com");
		formBodyUpdate.put("name", "jameskev");
		formBodyUpdate.put("phone", "+20000000000");

		Response responseUpdate = RestClient.doUpdate(baseUri, basePath +"/"+ custId, tokenMap, "forms", null, true,
				formBodyUpdate);

		System.out.println(responseUpdate.statusCode());
		System.out.println(responseUpdate.prettyPrint());

		Assert.assertEquals(responseUpdate.statusCode(), 200);
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("email"), "jameskev@gmail.com");
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("name"), "jameskev");
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("phone"), "+20000000000");

		System.out.println("------------------------------------------------------------------");

		Response responseGetUpdated = RestClient.doGet(baseUri, basePath +"/"+ custId, tokenMap, "JSON", null, true);

		System.out.println(responseGetUpdated.statusCode());
		System.out.println(responseGetUpdated.prettyPrint());

		Assert.assertEquals(responseGetUpdated.statusCode(), 200);
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("email"), "jameskev@gmail.com");
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("name"), "jameskev");
		Assert.assertEquals(responseUpdate.body().jsonPath().getString("phone"), "+20000000000");
	}
}
