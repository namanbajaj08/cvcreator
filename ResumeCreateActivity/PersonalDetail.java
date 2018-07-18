package com.naman.resumemaker.ResumeCreateActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.CreateResume;
import com.naman.resumemaker.Crop_Image;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDetail extends AppCompatActivity {
    private static final String TAG = "PersonalDetail";
    ImageView date_pick;
    private EditText dob;
    private DatePickerDialog.OnDateSetListener mDateSetListener ;
    RegistrationAdapter reg;
    int rowId;
    String rowName;
    Cursor cursor;
    EditText father, mother, nationality, language;
    EditText mother_tongue, passport, address;
    RadioGroup gender;
    AppCompatRadioButton male , female;
    CircleImageView profile;
    ImageView edit;
    TextView u_name;
    Button save;
    String action;

    int screenHeight;
    int screenWidth;
    Uri selectedImagephoto;
    Bitmap bmp;
    String pathimage;
    String getFather, getMother, getDob, getGender, getNaitionality ;
    String getLanguage, getMtongue, getPassport, getAddress;
    String getimagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();

        getSupportActionBar().setTitle("Profile Info");

        edit = (ImageView) findViewById(R.id.edit_profile);
        profile = (CircleImageView) findViewById(R.id.profile_image);
        u_name = (TextView) findViewById(R.id.u_name);
        father = (EditText) findViewById(R.id.father);
        mother = (EditText) findViewById(R.id.mother);
        dob = (EditText) findViewById(R.id.dob);
        date_pick = (ImageView) findViewById(R.id.date_pick);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (AppCompatRadioButton) findViewById(R.id.male);
        female = (AppCompatRadioButton) findViewById(R.id.female);
        nationality = (EditText) findViewById(R.id.nationality);
        language = (EditText) findViewById(R.id.language);
        mother_tongue = (EditText) findViewById(R.id.mother_tongue);
        passport = (EditText) findViewById(R.id.passport);
        address = (EditText) findViewById(R.id.address);
        save = (Button) findViewById(R.id.save);

        u_name.setText(CreateResume.data);
        rowId = Util.resumeid;
        rowName = Util.resumename;
        reg = new RegistrationAdapter(this);
        ViewTreeObserver viewTree = profile.getViewTreeObserver();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
            }
        });

        try{
            cursor = reg.queryPersonal(Util.resumeid);
            if (cursor.moveToFirst()){
                do {
                    getimagepath = cursor.getString(1);
                    getFather = cursor.getString(2);
                    getMother = cursor.getString(3);
                    getDob = cursor.getString(4);
                    getGender = cursor.getString(5);
                    getNaitionality = cursor.getString(6);
                    getLanguage = cursor.getString(7);
                    getMtongue = cursor.getString(8);
                    getPassport = cursor.getString(9);
                    getAddress = cursor.getString(10);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(getimagepath != null){
            profile.setImageURI(Uri.parse(getimagepath));
            u_name.setText(CreateResume.data);
            father.setText(getFather);
            father.setSelection(father.getText().length());
            mother.setText(getMother);
            mother.setSelection(mother.getText().length());
            dob.setText(getDob);
            dob.setSelection(dob.getText().length());
            if (getGender.equals("Male")){
                male.setChecked(true);
            }
            else {
                female.setChecked(true);
            }
            nationality.setText(getNaitionality);
            nationality.setSelection(nationality.getText().length());
            language.setText(getLanguage);
            language.setSelection(language.getText().length());
            mother_tongue.setText(getMtongue);
            mother_tongue.setSelection(mother_tongue.getText().length());
            passport.setText(getPassport);
            passport.setSelection(passport.getText().length());
            address.setText(getAddress);
            address.setSelection(address.getText().length());
        }
        else  if (getFather !=null){
                u_name.setText(CreateResume.data);
                father.setText(getFather);
                father.setSelection(father.getText().length());
                mother.setText(getMother);
                mother.setSelection(mother.getText().length());
                dob.setText(getDob);
                dob.setSelection(dob.getText().length());
                if (getGender.equals("Male")) {
                    male.setChecked(true);
                }
                else {
                    female.setChecked(true);
                }
                nationality.setText(getNaitionality);
                nationality.setSelection(nationality.getText().length());
                language.setText(getLanguage);
                language.setSelection(language.getText().length());
                mother_tongue.setText(getMtongue);
                mother_tongue.setSelection(mother_tongue.getText().length());
                passport.setText(getPassport);
                passport.setSelection(passport.getText().length());
                address.setText(getAddress);
                address.setSelection(address.getText().length());
            }


        date_pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PersonalDetail.this,
                        R.style.DialogTheme,
                        mDateSetListener,
                        year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                String fm=MONTHS[month];
                String dm = "" + day;
                if (day < 10) {
                    dm = "0" + day;
                }
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = dm + "/" + fm + "/" + year;
                dob.setText(date);
                dob.setSelection(dob.getText().length());
                dob.setTextColor(Color.BLACK);
            }
        };


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_father = father.getText().toString();
                String add_mother = mother.getText().toString();
                String add_dob = dob.getText().toString();
                String add_gender = "";
                if (gender.getCheckedRadioButtonId()==R.id.male){
                    add_gender = "Male";
                }
                if (gender.getCheckedRadioButtonId()==R.id.female){
                    add_gender = "Female";
                }
                String add_nationality = nationality.getText().toString();
                String add_language = language.getText().toString();
                String add_mother_tongue = mother_tongue.getText().toString();
                String add_passport = passport.getText().toString();
                String add_address = address.getText().toString();
                int r_id = Util.resumeid;

                if (add_father.equals("") || add_mother.equals("") || add_dob.equals("") || add_nationality.equals("") || add_language.equals("") || add_mother_tongue.equals("")){
                    Toast.makeText(PersonalDetail.this , "Fields can not be blank" , Toast.LENGTH_SHORT).show();
                }
                else if (getFather !=null ){
                    long val =reg.updatepersonalDetail(Util.pathofimagae , add_father, add_mother, add_dob, add_gender, add_nationality, add_language, add_mother_tongue, add_passport, add_address, r_id);
                    Toast.makeText(PersonalDetail.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    rowId = Util.resumeid;
                    rowName =Util.resumename;
                    Intent intent = new Intent(PersonalDetail.this , CreateResume.class);
                    intent.putExtra("id",rowId);
                    intent.putExtra("name",rowName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();

                }
                else {
                    long val = reg.insertpersonaltDetail(Util.pathofimagae , add_father, add_mother, add_dob, add_gender, add_nationality, add_language, add_mother_tongue, add_passport, add_address, r_id);
                    Toast.makeText(PersonalDetail.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                    rowId = Util.resumeid;
                    rowName =Util.resumename;
                    Intent intent = new Intent(PersonalDetail.this , CreateResume.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null){
            selectedImagephoto = data.getData();
            Util.selectedImageUri =selectedImagephoto;
            pathimage = getRealPathFromURI(this , selectedImagephoto);
            Intent intent = new Intent(PersonalDetail.this , Crop_Image.class);
            intent.putExtra("isFromMain", true);
            startActivity(intent);

        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri, new String[]{"_data"}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            String string = cursor.getString(column_index);
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void savephoto() {
        String root = Environment.getExternalStorageDirectory() + "/Android/data/" + this.getPackageName();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "photo" + new Random().nextInt(100) + ".png";
        File file = new File(myDir, fname);
        this.pathimage = new StringBuilder(String.valueOf(root)).append("/").append(fname).toString();
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            this.bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        if (Util.pathofimagae != null && Util.flag) {
            this.profile.setImageURI(Uri.parse(Util.pathofimagae));
            Util.flag = false;
        }
    }

}
