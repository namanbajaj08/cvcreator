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

import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class ReferenceDetails extends AppCompatActivity {

    RegistrationAdapter adpt;
    Cursor cursor;
    EditText name, post, org , contact;
    Button save;
    String getname , getPost , getOrg , getContact;
    String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrence_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();



        name =(EditText)findViewById(R.id.refer_name);
        post =(EditText)findViewById(R.id.refer_post);
        org =(EditText)findViewById(R.id.refer_company);
        contact =(EditText)findViewById(R.id.refer_contact);
        save = (Button)findViewById(R.id.save);

        adpt = new RegistrationAdapter(getApplicationContext());
        cursor =adpt.queryrefer(Util.ref_id);
        if (cursor.moveToFirst()){
            do {
                getname = cursor.getString(1);
                getPost = cursor.getString(2);
                getOrg = cursor.getString(3);
                getContact = cursor.getString(4);
            } while (cursor.moveToNext());
        }

        if (getname!=null){
            name.setText(getname);
            name.setSelection(name.getText().length());
            post.setText(getPost);
            post.setSelection(post.getText().length());
            org.setText(getOrg);
            org.setSelection(org.getText().length());
            contact.setText(getContact);
            contact.setSelection(contact.getText().length());

        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_name = name.getText().toString();
                String add_post = post.getText().toString();
                String add_org = org.getText().toString();
                String add_conatct = contact.getText().toString();

                int r_id = Util.resumeid;

                if (add_name.equals("") || add_post.equals("") || add_org.equals("") || add_conatct.equals("")){
                    Toast.makeText(ReferenceDetails.this, "Fields can not be blank" ,Toast.LENGTH_SHORT).show();
                }
                else if (getname != null){
                    long val = adpt.updatereferinfo(add_name,add_post,add_org,add_conatct,r_id);
                    Toast.makeText(ReferenceDetails.this , "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    long val = adpt.referInfo(add_name,add_post,add_org,add_conatct,r_id);
                    Toast.makeText(ReferenceDetails.this , "Inserted successfully", Toast.LENGTH_SHORT).show();
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
}
