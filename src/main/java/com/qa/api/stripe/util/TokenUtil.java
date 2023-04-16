package com.qa.api.stripe.util;

import java.util.Map;

public class TokenUtil {

	public static Map<String, String> tokenMap;
	public static String authToken = "sk_test_51MoqHOSFLfxbYOM93dhNX7xC35aNeNAxHdkHl47hTsuUFfwrwjeZKLfSguTUDkaZfx6JgGf2IXM45CjssC3bpLkr00R4Oju1c7";
	
	public static Map<String, String> getAuthToken() {
		
		tokenMap.put("Authorization", "Bearer "+authToken);
		return tokenMap;
	}
	
}
