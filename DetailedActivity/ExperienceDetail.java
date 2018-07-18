package com.naman.resumemaker.DetailedActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExperienceDetail extends AppCompatActivity {

    private static final String TAG = "ExperienceDetail";
    ImageView date_pick , date_picker;
    private EditText from , to;
    private DatePickerDialog.OnDateSetListener mDateSetListen ;
    private DatePickerDialog.OnDateSetListener mDateSetListener ;
    String action;
    RegistrationAdapter adpt;
    Cursor cursor;
    EditText org , post , role;
    RadioGroup rg1;
    AppCompatRadioButton previous, currently;
    Button save;
    String getOrg ,getPost , getDateFrom ,getDateTo,getRole, getWorking;
    private DatePickerDialog mDatePicker;
    int year,month,day;
    int fr_year,fr_month,fr_day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();



        org= (EditText)findViewById(R.id.organization);
        post = (EditText)findViewById(R.id.designation);
        rg1 = (RadioGroup)findViewById(R.id.radio_btn);
        previous = (AppCompatRadioButton)findViewById(R.id.previous);
        currently =(AppCompatRadioButton)findViewById(R.id.currently);
        role =(EditText)findViewById(R.id.role);
        save = (Button)findViewById(R.id.save);

        from = (EditText)findViewById(R.id.date_from);
        to = (EditText)findViewById(R.id.date_to);
        date_pick=(ImageView)findViewById(R.id.date_pick);
        date_picker=(ImageView)findViewById(R.id.date_picker);


        final Calendar mcurrentDate=Calendar.getInstance();
        fr_year=mcurrentDate.get(Calendar.YEAR);
        fr_month=mcurrentDate.get(Calendar.MONTH);
        fr_day=mcurrentDate.get(Calendar.DAY_OF_MONTH);



        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.previous){
                    to.setEnabled(true);
                    date_picker.setEnabled(true);
                }

                if (i == R.id.currently){
                    to.setEnabled(false);
                    date_picker.setEnabled(false);
                    to.setText("");
                }
            }
        });
        adpt = new RegistrationAdapter(getApplicationContext());
        cursor =adpt.queryexpinfo1(Util.workinfo_id);
        if (cursor.moveToFirst()){
            do {
                getOrg = cursor.getString(1);
                getPost = cursor.getString(2);
                getWorking = cursor.getString(3);
                getDateFrom = cursor.getString(4);
                getDateTo =cursor.getString(5);
                getRole = cursor.getString(6);
            } while (cursor.moveToNext());
        }

        if (getOrg!=null){
            org.setText(getOrg);
            org.setSelection(org.getText().length());
            post.setText(getPost);
            post.setSelection(post.getText().length());
            if (getWorking.equals("Previously")){
                previous.setChecked(true);
            }
            else {
                currently.setChecked(true);
            }
            from.setText(getDateFrom);
            from.setSelection(from.getText().length());
            to.setText(getDateTo);
            to.setSelection(to.getText().length());
            role.setText(getRole);
            role.setSelection(role.getText().length());

        }


        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fr_year == 0 || fr_month == 0 || fr_day == 0) {
                    Calendar mcurrentDate=Calendar.getInstance();
                    fr_year=mcurrentDate.get(Calendar.YEAR);
                    fr_month=mcurrentDate.get(Calendar.MONTH);
                    fr_day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                }

                DatePickerDialog mDatePicker=new DatePickerDialog(ExperienceDetail.this, R.style.DialogTheme,new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        fr_year = selectedyear;
                        fr_month = selectedmonth;
                        fr_day = selectedday;
                        final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        String fm = MONTHS[fr_month];
                        from.setText(new StringBuilder().append(fm).append(",").append(fr_year));
                        from.setSelection(from.getText().length());
                        from.setTextColor(Color.BLACK);
                    }
                },fr_year, fr_month, fr_day);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();

            }
        });


        date_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (year == 0 || month == 0 || day == 0) {
                        Calendar mcurrentDate=Calendar.getInstance();
                        year=mcurrentDate.get(Calendar.YEAR);
                        month=mcurrentDate.get(Calendar.MONTH);
                        day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        }

                    mDatePicker=new DatePickerDialog(ExperienceDetail.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                        {
                            year = selectedyear;
                            month = selectedmonth;
                            day = selectedday;
                            final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                            String fm = MONTHS[month];
                            to.setText(new StringBuilder().append(fm).append(",").append(year));
                            to.setSelection(from.getText().length());
                            to.setTextColor(Color.BLACK);
                        }
                    },year, month, day);
                    mDatePicker.updateDate(year,month,day);
                    mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                    mDatePicker.show();

                }
            });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String add_org = org.getText().toString();
                String add_post = post.getText().toString();
                String add_working = "";
                if (rg1.getCheckedRadioButtonId()== R.id.previous)
                    add_working="Previously";
                if (rg1.getCheckedRadioButtonId()==R.id.currently)
                    add_working="Currently";
                String add_dateFrom = from.getText().toString();
                String add_date_to = to.getText().toString();
                String add_role = role.getText().toString();

                int r_id = Util.resumeid;


                if (rg1.getCheckedRadioButtonId()==R.id.previous) {
                    if (add_org.equals("") || add_post.equals("") || add_dateFrom.equals("") || add_date_to.equals("") || add_role.equals("")) {
                        Toast.makeText(ExperienceDetail.this, "Fields can not be blank", Toast.LENGTH_SHORT).show();
                    } else if (getOrg != null) {
                        long val = adpt.updateworkexpinfo(add_org, add_post, add_working, add_dateFrom, add_date_to, add_role, r_id);
                        Toast.makeText(ExperienceDetail.this, "Updated successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        long val = adpt.workexpinfo(add_org, add_post, add_working, add_dateFrom, add_date_to, add_role, r_id);
                        Toast.makeText(ExperienceDetail.this, "Inserted successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                else {
                    if (add_org.equals("") || add_post.equals("") || add_dateFrom.equals("") || add_role.equals("")) {
                        Toast.makeText(ExperienceDetail.this, "Fields can not be blank", Toast.LENGTH_SHORT).show();
                    } else if (getOrg != null) {
                        long val = adpt.updateworkexpinfo(add_org, add_post, add_working, add_dateFrom, add_date_to, add_role, r_id);
                        Toast.makeText(ExperienceDetail.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        long val = adpt.workexpinfo(add_org, add_post, add_working, add_dateFrom, add_date_to, add_role, r_id);
                        Toast.makeText(ExperienceDetail.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

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
