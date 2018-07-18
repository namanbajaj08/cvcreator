package com.naman.resumemaker.ResumeCreateActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.naman.resumemaker.Adapter.ProfileAdapter;
import com.naman.resumemaker.Adapter.ProjectAdapter;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.DetailedActivity.ProjectDetails;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class ProjectActivity extends AppCompatActivity {

    ListView listView;
    Context context;
    RegistrationAdapter adpt;
    Cursor cursor;
    String action;
    TextView textView;
    String[] project_id;
    String[] project_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);


        textView = (TextView)findViewById(R.id.add_project);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.project_id = 0;
                Intent intent = new Intent(ProjectActivity.this, ProjectDetails.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();



        listView = (ListView) findViewById(R.id.project_list);
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
            cursor = adpt.queryNameProject(Util.resumeid);
            project_id = new String[cursor.getCount()];
            project_name = new String[cursor.getCount()];
            int i = 0;
            if (cursor.moveToFirst()) {
                do {
                    project_id[i] = cursor.getString(1);
                    project_name[i] = cursor.getString(2);
                    i++;
                } while (cursor.moveToNext());
            }
            listView.setAdapter(new ProjectAdapter(this, project_id, project_name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
