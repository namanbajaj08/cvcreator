package com.naman.resumemaker.ResumeCreateActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.naman.resumemaker.CreateResume;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.Database.RegistrationOpenHelper;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class DeclerationActivity extends AppCompatActivity {

    EditText declearation;
    Button save;
    RegistrationAdapter reg;
    RegistrationOpenHelper help;
    String dec;
    int rowId;
    String rowName;
    Cursor cursor;
    String action;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decleration);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();

        rowId = Util.resumeid;
        rowName =Util.resumename;

        declearation =(EditText)findViewById(R.id.decleration);
        declearation.setSelection(declearation.getText().length());
        save = (Button)findViewById(R.id.save);
        reg = new RegistrationAdapter(this);
        cursor = reg.queryDecleartion(Util.resumeid);
        if (cursor.moveToFirst()){
            do {
                dec = cursor.getString(1);
            }while (cursor.moveToNext());
        }

        if (dec != null){
            declearation.setText(dec);
            declearation.setSelection(declearation.getText().length());
        }

        MobileAds.initialize(this,"ca-app-pub-5147809161095891~1490088646");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String declear = declearation.getText().toString();


                int r_id = Util.resumeid;
                if (declear.equals("")){
                    declearation.setSelection(declearation.getText().length());
                    Toast.makeText(DeclerationActivity.this, "Can't be blank" , Toast.LENGTH_SHORT).show();
                }
                else if (dec !=null){
                    long insertObjective = reg.updateDecleration(declear,r_id);
                    Toast.makeText(DeclerationActivity.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                    rowId = Util.resumeid;
                    rowName =Util.resumename;
                    Intent intent = new Intent(DeclerationActivity.this , CreateResume.class);
                    intent.putExtra("id",rowId);
                    intent.putExtra("name",rowName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
                else {
                    long insertObjective = reg.insertDecleration(declear,r_id);
                    Toast.makeText(DeclerationActivity.this,"Inserted successfully",Toast.LENGTH_SHORT).show();
                    rowId = Util.resumeid;
                    rowName =Util.resumename;
                    Intent intent = new Intent(DeclerationActivity.this , CreateResume.class);
                    intent.putExtra("id",rowId);
                    intent.putExtra("name",rowName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
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
