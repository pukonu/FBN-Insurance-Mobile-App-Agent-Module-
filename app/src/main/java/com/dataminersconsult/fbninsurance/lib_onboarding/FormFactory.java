package com.dataminersconsult.fbninsurance.lib_onboarding;

class FormFactory {
    private static final int ARRAY_SIZE = 4;
    public static final int FORM_TYPE_SINGLE = 1;
    public static final int FORM_TYPE_SELECT = 2;

    enum FIELDS {
        first_name, last_name, middle_name, title, occupation, image
    }

    enum SELECT_TITLE {
        Chief, Dr, Honourable, Mr, Mrs, Miss, Pastor, Rev, Senator
    }

    static String[] getFieldProperties (String fieldName) {
        String[] s;
        switch (fieldName.toLowerCase()) {
            case "first_name":
                s = new String[ARRAY_SIZE];
                s[0] = "1";
                s[1] = "First Name";
                s[2] = "Enter customer first name";
                s[3] = String.format("%s", FORM_TYPE_SINGLE);
                return s;
            case "last_name":
                s = new String[ARRAY_SIZE];
                s[0] = "2";
                s[1] = "Last Name";
                s[2] = "Enter customer last name";
                s[3] = String.format("%s", FORM_TYPE_SINGLE);
                return s;
            case "middle_name":
                s = new String[ARRAY_SIZE];
                s[0] = "3";
                s[1] = "Middle Name";
                s[2] = "Enter customer middle name";
                s[3] = String.format("%s", FORM_TYPE_SINGLE);
                return s;
            case "title":
                s = new String[ARRAY_SIZE];
                s[0] = "4";
                s[1] = "Title";
                s[2] = "Enter title";
                s[3] = String.format("%s", FORM_TYPE_SELECT);
                return s;
            case "occupation":
                s = new String[ARRAY_SIZE];
                s[0] = "5";
                s[1] = "Occupation";
                s[2] = "Enter customer occupation";
                s[3] = String.format("%s", FORM_TYPE_SINGLE);
                return s;
            default:
                s = new String[ARRAY_SIZE];
                s[0] = "0";
                s[1] = "";
                s[2] = "";
                s[3] = String.format("%s", FORM_TYPE_SINGLE);
                return s;
        }
    }
}
