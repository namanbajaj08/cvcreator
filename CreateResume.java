package com.naman.resumemaker;

// Color icon android:background="#59B5F7"

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.naman.resumemaker.Adapter.CustomAdapter;

public class CreateResume extends AppCompatActivity {

    ListView listView;
    Context context;
    String action;
    Button generate;
    public static String data;

    private String [] attribute = {"Applicant header" , "Career Objective", "Academic Records", "Work Experience",
                                   "Technical Details", "Training","Projects", "Achievements and Rewards",
                                    "Extracurricular Activites", "Hobbies", "Strength", "Personal Details",
                                    "References", "Declaration"};


    private int [] img = {R.drawable.applicant,R.drawable.objective, R.drawable.academic, R.drawable.experience,
            R.drawable.technical_details,R.drawable.training, R.drawable.projects, R.drawable.rewards, R.drawable.extra,R.drawable.hobby,
            R.drawable.strength, R.drawable.personal_detail , R.drawable.refrence , R.drawable.decleartion};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        context= this;
        listView = (ListView)findViewById(R.id.list_view);
        generate = (Button)findViewById(R.id.generate);

        listView.setAdapter(new CustomAdapter(this, img, attribute));

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView show= (TextView)findViewById(R.id.show_name);
        toolbar.setContentInsetsAbsolute(0,0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        action = getIntent().getAction();
        data = getIntent().getExtras().getString("name");
        show.setText(data);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateResume.this , Grid_View.class);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
