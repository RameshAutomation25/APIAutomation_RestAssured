package com.qa.api.stripe.tests;

import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.containsString;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class UpdateProductTest {

	String baseUri = "https://api.stripe.com";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";

	@Test
	public void updateWithAllOptionalDetailsForValidProductId() {
		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "productTest20")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.statusCode(200)
				.extract().jsonPath();
		
		System.out.println(responseBody.prettyPrint());
		String productId = responseBody.getString("id");
		
		Assert.assertEquals(responseBody.getString("object"), "product");
		Assert.assertEquals(responseBody.getString("name"), "productTest20");
				
				
		System.out.println("-------------------------------------------------------------");
		
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("prodId", productId)
			.formParam("metadata[order_id]", "1000")
			.formParam("active", "false")
			.formParam("description", "testing10")
			.formParam("name", "productTest10")
		.when().log().all()
			.post("/v1/products/{prodId}")
		.then().log().all()
			.assertThat()	
				.statusCode(200)
			.and()
				.body("id", equalTo(productId))
				.body("object", equalTo("product"))
				.body("active", equalTo(false))
				.body("description", equalTo("testing10"))
				.body("metadata.order_id", equalTo("1000"))
				.body("name", equalTo("productTest10"));
		
		System.out.println("-------------------------------------------------------------");
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("prodId", productId)
		.when().log().all()
			.get("/v1/products/{prodId}")
		.then().log().all()
			.statusCode(200)
			.body("id", equalTo(productId))
			.body("object", equalTo("product"))
			.body("active", equalTo(false))
			.body("description", equalTo("testing10"))
			.body("metadata.order_id", equalTo("1000"))
			.body("name", equalTo("productTest10"));
		
		
		
	}

	
	@Test
	public void updateWithNoToken() {

		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "productTest30")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.statusCode(200)
				.extract().jsonPath();
		
		System.out.println(responseBody.prettyPrint());
		String productId = responseBody.getString("id");
		
		Assert.assertEquals(responseBody.getString("object"), "product");
		Assert.assertEquals(responseBody.getString("name"), "productTest30");
				
				
		System.out.println("-------------------------------------------------------------");
		
		String noToken = "";
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + noToken)
			.pathParam("prodId", productId)
			.formParam("metadata[order_id]", "1000")
			.formParam("active", "false")
			.formParam("description", "testing10")
			.formParam("name", "productTest10")
		.when().log().all()
			.post("/v1/products/{prodId}")
		.then().log().all()
			.assertThat()	
				.statusCode(401)
			.and()
				.body(containsString("You did not provide an API key"));
				
	}

	
	
	@Test
	public void updateWithInvalidToken() {

		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody = 
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "productTest30")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.statusCode(200)
				.extract().jsonPath();
		
		System.out.println(responseBody.prettyPrint());
		String productId = responseBody.getString("id");
		
		Assert.assertEquals(responseBody.getString("object"), "product");
		Assert.assertEquals(responseBody.getString("name"), "productTest30");
				
				
		System.out.println("-------------------------------------------------------------");
		
		String invalidToken = "test";
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + invalidToken)
			.pathParam("prodId", productId)
			.formParam("metadata[order_id]", "1000")
			.formParam("active", "false")
			.formParam("description", "testing10")
			.formParam("name", "productTest10")
		.when().log().all()
			.post("/v1/products/{prodId}")
		.then().log().all()
			.assertThat()	
				.statusCode(401)
			.and()
				.body(containsString("Invalid API Key provided"));
	}

	
	
	
	@Test
	public void updateWithInvalidProductId() {
		
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
		.contentType(ContentType.URLENC)
		.header("Authorization", "Bearer " + token)
		.pathParam("prodId", "testtest")
		.formParam("metadata[order_id]", "1000")
		.formParam("active", "false")
		.formParam("description", "testing10")
		.formParam("name", "productTest10")
	.when().log().all()
		.post("/v1/products/{prodId}")
	.then().log().all()
		.assertThat()	
			.statusCode(404)
		.and()
			.body(containsString("No such product"));
	}
}
