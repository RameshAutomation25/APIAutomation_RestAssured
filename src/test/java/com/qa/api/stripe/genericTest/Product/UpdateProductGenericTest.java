package com.qa.api.stripe.genericTest.Product;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateProductGenericTest {

	String baseUri = "https://api.stripe.com";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	Response response;
	Map<String, String> tokenMap = new HashMap<String, String>();
	Map<String, String> formBody = new HashMap<String, String>();
	Map<String, String> paramsMap = new HashMap<String, String>();
	String basePath;
	String responseBody;

	public void updateWithAllOptionalDetailsForValidProductId() {
		RestAssured.baseURI=baseUri;
		
		basePath = "v1/products/{prodId}";
		
		paramsMap.put("prodId", "prod_NgNrp3IhWYUGRG");
		
		tokenMap.put("Authorization", "Bearer " + token);
		
		formBody.put("metadata[order_id]", "1000");
		formBody.put("active", "true");
		formBody.put("description", "testing1");
		formBody.put("name", "productTest1");
		
//		RestAssured.given()
//			.contentType(ContentType.URLENC)
			
		
		
		
	}

	public void updateWithNoToken() {
		
	}

	public void updateWithInvalidToken() {
		
	}

	public void updateWithInvalidProductId() {
		
	}

}
