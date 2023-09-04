package com.syg.webfluxdemo.service;

public class SleepUtil {

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
           System.out.println("Error in sleep method: " + e);
        }
    }
}
