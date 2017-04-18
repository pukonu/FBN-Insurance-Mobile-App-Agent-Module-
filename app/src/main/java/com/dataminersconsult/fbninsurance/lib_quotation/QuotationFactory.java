package com.dataminersconsult.fbninsurance.lib_quotation;


public class QuotationFactory {

    public static final int[] POLICY_DURATION = {1 ,2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    public enum POLICY_CATEGORIES {
        Platinum, Premium, Gold, Diamond, Classic
    }

    public enum POLICY_TYPES {
        FIPP_GOLD
    }

    public enum PAYMENT_FREQUENCY {
        Weekly, Monthly, Quarterly, Yearly
    }

}
