package com.qa.api.stripe.util;

public class ResponseCheck {

	public static void validationEqualsCheck(String actResult, String expResult) {

		if (actResult.equals(expResult)) {
			System.out.println("Actual and Expected Result are same");
			System.out.println("Actual Result is : " + actResult);
			System.out.println("Expected Result is : " + expResult);
		} else {
			System.err.println("Actual and Expected Result are not same");
			System.out.println("Actual Result is : " + actResult);
			System.out.println("Expected Result is : " + expResult);
		}
	}

	public static void validationContainsCheck(String actResult, String expResult) {

		if (actResult.contains(expResult)) {
			System.out.println("Actual Result contains the Expected Result");
			System.out.println("Actual Result is : " + actResult);
			System.out.println("Expected Result is : " + expResult);
		} else {
			System.err.println("Actual Result does not contain the Expected Result");
			System.out.println("Actual Result is : " + actResult);
			System.out.println("Expected Result is : " + expResult);
		}
	}

}
