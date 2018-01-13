package com.scholarum.common.util;

import java.util.Random;

public class RandomIdGenerator {

	public static String generateAccessKey(char[] charset) {
		Random random = new Random();
		String randomCode = "";
		for (int i = 0; i < 12; i++) {
			int idx = random.nextInt(charset.length);
			randomCode += charset[idx];
		}
		return randomCode;
	}
}
