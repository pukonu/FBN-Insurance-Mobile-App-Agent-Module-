package com.dataminersconsult.fbninsurance.lib_onboarding;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.dataminersconsult.fbninsurance.lib.UserContract;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;


public class CustomerFactory implements Serializable {
    private Bitmap bm;
    public static final long serialVersionUID = 20170331L;

    static final int PUT = 1;
    static final int GET = 0;
    static final int UPDATE = 2;

    private static final String TAG = "CustomerFactory";

    public Bitmap getImage(SharedPreferences prefs) {
        String bmString = prefs.getString("image", "");
        if (!bmString.isEmpty()) {
            byte[] imageAsBytes = Base64.decode(bmString.getBytes(), 0);
            return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        } else {
            return null;
        }
    }

    public void setImage(Bitmap bm, SharedPreferences prefs) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encoded = Base64.encodeToString(b, Base64.DEFAULT);
        prefs.edit().putString("image", encoded).apply();
    }

    /**
     * Retrieves or inputs values into the class properties
     * @param string
     * @param stringSlug
     * @param method
     */
    String generic(String string, String stringSlug, int method, SharedPreferences prefs) {
        if (method == PUT){
            prefs.edit().putString(stringSlug, string).apply();
            return null;
        }
        else return prefs.getString(stringSlug, "");
    }

    public void saveCustomerInformation(Activity activity, SharedPreferences prefs) {
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues contentValues = new ContentValues();

        for (FormFactory.FIELDS field : FormFactory.FIELDS.values()) {
            contentValues.put(field.toString(), generic(null, field.toString(), GET, prefs));
        }
        contentResolver.insert(UserContract.CONTENT_URI, contentValues);
        prefs.edit().clear().apply();
    }
}
