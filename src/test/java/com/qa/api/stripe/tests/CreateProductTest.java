package com.qa.api.stripe.tests;

import org.hamcrest.core.StringContains;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;


public class CreateProductTest {

	String baseUri = "https://api.stripe.com";
	String basePath = "/v1/customers/{custID}";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	JsonPath responseBody;
	
	
	@Test
	public void createProductWithValidMandatoryDetails() {
		
		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody =
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.and()
				.extract().jsonPath();
		
		String productId = responseBody.getString("id");
		System.out.println(productId);
		Assert.assertEquals(responseBody.getString("object"), "product");
		Assert.assertEquals(responseBody.getBoolean("active"), true);
		Assert.assertEquals(responseBody.getString("name"), "Testing");
	}

	
	@Test
	public void createProductWithoutName() {
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(400)
			.and()
				.body("error.message", containsString("Missing required param: name."))
				.body("error.param", equalTo("name"));
	}

	
	@Test
	public void createProductWithoutToken() {
		RestAssured.baseURI=baseUri;
		String noToken = "";
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + noToken)
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(401)
			.and()
				.body("error.message", containsString("You did not provide an API key"));
	}

	
	@Test
	public void createProductWithInvalidToken() {
		RestAssured.baseURI=baseUri;
		String invalidToken = "test";
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + invalidToken)
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(401)
			.and()
				.body("error.message", containsString("Invalid API Key provided"));
	}

	
	@Test
	public void createProductWithValidUniqueIdDetails() {
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing")
			.formParam("id", "test")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.body("id", equalTo("test"))
			.body("name", equalTo("Testing"));
	}
	
	
	
	@Test
	public void createProductWithDuplicateIdDetails() {
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing")
			.formParam("id", "test")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(400)
			.body("error.message", equalTo("Product already exists."))
			.body("error.param", equalTo("id"));
	}
	
	
	@Test
	public void createProductWithValidactiveStatus() {
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing1")
			.formParam("active", "false")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.body("active", equalTo(false))
			.body("name", equalTo("Testing1"));
	}
	
	
	
	@Test
	public void createProductWithValidUrl() {
		RestAssured.baseURI=baseUri;
		
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing1")
			.formParam("url", "https://www.google.com")
		.when().log().all()
			.post("/v1/products")
		.then().log().all()
			.assertThat()
				.statusCode(200)
			.body("url", equalTo("https://www.google.com"))
			.body("name", equalTo("Testing1"));
	}
}
