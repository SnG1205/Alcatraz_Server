package com.example.alcatraz_server.utils;

import java.util.Random;

public class RandomNum {
    private static Random rand = new Random();

    public static int randInt() {
        return rand.nextInt((100)) + 1;
    }
}
