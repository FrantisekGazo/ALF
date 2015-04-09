package com.f3rog.alf.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Random;

import javax.inject.Inject;


/**
 * Class {@link com.f3rog.alf.utils.Utils}
 *
 * @author f3rog
 * @version 2014-12-01
 */
public class Utils {

    @Inject
    static Application _app;

    /**
     * Sets visibility to all given views.
     *
     * @param visibility use {@link android.view.View#GONE}, {@link android.view.View#VISIBLE} or {@link android.view.View#INVISIBLE}
     * @param views      Views
     */
    public static void setVisibility(int visibility, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            if (view != null) {
                view.setVisibility(visibility);
            }
        }
    }

    /**
     * Sets visibility to all given views.
     *
     * @param condition Visibility condition.
     * @param ifTrue    Visibility state if condition was met. Use {@link android.view.View#GONE}, {@link android.view.View#VISIBLE} or {@link android.view.View#INVISIBLE}.
     * @param ifFalse   Visibility state if condition was not met. Use {@link android.view.View#GONE}, {@link android.view.View#VISIBLE} or {@link android.view.View#INVISIBLE}.
     * @param views     Views
     */
    public static void setVisibility(boolean condition, int ifTrue, int ifFalse, View... views) {
        setVisibility(condition ? ifTrue : ifFalse, views);
    }

    public static int getColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    /**
     * Converts given PX value to DP.
     */
    public static int pxToDp(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, _app.getResources().getDisplayMetrics());
    }

    /**
     * Converts given DP value to PX.
     */
    public static int dpToPx(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value, _app.getResources().getDisplayMetrics());
    }

    public static void showMessage(String msg) {
        Toast.makeText(_app, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(int msgRes) {
        Toast.makeText(_app, msgRes, Toast.LENGTH_SHORT).show();
    }

    public static void showMessageLong(String msg) {
        Toast.makeText(_app, msg, Toast.LENGTH_LONG).show();
    }

    public static void showMessageLong(int msgRes) {
        Toast.makeText(_app, msgRes, Toast.LENGTH_LONG).show();
    }

    /**
     * Hides keyboard
     *
     * @param context Current activity
     */
    public static void hideKeyboard(Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) _app.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
        }
    }

    /**
     * Shows keyboard
     */
    public static void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) _app.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Discovers whether internet connection is available.
     *
     * @return <code>true</code> on success
     */
    public static boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) _app.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    public static float getScreenWidth(Activity context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static float getScreenHeight(Activity context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static int idForObject(Object o) {
        return System.identityHashCode(o);
    }

}