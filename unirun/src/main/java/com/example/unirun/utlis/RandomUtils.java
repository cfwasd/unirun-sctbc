package com.example.unirun.utlis;

import java.util.Random;

/**
 * @author wzh
 * 随机数
 */
public class RandomUtils {
    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static double randAccuracy() {
        Random random = new Random();
        return random.nextDouble() * 100 + 50;
    }
}
