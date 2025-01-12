package com.mindera.mindswap;

/**
 * Utility class for generating random numbers within a specified range.
 */
public class RandomGenerator {
    /**
     * Generates a random integer between min and max (inclusive)
     * @param min Minimum value
     * @param max Maximum value
     * @return Random integer between min and max
     */
    public static int generate(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
