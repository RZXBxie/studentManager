package com.student_manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StudentManagerHomePageTest {
	@ParameterizedTest
	@CsvSource({
			"ad, false",                // too short
			"abcdefghijklmnopq, false", // too long
			"112233, false",            // digit only
			"abcde, false",            // exist
			"..xie, false",                // invalid character
			"admin123, true"            // success
	})
	public void testCheckUsername(String username, boolean expected) {
		if (!expected && !StudentManagerHomePage.uniqueUsername.contains(username)) {
			if (username.equals("abcde")) {
				StudentManagerHomePage.uniqueUsername.add(username);
			}
		}
		Assertions.assertEquals(expected, StudentManagerHomePage.checkUsername(username));
	}

	@ParameterizedTest
	@CsvSource({
			"11323, false",                // too short
			"1234567891234567891, false",  // too long
			"012340123401234012, false",   // starts with 0
			"x0123401234012342, false",    // illegal characters in the first 17 digits
			"11234001234001234a, false",   // illegal character in the last index
			"123456789123456789, true",    // success
	})
	public void testCheckId(String username, boolean expected) {
		Assertions.assertEquals(expected, StudentManagerHomePage.checkID(username));
	}

	@ParameterizedTest
	@CsvSource({
			"2323, false",   // too short
			"123412341234, false", // too long
			"02323232323, false", // starts with 0
			"x2323232323, false", // illegal
			".2323232323, false", // illegal character
			"12312341234, true", // success
	})
	public void testCheckPhoneNumber(String username, boolean expected) {
		Assertions.assertEquals(expected, StudentManagerHomePage.checkPhoneNumber(username));
	}

	@ParameterizedTest
	@CsvSource({
			"2323, false",   // too short
			"123412341234, false", // too long
			"023232323232, false", // starts with 0
			"x1223, false", // incorrect digit count
			"xx223, false", // incorrect digit count
			"xxx23, false", // incorrect digit count
			"xxxxx, false", // incorrect digit count
			"xyz.2, false", // illegal character
			"Absx1, true", // success
	})
	public void testCheckVerifyCode(String username, boolean expected) {
		Assertions.assertEquals(expected, StudentManagerHomePage.checkVerifyCode(username));
	}
}
