package com.dataminersconsult.fbninsurance.lib;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.dataminersconsult.fbninsurance.R;

public class PermissionUtility {
    public static final int MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Permission Necessary");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    (Activity) context,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    ActivityCompat.requestPermissions(
                            (Activity) context,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
