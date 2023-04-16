package com.qa.api.stripe.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class GetAProductTest {

	String baseUri = "https://api.stripe.com";
	String token = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	
	@Test
	public void getProductWithValidId() {
		
		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody =
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing10")
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
		Assert.assertEquals(responseBody.getString("name"), "Testing10");
		
		System.out.println("------------------------------------------------------------------");
		
		RestAssured.baseURI = baseUri;
		
		RestAssured.given()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("prodId", productId)
		.when()
			.get("/v1/products/{prodId}")
		.then()
			.assertThat()
				.statusCode(200)
			.and()
				.body("id", equalTo(productId))
				.body("object", equalTo("product"))
				.body("name", equalTo("Testing10"));
	}
	
	
	@Test
	public void getProductWithInvalidId() {
		RestAssured.baseURI = baseUri;
		
		RestAssured.given()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("prodId", "testing")
		.when()
			.get("/v1/products/{prodId}")
		.then()
			.assertThat()
				.statusCode(404)
			.and()
				.body("error.message", containsString("No such product"))
				.body("error.param", equalTo("id"));
	}
	
	
	@Test
	public void getProductWithNoToken() {
		
		String noToken = "";
		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody =
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing10")
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
		Assert.assertEquals(responseBody.getString("name"), "Testing10");
		
		System.out.println("------------------------------------------------------------------");
		
		RestAssured.baseURI = baseUri;
		
		RestAssured.given()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + noToken)
			.pathParam("prodId", productId)
		.when()
			.get("/v1/products/{prodId}")
		.then()
			.assertThat()
				.statusCode(401)
			.and()
				.body("error.message", containsString("You did not provide an API key"));
	}
	
	
	@Test
	public void getProductWithInvalidToken() {
		String invalidToken = "test";
		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody =
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "Testing10")
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
		Assert.assertEquals(responseBody.getString("name"), "Testing10");
		
		System.out.println("------------------------------------------------------------------");
		
		RestAssured.baseURI = baseUri;
		
		RestAssured.given()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + invalidToken)
			.pathParam("prodId", productId)
		.when()
			.get("/v1/products/{prodId}")
		.then()
			.assertThat()
				.statusCode(401)
			.and()
				.body("error.message", containsString("Invalid API Key provided"));
	}
	
	
	@Test
	public void getProductWithDeletedProductId() {
		RestAssured.baseURI=baseUri;
		
		JsonPath responseBody =
		RestAssured.given().log().all()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.formParam("name", "test")
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
		Assert.assertEquals(responseBody.getString("name"), "test");
		
		
		System.out.println("------------------------------------------------------------------");

		RestAssured.given()
		.contentType(ContentType.URLENC)
		.header("Authorization", "Bearer " + token)
		.pathParam("prodId", productId)
	.when()
		.delete("/v1/products/{prodId}")
	.then()
		.assertThat()
			.statusCode(200)
		.and()
			.body("id", equalTo(productId))
			.body("object", equalTo("product"))
			.body("deleted", equalTo(true));		
		
		
		System.out.println("------------------------------------------------------------------");
		
		RestAssured.given()
			.contentType(ContentType.URLENC)
			.header("Authorization", "Bearer " + token)
			.pathParam("prodId", productId)
		.when()
			.get("/v1/products/{prodId}")
		.then()
			.assertThat()
			.statusCode(404)
			.and()
				.body("error.message", containsString("No such product"))
				.body("error.param", equalTo("id"));
	}
}
