package com.naman.resumemaker.DetailedActivity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class ExtraDetail extends AppCompatActivity {

    RegistrationAdapter adpt;
    Cursor cursor;
    String action;
    EditText extra;
    Button save;
    String getExtra;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();



        extra =(EditText)findViewById(R.id.extra_detail);
        save = (Button)findViewById(R.id.save);

        adpt = new RegistrationAdapter(getApplicationContext());
        cursor =adpt.queryextra(Util.extra_id);
        if (cursor.moveToFirst()){
            do {
                getExtra = cursor.getString(1);

            } while (cursor.moveToNext());
        }

        if (getExtra!=null){
            extra.setText(getExtra);
            extra.setSelection(extra.getText().length());

        }

        MobileAds.initialize(this,"ca-app-pub-5147809161095891~1490088646");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String add_extra = extra.getText().toString();
                int r_id = Util.resumeid;

                if (add_extra.equals("")){
                    Toast.makeText(ExtraDetail.this ,"Field can not be blank",Toast.LENGTH_SHORT).show();
                }
                else if (getExtra != null){
                    long val = adpt.updateextra(add_extra,r_id);
                    Toast.makeText(ExtraDetail.this , "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    long val = adpt.extra_curricular(add_extra,r_id);
                    Toast.makeText(ExtraDetail.this , "Inserted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
