package com.jorgepinedo.barcodepdf417;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.microblink.MicroblinkSDK;
import com.microblink.activity.BarcodeScanActivity;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkbarcode.pdf417.Pdf417Recognizer;
import com.microblink.locale.LanguageUtils;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BarcodeUISettings;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 200;
    private Pdf417Recognizer mRecognizer;
    private RecognizerBundle mRecognizerBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LanguageUtils.setLanguageAndCountry("hr", "", this);

        MicroblinkSDK.setLicenseKey("sRwAAAAdY29tLmpvcmdlcGluZWRvLmJhcmNvZGVwZGY0MTfoMZbmjpXFig7TNj3bniC5g3j/iKlowyVgv2uhLw4BmiJsX2OEhXKwcZTsshddnr0wGG0AN6pWvc9UWM6EY8cZGmmG5AlFTSwNu1mqjJyS4OWjXyOTJ3NNIUhKoEFV56eSyVZF", this);

        mRecognizer = new Pdf417Recognizer();

        // bundle recognizers into RecognizerBundle
        mRecognizerBundle = new RecognizerBundle(mRecognizer);

        startScanning();
    }

    // method within MyActivity from previous step
    public void startScanning() {
        // Settings for BarcodeScanActivity Activity
        BarcodeUISettings settings = new BarcodeUISettings(mRecognizerBundle);

        // tweak settings as you wish

        // Start activity
        ActivityRunner.startActivityForResult(this, MY_REQUEST_CODE, settings);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == BarcodeScanActivity.RESULT_OK && data != null) {
                // load the data into all recognizers bundled within your RecognizerBundle
                mRecognizerBundle.loadFromIntent(data);

                // now every recognizer object that was bundled within RecognizerBundle
                // has been updated with results obtained during scanning session

                // you can get the result by invoking getResult on recognizer
                Pdf417Recognizer.Result result = mRecognizer.getResult();
                if (result.getResultState() == Recognizer.Result.State.Valid) {
                    Log.d("JORKE",result.getBarcodeType().toString());
                    Log.d("JORKE-11",result.getStringData());
                    // result is valid, you can use it however you wish
                }
            }
        }
    }
}
