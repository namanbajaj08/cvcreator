package com.naman.resumemaker.ResumeCreateActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.naman.resumemaker.Adapter.RewardsAdapter;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.DetailedActivity.RewardsDetail;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class RewardsActivty extends AppCompatActivity {

    ListView listView;
    Context context;
    RegistrationAdapter adpt;
    Cursor cursor;
    String action;
    TextView textView;
    String [] reward_id;
    String [] reward_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.rewards_id= 0;
                Intent intent = new Intent(RewardsActivty.this, RewardsDetail.class);
                startActivity(intent);
            }
        });

        textView = (TextView)findViewById(R.id.add_reward);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();



        listView =(ListView)findViewById(R.id.reward_list);
        listView.setEmptyView(textView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRewards(Util.resumeid);
            reward_id = new String[cursor.getCount()];
            reward_name =new  String[cursor.getCount()];
            int i = 0;
            if (cursor.moveToFirst()){
                do {
                    reward_id[i] = cursor.getString(1);
                    reward_name[i] = cursor.getString(2);
                    i++;
                }while (cursor.moveToNext());
            }
            listView.setAdapter(new RewardsAdapter(this , reward_id , reward_name));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
