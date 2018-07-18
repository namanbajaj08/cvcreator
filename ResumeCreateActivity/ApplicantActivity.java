package com.naman.resumemaker.ResumeCreateActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.CreateProfile;
import com.naman.resumemaker.CreateResume;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.Database.RegistrationOpenHelper;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

public class ApplicantActivity extends AppCompatActivity {

    EditText userName,emailId,number,url;
    Button save;
    String action;
    RegistrationAdapter reg;
    RegistrationOpenHelper help;
    int rowId;
    String rowName;
    Cursor cursor;
    String u_mail;
    String u_name;
    String u_number;
    String u_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();

        userName = (EditText)findViewById(R.id.name);
        emailId = (EditText)findViewById(R.id.email);
        number = (EditText)findViewById(R.id.contact);
        url = (EditText)findViewById(R.id.link);

        rowId = Util.resumeid;
        rowName =Util.resumename;
        reg = new RegistrationAdapter(this);
        cursor = reg.queryAll(rowId);
        if (cursor.moveToFirst()){
            do {
                u_name = cursor.getString(1);
                u_mail = cursor.getString(2);
                u_number = cursor.getString(3);
                u_link = cursor.getString(4);
            } while (cursor.moveToNext());

        }

        if (u_name != null){
            userName.setText(u_name);
            userName.setSelection(userName.getText().length());
            emailId.setText(u_mail);
            emailId.setSelection(emailId.getText().length());
            number.setText(u_number);
            number.setSelection(number.getText().length());
            url.setText(u_link);
            url.setSelection(url.getText().length());
        }

        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String email = emailId.getText().toString();
                String contact = number.getText().toString();
                String link = url.getText().toString();
                int r_id = Util.resumeid;
                if (name.equals("") || email.equals("") || contact.equals("")){
                    Toast.makeText(ApplicantActivity.this , "Fields can't be blank" , Toast.LENGTH_SHORT).show();
                }
                else if (u_name != null){
                    long val = reg.updateapplicantDetail(name , email , contact , link , r_id);
                    Toast.makeText(ApplicantActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    rowId = Util.resumeid;
                    rowName =Util.resumename;
                    Intent intent = new Intent(ApplicantActivity.this , CreateResume.class);
                    intent.putExtra("id",rowId);
                    intent.putExtra("name",rowName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
                else {
                    long val = reg.insertappilcantDetail(name , email , contact , link , r_id);
                    Toast.makeText(ApplicantActivity.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                    rowId = Util.resumeid;
                    rowName =Util.resumename;
                    Intent intent = new Intent(ApplicantActivity.this , CreateResume.class);
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
}
