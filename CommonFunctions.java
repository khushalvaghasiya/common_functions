package com.appmakers.stylishtext.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AlertDialog;
import com.appmakers.ffnicknamegenerator.R;
import com.google.android.gms.ads.RequestConfiguration;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class CommonFunctions {
    public static String DATE_FORMANT = "dd MMMM yyyy";
    public static String DATE_TIME_FORMANT = "E, dd MMM yyyy HH:mm:ss z";
    static String TAG = "CommonFunctions";

    public static void changeActivity(Activity activity, Class cls, boolean z, boolean z2) {
        activity.startActivity(new Intent(activity, cls));
        if (z) {
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else {
            activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
    }

    public static void finishActivity(Context context) {
        ((Activity) context).finish();
    }

    public static String getCurrentDateTime() {
        try {
            return new SimpleDateFormat().format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long getCurrentTimeStamp() {
        return Long.valueOf(new Date().getTime());
    }

    public static String getDate(String str, long j) {
        Calendar instance = Calendar.getInstance(Locale.ENGLISH);
        instance.setTimeInMillis(j);
        return new SimpleDateFormat(str).format(instance.getTime());
    }

    public static long getTimeStamp(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        Calendar instance = Calendar.getInstance(Locale.ENGLISH);
        try {
            instance.setTime(simpleDateFormat.parse(str2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return instance.getTimeInMillis();
    }

    public static void hideKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setExitDialog(final Activity activity) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle((CharSequence) activity.getResources().getString(R.string.app_name));
            builder.setMessage((CharSequence) "Are you sure you want to exit?");
            builder.setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent("android.intent.action.MAIN");
                    intent.addCategory("android.intent.category.HOME");
                    intent.setFlags(268435456);
                    activity.startActivity(intent);
                }
            });
            builder.setNegativeButton((CharSequence) "NO", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception unused) {
        }
    }

    public static String getDeviceId(Context context) {
        return Settings.System.getString(context.getContentResolver(), "android_id");
    }

    public static String getString(Context context, int i) {
        return context.getResources().getString(i);
    }

    public static String getRandomString(int i) {
        byte[] bArr = new byte[256];
        new Random().nextBytes(bArr);
        String str = new String(bArr, Charset.forName("UTF-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String replaceAll = str.replaceAll("[^A-Za-z0-9]", RequestConfiguration.MAX_AD_CONTENT_RATING_UNSPECIFIED);
        for (int i2 = 0; i2 < replaceAll.length(); i2++) {
            if ((Character.isLetter(replaceAll.charAt(i2)) && i > 0) || (Character.isDigit(replaceAll.charAt(i2)) && i > 0)) {
                stringBuffer.append(replaceAll.charAt(i2));
                i--;
            }
        }
        return stringBuffer.toString();
    }

    public static void shareText(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", context.getResources().getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", str);
        context.startActivity(Intent.createChooser(intent, "Share"));
    }
}
