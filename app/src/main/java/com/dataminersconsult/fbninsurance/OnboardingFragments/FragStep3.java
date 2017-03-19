package com.dataminersconsult.fbninsurance.OnboardingFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dataminersconsult.customfonts.TextViewStyleA;
import com.dataminersconsult.fbninsurance.R;
import com.dataminersconsult.fbninsurance.lib.PermissionUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FragStep3 extends Fragment {

    private static final String TAG = "FragStep2";

    public static final String DIALOG_CAMERA = "Take Photo";
    public static final String DIALOG_GALLERY = "Choose from Library";
    public static final String DIALOG_CANCEL = "Cancel";

    public static final int REQUEST_CAMERA = 0;
    public static final int SELECT_FILE = 1;

    private String userChosenTask;
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        View view = inflater.inflate(R.layout.activity_onboarding_frag3, container, false);

        mImageView = (ImageView) view.findViewById(R.id.oa_frag3_imageHolder);
        TextViewStyleA mTextViewStyleA = (TextViewStyleA) view.findViewById(R.id.oa_frag3_get_photo);

        mTextViewStyleA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        return view;
    }

    private void selectImage () {
        final CharSequence[] items = { DIALOG_CAMERA, DIALOG_GALLERY, DIALOG_CANCEL };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean result = PermissionUtility.checkPermission(getActivity());
                switch (i) {
                    case 0:
                        if (result) {
                            cameraIntent();
                            userChosenTask = DIALOG_CAMERA;
                        }
                        break;

                    case 1:
                        if (result) {
                            galleryIntent();
                            userChosenTask = DIALOG_GALLERY;
                        }
                        break;

                    case 2:
                        dialogInterface.dismiss();
                        break;

                    default:
                        throw new IllegalArgumentException("Some funny dialog item was selected");
//                        dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtility.MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChosenTask.equals(DIALOG_GALLERY)) {
                        cameraIntent();
                    } else {
                        galleryIntent();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Required permissions missing", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                throw new IllegalArgumentException("Couldn't access required permissions");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            onSelectFromGalleryResult(data);
        } else if (requestCode == REQUEST_CAMERA) {
            onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult (Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                Log.d(TAG, "onSelectFromGalleryResult: error occurred while retrieving the image data");
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        mImageView.setImageBitmap(bm);

        // TODO fix Glide class for image to show in proper orientation
//        Glide.with(getContext())
//                .load(getImageUri(getActivity().getApplicationContext(), bm)) // Uri of the picture
//                .centerCrop()
//                .transform(new MyTransformation(getActivity().getApplicationContext(), 90))
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .into(mImageView);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "onCaptureImageResult: File was not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "onCaptureImageResult: An IO exception occured");
            e.printStackTrace();
        }

        mImageView.setImageBitmap(thumbnail);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
