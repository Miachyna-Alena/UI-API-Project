package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {
    public static String generateRandoString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
