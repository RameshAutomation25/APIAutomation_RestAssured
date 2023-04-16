package com.qa.api.stripe.restclient;

import java.io.File;
import java.util.Map;

import com.qa.api.stripe.util.PayloadUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

	/**
	 * 
	 * @param baseURI
	 * @param basePath
	 * @param token
	 * @param contentType
	 * @param params
	 * @param log
	 * @return
	 */
	public static Response doGet(String baseURI, String basePath, Map<String, String> token, String contentType,
			Map<String, String> params,	boolean log) {
		
		if(setBaseUri(baseURI)) {
			RequestSpecification request = createRequest(token, contentType, params, log);
			return getResponse("GET", request, basePath);
		}
		return null;
	}

	
	public static Response doPost(String baseURI, String basePath, Map<String, String> token, String contentType,
			Map<String, String> params,	boolean log, Object obj) {
		
		
		if(setBaseUri(baseURI)) {
			RequestSpecification request = createRequest(token, contentType, params, log);
			addRequestPayload(request, obj);
			Response response = getResponse("POST", request, basePath);
			return response;
		}
		return null;
	}
	
	
	public static void addRequestPayload(RequestSpecification request, Object obj) {
		
		if(obj instanceof Map) {
			request.formParams((Map<String, String>)obj);
		}
		else {
			String jsonPayload = PayloadUtil.getSerializedJson(obj);
			request.body(jsonPayload);
		}
	}
	
	
	public static Response doUpdate(String baseURI, String basePath, Map<String, String> token, String contentType,
			Map<String, String> params,	boolean log, Object obj) {
		
		if(setBaseUri(baseURI)) {
			RequestSpecification request = createRequest(token, contentType, params, log);
			addRequestPayload(request, obj);
			Response response = getResponse("POST", request, basePath);
			return response;
		}
		return null;
	}
	
	
	public static Response doDelete(String baseURI, String basePath, Map<String, String> token, String contentType,
			Map<String, String> params,	boolean log) {
		
		if(setBaseUri(baseURI)) {
			RequestSpecification request = createRequest(token, contentType, params, log);
			return getResponse("DELETE", request, basePath);
		}
		return null;
	}
	
	/**
	 * 
	 * @param baseURI
	 * @return
	 */
	public static boolean setBaseUri(String baseURI) {

		if (baseURI == null) {
			System.out.println("Base URI cannot be null");
		} else if (baseURI.isEmpty()) {
			System.out.println("Base URI cannot be blank");
		} else {
			RestAssured.baseURI = baseURI;
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param token
	 * @param contentType
	 * @param params
	 * @param log
	 * @return
	 */
	public static RequestSpecification createRequest(Map<String, String> token, String contentType, Map<String, String> params, boolean log) {
		
		RequestSpecification request;
		if(log) {
			request = RestAssured.given().log().all();
		}
		else {
			request = RestAssured.given();
		}
		
		if(token.size()>0) {
			request.headers(token);
		}
		
		if(!(params==null)) {
			request.pathParams(params);
		}
		
		if(contentType!=null) {
			if(contentType.equalsIgnoreCase("JSON")) {
				request.contentType(ContentType.JSON);
			}
			else if(contentType.equalsIgnoreCase("XML")) {
				request.contentType(ContentType.XML);
			}
			else if(contentType.equalsIgnoreCase("TEXT")) {
				request.contentType(ContentType.TEXT);
			}
			else if(contentType.equalsIgnoreCase("multipart")) {
				request.multiPart(new File("C:\\Users\\DELL LATITUDE 7480\\Downloads\\nature"));
			}
			else if(contentType.equalsIgnoreCase("forms")) {
				request.contentType(ContentType.URLENC);
			}
		}
		
		return request;
	}
	
	
	/**
	 * 
	 * @param httpMethod
	 * @param request
	 * @param basePath
	 * @return
	 */
	public static Response getResponse(String httpMethod, RequestSpecification request, String basePath) {
		
		return executeAPI(httpMethod, request, basePath);
	}
	
	
	/**
	 * 
	 * @param httpMethod
	 * @param request
	 * @param basePath
	 * @return
	 */
	public static Response executeAPI(String httpMethod, RequestSpecification request, String basePath) {
		
		Response response=null;
		
		switch (httpMethod) {
		case "GET":
			response = request.get(basePath);
			break;
		case "POST":
			response = request.post(basePath);
			break;
		case "PUT":
			response = request.put(basePath);
			break;
		case "DELETE":
			response = request.delete(basePath);
			break;

		default:
			System.out.println("Please enter the correct http method");
			break;
		}
		return response;
	}
	
	
}
