package com.naman.resumemaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    TextView create , showResume;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-5147809161095891~1490088646");

        create= (TextView)findViewById(R.id.create_resume);
        showResume =(TextView)findViewById(R.id.checkpdf);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CreateProfile.class);
                startActivity(intent);
            }
        });

        showResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this,ShowResume.class);
                    startActivity(intent);
                }


            }
        });

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-5147809161095891/1624329325");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener()
                                     {
                                         @Override
                                         public void onAdClosed() {
                                             Intent intent = new Intent(MainActivity.this,ShowResume.class);
                                             startActivity(intent);
                                             interstitialAd.loadAd(new AdRequest.Builder().build());
                                         }
                                     }

        );

    }


}
