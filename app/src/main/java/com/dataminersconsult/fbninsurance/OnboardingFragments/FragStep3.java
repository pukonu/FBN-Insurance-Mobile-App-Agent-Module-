package com.dataminersconsult.fbninsurance.OnboardingFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.dataminersconsult.fbninsurance.OnboardingActivity;
import com.dataminersconsult.fbninsurance.R;
import com.dataminersconsult.fbninsurance.lib.PermissionUtility;
import com.google.gson.Gson;

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

    private SharedPreferences prefs;
    private CustomerFactory customerFactory;
    private Bitmap bm = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started");
        View view = inflater.inflate(R.layout.activity_onboarding_frag3, container, false);

        OnboardingActivity activity = (OnboardingActivity) getActivity();
        customerFactory = activity.mCustomerFactory;
        prefs = getContext().getSharedPreferences("ON_BOARDING", Context.MODE_PRIVATE);

        mImageView = (ImageView) view.findViewById(R.id.oa_frag3_imageHolder);
        bm = customerFactory.getImage(prefs);
        if (bm != null) {
            mImageView.setImageBitmap(bm);
        }

        TextViewStyleA mTextViewStyleA = (TextViewStyleA) view.findViewById(R.id.oa_frag3_get_photo);
        mTextViewStyleA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        OnboardingActivity activity = (OnboardingActivity) getActivity();
        customerFactory = activity.mCustomerFactory;
        if (bm != null) {
            customerFactory.setImage(bm, prefs);
        }
        outState.putSerializable("CUSTOMER_FACTORY", customerFactory);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LayoutInflater layoutInflater = getLayoutInflater(savedInstanceState);
        if (savedInstanceState != null){
            customerFactory = (CustomerFactory) savedInstanceState.getSerializable("CUSTOMER_FACTORY");
            try {
                bm = customerFactory.getImage(prefs);
                mImageView.setImageBitmap(bm);
            } catch (NullPointerException e) {
                Log.e(TAG, "onViewStateRestored: " + e.toString(), e);
            }
        }
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
        if (requestCode == REQUEST_CAMERA) {
            onCaptureImageResult(data);
        } else if (resultCode == Activity.RESULT_OK) {
            onSelectFromGalleryResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult (Intent data) {
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

//        try {
//            Uri imageUri = getImageUri(getActivity().getApplicationContext(), bm);
//            bm = rotateImageIfRequired(imageUri);
//            Log.d(TAG, "onSelectFromGalleryResult: Image rotated");
//        } catch (IOException e) {
//            Log.e(TAG, "onSelectFromGalleryResult: " + e.toString());
//        }

//        mActivity.mCustomerFactory.setImage(bm);

        mImageView.setImageBitmap(bm);

        // TODO fix the image function
//        Glide.with(getContext())
//                .load(getImageUri(getActivity().getApplicationContext(), bm)) // Uri of the picture
//                .centerCrop()
//                .transform(new MyTransformation(getActivity().getApplicationContext(), 90))
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .into(mImageView);
    }

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
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

//        mActivity.mCustomerFactory.setImage(thumbnail);

        mImageView.setImageBitmap(bm);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap rotateImageIfRequired(Uri selectedImage) throws IOException {

        String mMediaString = selectedImage.toString();
        Log.d(TAG, "rotateImageIfRequired: " + mMediaString);
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mMediaString, bounds);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(mMediaString, opts);
        ExifInterface exif = null;
        try
        {
            exif = new ExifInterface(mMediaString);
        }
        catch (IOException e)
        {
            //Error
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;

        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
    }
}
