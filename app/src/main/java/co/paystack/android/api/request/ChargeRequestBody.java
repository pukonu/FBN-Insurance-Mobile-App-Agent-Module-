package co.paystack.android.api.request;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import co.paystack.android.PaystackSdk;
import co.paystack.android.model.Charge;
import co.paystack.android.utils.Crypto;
import co.paystack.android.utils.StringUtils;

/**
 * Charge Request Body
 */
public class ChargeRequestBody extends BaseRequestBody {

    public static final String FIELD_CLIENT_DATA = "clientdata";
    public static final String FIELD_LAST4 = "last4";
    public static final String FIELD_PUBLIC_KEY = "public_key";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_REFERENCE = "reference";
    public static final String FIELD_SUBACCOUNT = "subaccount";
    public static final String FIELD_TRANSACTION_CHARGE = "transaction_charge";
    public static final String FIELD_BEARER = "bearer";
    public static final String FIELD_HANDLE = "handle";
    public static final String FIELD_METADATA = "metadata";
    public static final String FIELD_CURRENCY = "currency";
    public static final String FIELD_PLAN = "plan";

    @SerializedName(FIELD_CLIENT_DATA)
    public final String clientData;

    @SerializedName(FIELD_LAST4)
    public final String last4;

    @SerializedName(FIELD_PUBLIC_KEY)
    public final String public_key;

    @SerializedName(FIELD_EMAIL)
    public final String email;

    @SerializedName(FIELD_AMOUNT)
    public final String amount;

    @SerializedName(FIELD_REFERENCE)
    public final String reference;

    @SerializedName(FIELD_SUBACCOUNT)
    public final String subaccount;

    @SerializedName(FIELD_TRANSACTION_CHARGE)
    public final String transaction_charge;

    @SerializedName(FIELD_BEARER)
    public final String bearer;

    @SerializedName(FIELD_HANDLE)
    public String handle;

    @SerializedName(FIELD_METADATA)
    public String metadata;

    @SerializedName(FIELD_CURRENCY)
    public String currency;

    @SerializedName(FIELD_PLAN)
    public String plan;


    public ChargeRequestBody addPin(String pin) {
        this.handle = Crypto.encrypt(pin);
        return this;
    }

    private HashMap<String, String> additionalParameters;

    public ChargeRequestBody(Charge charge) {
        this.clientData = Crypto.encrypt(StringUtils.concatenateCardFields(charge.getCard()));
        this.last4 = charge.getCard().getLast4digits();
        this.public_key = PaystackSdk.getPublicKey();
        this.email = charge.getEmail();
        this.amount = Integer.toString(charge.getAmount());
        this.reference = charge.getReference();
        this.subaccount = charge.getSubaccount();
        this.transaction_charge = charge.getTransactionCharge() > 0 ? Integer.toString(charge.getTransactionCharge()) : null;
        this.bearer = charge.getBearer() != null ? charge.getBearer().name() : null;
        this.metadata = charge.getMetadata();
        this.plan = charge.getPlan();
        this.currency = charge.getCurrency();
        this.additionalParameters = charge.getAdditionalParameters();
    }


    @Override
    public HashMap<String, String> getParamsHashMap() {
        // set values will override additional params provided
        HashMap<String, String> params = additionalParameters;
        params.put(FIELD_PUBLIC_KEY, public_key);
        params.put(FIELD_CLIENT_DATA, clientData);
        params.put(FIELD_LAST4, last4);
        params.put(FIELD_EMAIL, email);
        params.put(FIELD_AMOUNT, amount);
        if (handle != null) {
            params.put(FIELD_HANDLE, handle);
        }
        if (reference != null) {
            params.put(FIELD_REFERENCE, reference);
        }
        if (subaccount != null) {
            params.put(FIELD_SUBACCOUNT, subaccount);
        }
        if (transaction_charge != null) {
            params.put(FIELD_TRANSACTION_CHARGE, transaction_charge);
        }
        if (bearer != null) {
            params.put(FIELD_BEARER, bearer);
        }
        if (metadata != null) {
            params.put(FIELD_METADATA, metadata);
        }
        if (plan != null) {
            params.put(FIELD_PLAN, plan);
        }
        if (currency != null) {
            params.put(FIELD_CURRENCY, currency);
        }
        return params;
    }
}
