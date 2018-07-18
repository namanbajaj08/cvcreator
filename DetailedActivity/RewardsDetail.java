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

public class RewardsDetail extends AppCompatActivity {

    RegistrationAdapter adpt;
    Cursor cursor;
    String action;
    EditText reward;
    Button save;
    String getReward;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();

        reward =(EditText)findViewById(R.id.rewards);
        save = (Button)findViewById(R.id.save);

        adpt = new RegistrationAdapter(getApplicationContext());
        cursor =adpt.queryrewards(Util.rewards_id);
        if (cursor.moveToFirst()){
            do {
                getReward = cursor.getString(1);

            } while (cursor.moveToNext());
        }

        if (getReward!=null){
            reward.setText(getReward);
            reward.setSelection(reward.getText().length());

        }

        MobileAds.initialize(this,"ca-app-pub-5147809161095891~1490088646");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_reward = reward.getText().toString();
                int r_id = Util.resumeid;

                if (add_reward.equals("")){
                    Toast.makeText(RewardsDetail.this ,"Field can not be blank",Toast.LENGTH_SHORT).show();
                }
                else if (getReward != null){
                    long val = adpt.updaterewards(add_reward,r_id);
                    Toast.makeText(RewardsDetail.this , "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    long val = adpt.rewards(add_reward,r_id);
                    Toast.makeText(RewardsDetail.this , "Inserted successfully", Toast.LENGTH_SHORT).show();
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
