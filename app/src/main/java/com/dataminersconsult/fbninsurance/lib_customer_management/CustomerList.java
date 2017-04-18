package com.dataminersconsult.fbninsurance.lib_customer_management;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class CustomerList {
    private String title;
    private String email;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String occupation;
    private String image;
    private Bitmap bm;

    public CustomerList(String email, String title, String first_name, String last_name, String middle_name, String occupation, String image) {
        this.title = title;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.occupation = occupation;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getBm() {
        if (image != null) {
            byte[] imageAsBytes = Base64.decode(image.getBytes(), 0);
            return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        }
        return null;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public String getFullname() {
        return String.format("%s %s %s", title, first_name, last_name);
    }
}
