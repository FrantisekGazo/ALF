package com.f3rog.alf.utils;

import android.util.Log;

/**
 * Class {@link Timer}
 *
 * @author f3rog
 */
public class Timer {

    private static long _t;

    public static void start() {
        _t = System.currentTimeMillis();
        Log.d("Timer", "START: " + _t);
    }

    public static void stop() {
        System.currentTimeMillis();
        _t = System.currentTimeMillis() - _t;
        Log.d("Timer", "STOP: " + System.currentTimeMillis() + " ---> " + _t + "ms");
    }

    public static void interstop() {
        long temp = System.currentTimeMillis();
        Log.d("Timer", "INTERSTOP: " + System.currentTimeMillis() + " ---> " + (_t - temp) + "ms");
        _t = temp;
    }

}
