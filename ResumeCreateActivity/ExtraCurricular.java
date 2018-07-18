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

import com.naman.resumemaker.Adapter.ExtraAdapter;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.DetailedActivity.ExtraDetail;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class ExtraCurricular extends AppCompatActivity {

    ListView listView;
    Context context;
    RegistrationAdapter adpt;
    Cursor cursor;
    TextView textView;
    String [] extra_id;
    String [] extra_name;
    String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curricular);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.extra_id = 0;
                Intent intent = new Intent(ExtraCurricular.this , ExtraDetail.class);
                startActivity(intent);
            }
        });

        textView = (TextView)findViewById(R.id.add_extra);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        action = getIntent().getAction();




        listView =(ListView)findViewById(R.id.extra_list);
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
            cursor = adpt.queryNameExtra(Util.resumeid);
            extra_id = new String[cursor.getCount()];
            extra_name =new  String[cursor.getCount()];
            int i = 0;
            if (cursor.moveToFirst()){
                do {
                    extra_id[i] = cursor.getString(1);
                    extra_name[i] = cursor.getString(2);
                    i++;
                }while (cursor.moveToNext());
            }
            listView.setAdapter(new ExtraAdapter(this , extra_id , extra_name));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
