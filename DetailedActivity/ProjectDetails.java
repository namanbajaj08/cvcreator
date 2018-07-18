package com.naman.resumemaker.DetailedActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

import java.util.Calendar;

public class ProjectDetails extends AppCompatActivity {

    private static final String TAG = "ProjectDetails";
    ImageView date_pick , date_picker;
    private EditText from , to;
    RegistrationAdapter adpt;
    Cursor cursor;
    EditText org , project , role , des;
    Button save;
    String getOrg ,getProject , getDes, getDateFrom ,getDateTo,getRole;
    private DatePickerDialog.OnDateSetListener mDateSetListen ;
    private DatePickerDialog.OnDateSetListener mDateSetListener ;
    String action;
    private DatePickerDialog mDatePicker;
    int year,month,day;
    int fr_year,fr_month,fr_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();




        from = (EditText)findViewById(R.id.date_from);
        to = (EditText)findViewById(R.id.date_to);
        date_pick=(ImageView)findViewById(R.id.date_pick);
        date_picker=(ImageView)findViewById(R.id.date_picker);
        org= (EditText)findViewById(R.id.company_name);
        project = (EditText)findViewById(R.id.project_name);
        des = (EditText)findViewById(R.id.project_description);
        role =(EditText)findViewById(R.id.project_roll);
        save = (Button)findViewById(R.id.save);

        adpt = new RegistrationAdapter(getApplicationContext());
        cursor =adpt.queryproject(Util.project_id);
        if (cursor.moveToFirst()){
            do {
                getProject = cursor.getString(1);
                getOrg = cursor.getString(2);
                getDes = cursor.getString(3);
                getDateFrom = cursor.getString(4);
                getDateTo =cursor.getString(5);
                getRole = cursor.getString(6);
            } while (cursor.moveToNext());
        }

        if (getProject!=null){
            project.setText(getProject);
            project.setSelection(project.getText().length());
            org.setText(getOrg);
            org.setSelection(org.getText().length());
            des.setText(getDes);
            des.setSelection(des.getText().length());
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

                DatePickerDialog mDatePicker=new DatePickerDialog(ProjectDetails.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
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

                mDatePicker=new DatePickerDialog(ProjectDetails.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener()
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
                String add_project = project.getText().toString();
                String add_org = org.getText().toString();
                String add_des = des.getText().toString();
                String add_dateFrom = from.getText().toString();
                String add_date_to = to.getText().toString();
                String add_role = role.getText().toString();

                int r_id = Util.resumeid;

                if ( add_project.equals("") || add_org.equals("") || add_des.equals("") || add_dateFrom.equals("") ||  add_date_to.equals("") || role.equals("")){
                    Toast.makeText(ProjectDetails.this, "Fields can not be blank" ,Toast.LENGTH_SHORT).show();
                }
                else if (getProject != null){
                    long val = adpt.updateprojectinfo(add_project,add_org,add_des,add_dateFrom,add_date_to,add_role,r_id);
                    Toast.makeText(ProjectDetails.this , "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    long val = adpt.projectInfo(add_project,add_org,add_des,add_dateFrom,add_date_to,add_role,r_id);
                    Toast.makeText(ProjectDetails.this , "Inserted successfully", Toast.LENGTH_SHORT).show();
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
