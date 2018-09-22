package com.uteq.sistemas.ventasexpress.utils;

import android.animation.Animator;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * Created by varsovski on 28-Sep-15.
 */
public class UIUtil {

    public static void SlideWindowTransition(Window window) {
        Slide slide = new Slide();
        slide.setDuration(1000);
        window.setExitTransition(slide);
    }

    public static void FadeWindowTransition(Window window) {
        Fade fade = new Fade();
        fade.setDuration(1000);
        window.setEnterTransition(fade);
    }

    ///// Animacion para los CardView a medida que se cargan o eliminan de la Vista /////
    public static void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }
    /////// Fin Animacion ///////////////

    public static boolean isNumeric(String a) {
        boolean r = false;
        try {
            Integer.valueOf(a);
            r = true;
        } catch (Exception ignored) {
        }
        return r;
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        RunnableFuture<Boolean> futureRun = new FutureTask<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if ((networkInfo .isAvailable()) && (networkInfo .isConnected())) {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(1500);
                        urlc.connect();
                        return (urlc.getResponseCode() == 200);
                    } catch (IOException e) {
                        //Log.e(TAG, "Error checking internet connection", e);
                    }
                } else {
                    //Log.d(TAG, "No network available!");
                }
                return false;
            }
        });

        new Thread(futureRun).start();


        try {
            return futureRun.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }

    }
}
