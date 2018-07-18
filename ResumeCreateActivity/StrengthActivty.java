package com.naman.resumemaker.ResumeCreateActivity;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.Adapter.StrengthAdapter;
import com.naman.resumemaker.Database.RegistrationAdapter;

import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class StrengthActivty extends AppCompatActivity {

    public static ListView st_listView;
    final Context context = this;
    RegistrationAdapter adpt;
    Cursor cursor;
    String action;
    TextView textView;
    String[] strength_id;
    String[] strength_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.strength_id = 0;
                ViewDialog alert = new ViewDialog();
                alert.showDialog(context);
            }
        });

        textView = (TextView)findViewById(R.id.add_strength);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();


        st_listView=(ListView)findViewById(R.id.strength_list);
        st_listView.setEmptyView(textView);

        try
        {
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryStrength(Util.resumeid);
            strength_id = new String[this.cursor.getCount()];
            strength_name = new String[this.cursor.getCount()];
            int i = 0;
            if (cursor.moveToNext()){
                do {
                    strength_id[i]=cursor.getString(1);
                    strength_name[i]=cursor.getString(2);
                    i++;
                }while (cursor.moveToNext());
            }
            st_listView.setAdapter(new StrengthAdapter(this, strength_id , strength_name));
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
            dialog.setContentView(R.layout.strength_details);

            final EditText skill=(EditText)dialog.findViewById(R.id.strength_detail);
            btn_cancel = (TextView)dialog.findViewById(R.id.cancel);
            btn_add = (TextView)dialog.findViewById(R.id.add);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String add_strength = skill.getText().toString();
                    int r_id = Util.resumeid;

                    if(add_strength.equals("")){
                        Toast.makeText(getApplicationContext(),"Enter your Strength",Toast.LENGTH_SHORT).show();
                    }

                    else {
                        long val =adpt.insertStrength(add_strength ,r_id);
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
            cursor = adpt.queryStrength(Util.resumeid);
            strength_id = new String[this.cursor.getCount()];
            strength_name = new String[this.cursor.getCount()];
            int i = 0;
            if (cursor.moveToNext()){
                do {
                    strength_id[i]=cursor.getString(1);
                    strength_name[i]=cursor.getString(2);
                    i++;
                }while (cursor.moveToNext());
            }
            st_listView.setAdapter(new StrengthAdapter(this, strength_id , strength_name));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
