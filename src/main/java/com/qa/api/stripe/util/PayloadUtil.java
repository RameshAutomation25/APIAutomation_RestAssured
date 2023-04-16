package com.qa.api.stripe.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PayloadUtil {

	public static String getSerializedJson(Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonPayload = null;
		try {
			jsonPayload = mapper.writeValueAsString(obj);
			System.out.println("JSON String ===> "+jsonPayload);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonPayload;
	}
	
}
