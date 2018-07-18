package com.naman.resumemaker.DetailedActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import java.util.Calendar;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;


public class AcademicDetail extends AppCompatActivity {

    RegistrationAdapter adpt;
    Cursor cursor;
    private static final String TAG = "AcademicDetail";
    String action;
    private DatePickerDialog.OnDateSetListener mDateSetListener ;
    EditText degree , college , specilaization , percentile;
    RadioGroup rg1 , rg2;
    AppCompatRadioButton complete , pursuing;
    AppCompatRadioButton percent ,cgpa;
    Button save;
    ImageView date_pick , date_picker;
    private EditText from , to;
    private DatePickerDialog.OnDateSetListener mDateSetListen ;
    String getdegree , getCollege , getSepcilaization , getPercentile, getComplition ;
    String  getPercentage , getDateFrom , getDateTo;
    private DatePickerDialog mDatePicker;
    int year,month,day;
    int fr_year,fr_month,fr_day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();


        degree = (EditText)findViewById(R.id.degree);
        college = (EditText)findViewById(R.id.university);
        specilaization = (EditText)findViewById(R.id.specialization);
        rg1 = (RadioGroup)findViewById(R.id.radio_grp);
        complete = (AppCompatRadioButton)findViewById(R.id.completed);
        pursuing = (AppCompatRadioButton)findViewById(R.id.pursuing);
        rg2 = (RadioGroup)findViewById(R.id.radio_percent);
        percent = (AppCompatRadioButton)findViewById(R.id.percentage);
        cgpa = (AppCompatRadioButton)findViewById(R.id.cgpa);
        percentile =(EditText)findViewById(R.id.percentile);
        from = (EditText)findViewById(R.id.date_from);
        to = (EditText)findViewById(R.id.date_to);
        date_pick=(ImageView)findViewById(R.id.date_pick);
        date_picker=(ImageView)findViewById(R.id.date_picker);
        save = (Button)findViewById(R.id.save);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.completed){
                    to.setEnabled(true);
                    date_picker.setEnabled(true);
                }

                if (i == R.id.pursuing){
                    to.setEnabled(false);
                    date_picker.setEnabled(false);
                    to.setText("");
                }
            }
        });

        adpt = new RegistrationAdapter(getApplicationContext());
        cursor =adpt.queryacademic1(Util.acdemic_id);
        if (cursor.moveToFirst()){

            do {
                getdegree = cursor.getString(1);
                getCollege = cursor.getString(2);
                getSepcilaization = cursor.getString(3);
                getComplition = cursor.getString(4);
                getPercentage = cursor.getString(5);
                getPercentile  =cursor.getString(6);
                getDateFrom = cursor.getString(7);
                getDateTo =cursor.getString(8);
            }while (cursor.moveToNext());
        }



        if (getdegree!=null){
            degree.setText(getdegree);
            degree.setSelection(degree.getText().length());
            college.setText(getCollege);
            college.setSelection(college.getText().length());
            specilaization.setText(getSepcilaization);
            specilaization.setSelection(specilaization.getText().length());
            if (getComplition.equals("Completed")){
                complete.setChecked(true);
            }
            else
            {
                pursuing.setChecked(true);
            }
            if (getPercentage.equals("%")){
                percent.setChecked(true);
            }
            else {
                cgpa.setChecked(true);
            }
            percentile.setText(getPercentile);
            percentile.setSelection(percentile.getText().length());
            from.setText(getDateFrom);
            from.setSelection(from.getText().length());
            to.setText(getDateTo);
            to.setSelection(to.getText().length());

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

                DatePickerDialog mDatePicker=new DatePickerDialog(AcademicDetail.this,R.style.DialogTheme,new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        fr_year = selectedyear;
                        fr_month = selectedmonth;
                        fr_day = selectedday;
                        final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        String fm = MONTHS[fr_month];
                        from.setText(new StringBuilder().append(fr_year));
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

                mDatePicker=new DatePickerDialog(AcademicDetail.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                        final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        String fm = MONTHS[month];
                        to.setText(new StringBuilder().append(year));
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
                String add_degree = degree.getText().toString();
                String add_college = college.getText().toString();
                String add_stream = specilaization.getText().toString();
                String add_completion = "";
                if (rg1.getCheckedRadioButtonId()== R.id.completed)
                    add_completion="Completed";
                if (rg1.getCheckedRadioButtonId()==R.id.pursuing)
                    add_completion="Pursuing";

                String add_percentage = "";
                if (rg2.getCheckedRadioButtonId()==R.id.percentage)
                    add_percentage="%";
                if (rg2.getCheckedRadioButtonId()==R.id.cgpa)
                    add_percentage=" CGPA";

                String add_percentile = percentile.getText().toString();

                String add_dateFrom = from.getText().toString();
                String add_date_to = to.getText().toString();

                int r_id = Util.resumeid;
                if (rg1.getCheckedRadioButtonId()==R.id.completed) {

                    if (add_degree.equals("") || add_college.equals("")  || add_dateFrom.equals("") || add_date_to.equals("")) {
                        Toast.makeText(AcademicDetail.this, "Fields can not be blank", Toast.LENGTH_SHORT).show();
                    } else if (getdegree != null) {
                        long val = adpt.updateacademic(add_degree, add_college, add_stream, add_completion, add_percentage, add_percentile, add_dateFrom, add_date_to, r_id);
                        Toast.makeText(AcademicDetail.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        long val = adpt.academicinfo(add_degree, add_college, add_stream, add_completion, add_percentage, add_percentile, add_dateFrom, add_date_to, r_id);
                        Toast.makeText(AcademicDetail.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }


                else {
                    if (add_degree.equals("") || add_college.equals("")|| add_dateFrom.equals("")) {
                        Toast.makeText(AcademicDetail.this, "Fields can not be blank", Toast.LENGTH_SHORT).show();
                    } else if (getdegree != null) {
                        long val = adpt.updateacademic(add_degree, add_college, add_stream, add_completion, add_percentage, add_percentile, add_dateFrom, add_date_to, r_id);
                        Toast.makeText(AcademicDetail.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        long val = adpt.academicinfo(add_degree, add_college, add_stream, add_completion, add_percentage, add_percentile, add_dateFrom, add_date_to, r_id);
                        Toast.makeText(AcademicDetail.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
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
