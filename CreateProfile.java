package com.naman.resumemaker;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.Adapter.ProfileAdapter;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.Database.RegistrationOpenHelper;

public class CreateProfile extends AppCompatActivity {

    public static boolean math = false;
    RegistrationAdapter adpt;
    Cursor cursor;
    SQLiteDatabase db_bo;
    RegistrationOpenHelper helper;
    String action;
    public static ListView listView;
    final Context context = this;
    public static String [] profile;
    String [] id;
    TextView hide_txt;
    ProfileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btn_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialog alert = new ViewDialog();
                alert.showDialog(context);
            }
        });


        hide_txt=(TextView)findViewById(R.id.add_profile);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();


        listView=(ListView)findViewById(R.id.create_profile);
        listView.setEmptyView(hide_txt);

        try {
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryName();
            id = new String[this.cursor.getCount()];
            profile = new String[this.cursor.getCount()];
            int i = 0;
            if (cursor.moveToFirst()) {
                do {
                    id[i] = cursor.getString(1);
                    profile[i] = cursor.getString(2);
                    i++;
                } while (cursor.moveToNext());
            }
            listView.setAdapter(new ProfileAdapter(this,id,profile));
        } catch (Exception e) {
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
            dialog.setContentView(R.layout.add_profile);

            final EditText profile_name=(EditText)dialog.findViewById(R.id.add_profile_name);
            btn_cancel = (TextView)dialog.findViewById(R.id.cancel);
            btn_add = (TextView)dialog.findViewById(R.id.add);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreateProfile.math = false;
                    String pro_name = profile_name.getText().toString();
                    if (profile.length > 0){
                        for (String k : profile){
                            if (k.toString().equals(pro_name)){
                                CreateProfile.math=true;
                            }
                        }
                    }
                    if(pro_name.equals("")){
                        Toast.makeText(getApplicationContext(),"Please Enter name",Toast.LENGTH_SHORT).show();
                    }
                    else if (CreateProfile.math){

                        Toast.makeText(getApplicationContext(),"Profile Already Exist",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        long val =adpt.insertDetail(pro_name);
                        getdata();
                        dialog.dismiss();

                    }
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profile_name.getText().clear();
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
            cursor = adpt.queryName();
            id = new String[this.cursor.getCount()];
            profile = new String[this.cursor.getCount()];
            int i = 0;
            if (cursor.moveToNext()){
                do {
                    id[i]=cursor.getString(1);
                    profile[i]=cursor.getString(2);
                    i++;
                }while (cursor.moveToNext());
            }
            listView.setAdapter(new ProfileAdapter(this, id , profile));

            listView.invalidateViews();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
