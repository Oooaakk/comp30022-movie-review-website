package com.example.moviehub.util;

import java.security.SecureRandom;
import java.util.Random;

public class VerificationCodeUtil {

    private static final int LENGTH = 6;

    private static final String NUMBER = "0123456789";

    private static final Random RANDOM = new SecureRandom();

    public static String generateCode() {

        char[] numbers = new char[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            numbers[i] = NUMBER.charAt(RANDOM.nextInt(NUMBER.length()));
        }
        return new String(numbers);
    }
}
