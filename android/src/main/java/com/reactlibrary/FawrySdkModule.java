package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.emeint.android.fawryplugin.Plugininterfacing.FawrySdk;
import com.emeint.android.fawryplugin.Plugininterfacing.PayableItem;
import com.emeint.android.fawryplugin.exceptions.FawryException;
import com.emeint.android.fawryplugin.interfaces.FawrySdkCallback;
import com.emeint.android.fawryplugin.managers.FawryPluginAppClass;
import com.emeint.android.fawryplugin.models.FawryCardToken;
import com.emeint.android.fawryplugin.utils.UiUtils;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class FawrySdkModule extends ReactContextBaseJavaModule implements FawrySdkCallback, ActivityEventListener {

    private final ReactApplicationContext reactContext;
    private final int PAYMENT_PLUGIN_REQUEST = 1023;
    private final int CARD_TOKENIZER_REQUEST = 1024;
    private static final String LANGUAGE_KEY = "LANGUAGES";
    private static final String LANGUAGE_EN_KEY = "EN";
    private static final String LANGUAGE_AR_KEY = "AR";
    private static final String STYLE_KEY = "STYLES";
    private static final String STYLE_1_KEY = "STYLE_1";
    private static final String STYLE_2_KEY = "STYLE_2";

    private final String TAG = "FAWRY";
    private Promise mPromise;

    public FawrySdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(this);
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        Map<String, Object> language = new HashMap<>();
        language.put(LANGUAGE_EN_KEY, FawrySdk.Language.EN.name());
        language.put(LANGUAGE_AR_KEY, FawrySdk.Language.AR.name());
        constants.put(LANGUAGE_KEY, language);

        Map<String, Object> style = new HashMap<>();
        style.put(STYLE_1_KEY, FawrySdk.Styles.STYLE1.name());
        style.put(STYLE_2_KEY, FawrySdk.Styles.STYLE2.name());
        constants.put(STYLE_KEY, style);

        return constants;
    }

    @Override
    public String getName() {
        return "FawrySdk";
    }

    @ReactMethod
    public void init(String style, Promise promise) {
        try {
            FawrySdk.init(FawrySdk.Styles.valueOf(style));
        } catch (Exception ex) {
            promise.reject("Failed: ", ex.toString());
            return;
        }
        promise.resolve("Success");
    }

    @ReactMethod
    public void initPaymentPlugin(String merchantID, String serverURL, ReadableArray items, String language, Promise promise) {
        List listItems = ConvertUtils.readableArrayToList(items);
        List payableItems = ConvertUtils.objectListToPayableItems(listItems);
        FawryPluginAppClass.enableLogging = false;
        String merchantRefNumber = randomAlphaNumeric(16);
        try {
            FawrySdk.initialize(this.getCurrentActivity(), serverURL, this, merchantID, merchantRefNumber, payableItems, FawrySdk.Language.valueOf(language), PAYMENT_PLUGIN_REQUEST, null, new UUID(1, 2));
        } catch (FawryException e) {
            UiUtils.showDialog(getCurrentActivity(), e, false);
            e.printStackTrace();
            promise.reject("Failed to initialize Card Tokenizer: ", e.toString());
        }
        promise.resolve("Payment Plugin has been initialized");
    }

    @ReactMethod
    public void initCardTokenizer(String merchantID, String serverURL, String customerMobile, String customerEmail, String language, final Promise promise) {
        String merchantRefNumber = randomAlphaNumeric(16);
        try {
            FawrySdk.initializeCardTokenizer(this.getCurrentActivity(), serverURL, this, merchantID, merchantRefNumber,
                    customerMobile, customerEmail, FawrySdk.Language.valueOf(language), CARD_TOKENIZER_REQUEST, null, new UUID(1, 2));
        } catch (FawryException e) {
            UiUtils.showDialog(getCurrentActivity(), e, false);
            e.printStackTrace();
            promise.reject("Failed :", e.toString());
        }
        promise.resolve("Success");
    }

    @ReactMethod
    public void startPaymentActivity(Promise promise) {
        mPromise = promise;
        FawrySdk.startPaymentActivity(this.reactContext);
    }

    @ReactMethod
    public void resetFawrySDK(){
        FawrySdk.resetFawrySDK();
    }


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Override
    public void onSuccess(String s, Object o) {
    }

    @Override
    public void onFailure(String s) {
    }

    @Override
    public void paymentOperationSuccess(String s, Object o) {
    }

    @Override
    public void paymentOperationFailure(String s, Object o) {
    }


    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYMENT_PLUGIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                int requestResult = data.getIntExtra(FawryPluginAppClass.REQUEST_RESULT, -1);
                if (requestResult == FawryPluginAppClass.SUCCESS_CODE) {
                    mPromise.resolve("Success");
                } else if (requestResult == FawryPluginAppClass.FAILURE_CODE) {
                    mPromise.reject("Failed", "Payment Failed");
                }
            } else {
                mPromise.reject("Failed", "Payment Failed");
            }
        } else if (requestCode == CARD_TOKENIZER_REQUEST) {
            if (resultCode == RESULT_OK) {
                FawryCardToken fawryCardToken = data.getParcelableExtra(FawryPluginAppClass.CARD_TOKEN_KEY);
                WritableMap map = Arguments.createMap();
                map.putString("token", fawryCardToken.getToken());
                map.putString("lastFourDigits", fawryCardToken.getLastFourDigits());
                mPromise.resolve(map);
            } else {
                mPromise.reject("Failed: ", "Error card tokenizer");
            }
        }

    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: ");
    }
}
