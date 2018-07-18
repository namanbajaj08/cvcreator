package com.naman.resumemaker.DetailedActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class TrainingDetail extends AppCompatActivity {

    private static final String TAG = "TrainingDetail";
    ImageView date_pick , date_picker;
    private EditText from , to;
    private DatePickerDialog.OnDateSetListener mDateSetListen ;
    private DatePickerDialog.OnDateSetListener mDateSetListener ;
    RegistrationAdapter adpt;
    Cursor cursor;
    EditText org , project , role , des;
    Button save;
    String getOrg ,getProject , getDes, getDateFrom ,getDateTo,getRole;
    String action;
    private DatePickerDialog mDatePicker;
    int year,month,day;
    int fr_year,fr_month,fr_day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail);

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
        org= (EditText)findViewById(R.id.tr_company);
        project = (EditText)findViewById(R.id.tr_project);
        des = (EditText)findViewById(R.id.tr_about_project);
        role =(EditText)findViewById(R.id.tr_role);
        save = (Button)findViewById(R.id.save);

        adpt = new RegistrationAdapter(getApplicationContext());
        cursor =adpt.querytraining(Util.training_id);
        if (cursor.moveToFirst()){
            do {
                getOrg = cursor.getString(1);
                getProject = cursor.getString(2);
                getDes = cursor.getString(3);
                getDateFrom = cursor.getString(4);
                getDateTo =cursor.getString(5);
                getRole = cursor.getString(6);
            } while (cursor.moveToNext());
        }

        if (getOrg!=null){
            org.setText(getOrg);
            org.setSelection(org.getText().length());
            project.setText(getProject);
            project.setSelection(project.getText().length());
            des.setText(getDes);
            des.setSelection(des.getText().length());
            from.setText(getDateFrom);
            from.setSelection(from.getText().length());
            to.setText(getDateTo);
            to.setSelection(to.getText().length());
            role.setText(getRole);
            role.setSelection(role.getText().length());

        }



        /*date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TrainingDetail.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                String fm=MONTHS[month];
                String date = fm+"-" + year;
                from.setText(date);
                from.setSelection(from.getText().length());
                from.setTextColor(Color.BLACK);
            }
        };

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TrainingDetail.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListen,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        mDateSetListen = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                String fm=MONTHS[month];
                String date = fm + "-" + year;
                to.setText(date);
                to.setSelection(to.getText().length());
                to.setTextColor(Color.BLACK);
            }
        };
*/

        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fr_year == 0 || fr_month == 0 || fr_day == 0) {
                    Calendar mcurrentDate=Calendar.getInstance();
                    fr_year=mcurrentDate.get(Calendar.YEAR);
                    fr_month=mcurrentDate.get(Calendar.MONTH);
                    fr_day=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                }

                DatePickerDialog mDatePicker=new DatePickerDialog(TrainingDetail.this,R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener()
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

                mDatePicker=new DatePickerDialog(TrainingDetail.this,  R.style.DialogTheme,new DatePickerDialog.OnDateSetListener()
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
                String add_project = project.getText().toString();
                String add_des = des.getText().toString();
                String add_dateFrom = from.getText().toString();
                String add_date_to = to.getText().toString();
                String add_role = role.getText().toString();

                int r_id = Util.resumeid;

                if (add_org.equals("") || add_des.equals("") || add_dateFrom.equals("") ||  add_date_to.equals("") || role.equals("")){
                    Toast.makeText(TrainingDetail.this, "Fields can not be blank" ,Toast.LENGTH_SHORT).show();
                }
                else if (getOrg != null){
                    long val = adpt.updatetraininginfo(add_org,add_project,add_des,add_dateFrom,add_date_to,add_role,r_id);
                    Toast.makeText(TrainingDetail.this , "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    long val = adpt.trainingInfo(add_org,add_project,add_des,add_dateFrom,add_date_to,add_role,r_id);
                    Toast.makeText(TrainingDetail.this , "Inserted successfully", Toast.LENGTH_SHORT).show();
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
