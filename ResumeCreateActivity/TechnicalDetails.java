package com.naman.resumemaker.ResumeCreateActivity;


import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.Adapter.TechnicalAdapter;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class TechnicalDetails extends AppCompatActivity {

    RegistrationAdapter adpt;
    Cursor cursor;
    String action;
    ListView listView;
    final Context context = this;
    public  String [] technical_skill;
    String [] id;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.tech_id = 0;
                ViewDialog alert = new ViewDialog();
                alert.showDialog(context);
            }
        });

        textView = (TextView)findViewById(R.id.add_technical);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();


        listView=(ListView)findViewById(R.id.technical_list);
        listView.setEmptyView(textView);

        try
        {
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(Util.resumeid);
            id = new String[this.cursor.getCount()];
            technical_skill = new String[this.cursor.getCount()];
            int i = 0;
            if (cursor.moveToNext()){
                do {
                    id[i]=cursor.getString(1);
                    technical_skill[i]=cursor.getString(2);
                    i++;
                }while (cursor.moveToNext());
            }
            listView.setAdapter(new TechnicalAdapter(this, id , technical_skill));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class ViewDialog {

        private TextView btn_cancel , btn_add;

        public void showDialog(Context activity )
        {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_technical_skills);

            final EditText skill=(EditText)dialog.findViewById(R.id.technical);
            btn_cancel = (TextView)dialog.findViewById(R.id.cancel);
            btn_add = (TextView)dialog.findViewById(R.id.add);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String tec_skills = skill.getText().toString();
                    int r_id = Util.resumeid;

                    if(tec_skills.equals("")){
                        Toast.makeText(getApplicationContext(),"Enter Technical Skill",Toast.LENGTH_SHORT).show();
                    }

                    else {
                        long val =adpt.insertTechnicalDetail(tec_skills ,r_id);
                        getdata();
                        dialog.dismiss();

                    }
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    skill.getText().clear();
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }


    public void getdata(){
        try
        {
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(Util.resumeid);
            id = new String[this.cursor.getCount()];
            technical_skill = new String[this.cursor.getCount()];
            int i = 0;
            if (cursor.moveToNext()){
                do {
                    id[i]=cursor.getString(1);
                    technical_skill[i]=cursor.getString(2);
                    i++;
                }while (cursor.moveToNext());
            }
            listView.setAdapter(new TechnicalAdapter(this, id , technical_skill));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




}
