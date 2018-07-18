package com.naman.resumemaker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.lowagie.text.Cell;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.ResumeCreateActivity.StrengthActivty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.width;
import static com.naman.resumemaker.R.attr.height;

public class GeneratePdf extends AppCompatActivity {

    RegistrationAdapter adpt;
    Cursor cursor;
    int rowId;
    String name, email, contact,link;
    String carrier_obj;
    List projectList;
    ListView mListView;
    String address,dob,gender,nationality,language,mother_ton,father,mother,passport;
    String declaration;
    SQLiteDatabase db;
    String organization;

    int image_formate;
    String formate_name;
    String action;
    String pdfName ;
    int REQUEST_PERMISSIONS;
    Boolean boolean_permission = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pdf);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView show= (TextView)findViewById(R.id.show_formate);
        toolbar.setContentInsetsAbsolute(0,0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        action = getIntent().getAction();
        formate_name = getIntent().getExtras().getString("formate_name");
        show.setText(formate_name);

        pdfName = CreateResume.data;

        Intent i = getIntent();

        image_formate = i.getExtras().getInt("img_formate");

        ImageView imageView = (ImageView)findViewById(R.id.view_resume);
        imageView.setImageResource(image_formate);

        rowId =Util.resumeid;



        Button btn_print=(Button)findViewById(R.id.button1);
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (formate_name.equals("Format 1")){
                    createPdf1();
                }
                else if (formate_name.equals("Format 2")){
                    createPdf2();
                }
                else if (formate_name.equals("Format 3")){
                    createPdf3();
                }
                else if (formate_name.equals("Format 4")){
                    createPdf4();
                }
                else if (formate_name.equals("Format 5")){
                    createPdf5();
                }
                else if (formate_name.equals("Format 6")){
                    createPdf6();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Format Found" , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void createPdf1() {
        // TODO Auto-generated method stub
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CV Creator";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            Toast.makeText(this, "Resume Generated at" + path, Toast.LENGTH_LONG).show();

            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File file = new File(dir, pdfName + "-" + timestamp + ".pdf");
            OutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();

            Font paraFont = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFont5 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);


            Paragraph p1 = new Paragraph();
            Font paraFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.BOLD, CMYKColor.BLACK);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont1);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {

                    name = cursor.getString(1);
                    p1.add(name + "\n");


                }
                while (cursor.moveToNext());

            }
            document.add(p1);

            Paragraph p2 = new Paragraph();
            Font paraFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, CMYKColor.BLACK);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont2);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {
                    link = cursor.getString(4);
                    email = cursor.getString(2);
                    contact = cursor.getString(3);

                    p2.add(link + "\n");
                    p2.add(email + " | ");
                    p2.add(contact);


                }
                while (cursor.moveToNext());

            }
            document.add(p2);
            Chunk linebreak = new Chunk(new LineSeparator(1f, 100f, CMYKColor.BLACK, Element.ALIGN_CENTER, -1));
            document.add(linebreak);



            adpt = new RegistrationAdapter(this);
            cursor = adpt.querynameobjecticveedt(rowId);
            if (this.cursor.getCount() > 0)
            {
                Paragraph p3 = new Paragraph("Career Objective:\n", paraFont);
                p3.setFont(paraFont5);
                cursor = adpt.querynameobjecticveedt(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        carrier_obj = cursor.getString(1);
                        p3.add(carrier_obj);
                    } while (cursor.moveToNext());
                }
                document.add(p3);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAcademicList(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p4 = new Paragraph("Academic Records:\n", paraFont);
                p4.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from ACADEMIC_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String compelition = cursor.getString(4);
                        String degree = cursor.getString(1);
                        String college = cursor.getString(2);
                        String stream = cursor.getString(3);
                        String percent = cursor.getString(5);
                        String percentage = cursor.getString(6);
                        String yr_from = cursor.getString(7);
                        String yr_to = cursor.getString(8);

                        p4.add("\u2022");
                        p4.add(" " + compelition + " " + degree + " from " + college + " ");
                        if (stream.equals("")) {
                            if (compelition.equals("Completed")){
                                if (percentage.equals("")){
                                    p4.add("("+yr_from+"-Present).\n");
                                }
                                else {
                                    p4.add("with " + percentage + " " + percent + " in the year " + yr_from + "-");
                                    p4.add(yr_to + ".\n");
                                }
                            }
                            else {
                                if (percentage.equals("")){
                                p4.add("("+yr_from+"-Present).\n");
                                }
                                else {
                                    p4.add("with " + percentage + " " + percent + " (" + yr_from + "-");
                                    p4.add("Present).\n");
                                }
                            }

                        } else {
                            p4.add("in stream of " + stream + " ");
                            if (compelition.equals("Completed")){
                                if (percentage.equals("")){
                                    p4.add("("+yr_from+"-Present).\n");
                                }
                                else {
                                    p4.add("with " + percentage + " " + percent + " in the year " + yr_from + "-");
                                    p4.add(yr_to + ".\n");
                                }
                            }
                            else {
                                if (percentage.equals("")){
                                    p4.add("("+yr_from+"-Present).\n");
                                }
                                else {
                                    p4.add("with " + percentage + " " + percent + " (" + yr_from + "-");
                                    p4.add("Present).\n");
                                }
                            }
                        }
                    } while (cursor.moveToNext());
                }
                document.add(p4);
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryName2(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p5 = new Paragraph("Working Experience:\n", paraFont);
                p5.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String working = cursor.getString(3);
                        organization = cursor.getString(1);
                        String designation = cursor.getString(2);
                        String wr_yr_from = cursor.getString(4);
                        String wr_yr_to = cursor.getString(5);
                        String wr_role = cursor.getString(6);
                        p5.add("\u2022");
                        p5.add(" " + working + " working in " + organization + " as an " + designation + " from " + wr_yr_from );
                        if (working.equals("Previously")){
                            p5.add(" to " +wr_yr_to + ".\n");
                        }
                        else {
                            p5.add(".\n");
                        }
                    } while (cursor.moveToNext());
                }
                document.add(p5);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p6 = new Paragraph("Technical Details:\n", paraFont);

                p6.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  TECHNICAL where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String technical = cursor.getString(1);
                        p6.add(technical);
                        if (!cursor.isLast()) {
                            p6.add(", ");

                        }

                    } while (cursor.moveToNext());
                }
                document.add(p6);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameTraining(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p7 = new Paragraph("Training Details:\n", paraFont);
                p7.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TRAINING where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String tr_org = cursor.getString(1);
                        String tr_project = cursor.getString(2);
                        String tr_date_from = cursor.getString(4);
                        String tr_date_to = cursor.getString(5);
                        String tr_des = cursor.getString(3);
                        String tr_role = cursor.getString(6);
                        p7.setFont(paraFont2);
                        p7.add(tr_org + ": ");
                        p7.setFont(paraFont2);
                        p7.add(tr_project);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p7.setFont(paraFont5);
                        p7.add(new Chunk(glue));
                        p7.add(tr_date_from + "-" + tr_date_to + "\n");
                        p7.setAlignment(Paragraph.ALIGN_LEFT);
                        p7.setFont(paraFont5);
                        p7.add(tr_des + "\n");
                        if (!tr_role.equals("")) {
                            p7.add("Role: " + tr_role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                document.add(p7);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameProject(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p8 = new Paragraph("Project Details:\n", paraFont);
                p8.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TAB_PROJECT where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String project = cursor.getString(1);
                        String org = cursor.getString(2);
                        String date_from = cursor.getString(4);
                        String date_to = cursor.getString(5);
                        String des = cursor.getString(3);
                        String role = cursor.getString(6);
                        p8.setFont(paraFont2);
                        p8.add(project + ": ");
                        p8.setFont(paraFont2);
                        p8.add(org);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p8.setFont(paraFont5);
                        p8.add(new Chunk(glue));
                        p8.add(date_from + "-" + date_to + "\n");
                        p8.setAlignment(Paragraph.ALIGN_LEFT);
                        p8.setFont(paraFont5);
                        p8.add(des + "\n");
                        if (!role.equals("")) {
                            p8.add("Role: " + role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                document.add(p8);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRewards(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p9 = new Paragraph("Achievements and Rewards:\n", paraFont);
                p9.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  REWARDS where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String rewards = cursor.getString(1);
                        p9.add("\u2022");
                        p9.add(" " + rewards + "\n");

                    } while (cursor.moveToNext());
                }
                document.add(p9);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameExtra(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p10 = new Paragraph("Extracurricular Activites:\n", paraFont);
                p10.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  EXTRA_CURRICULAR where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String extra = cursor.getString(1);
                        p10.add("\u2022");
                        p10.add(" " + extra + "\n");

                    } while (cursor.moveToNext());
                }
                document.add(p10);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryHobby(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p11 = new Paragraph("Hobbies:\n", paraFont);
                p11.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  HOBBIES where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String hobbies = cursor.getString(1);
                        p11.add(hobbies);
                        if (!cursor.isLast()){
                            p11.add(", ");
                        }
                    } while (cursor.moveToNext());
                }
                document.add(p11);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryStrength(rowId);
            if (this.cursor.getCount() > 0)
            {
                Paragraph p12 = new Paragraph("Strength:\n", paraFont);
                p12.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  STRENGTH where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                    String strength = cursor.getString(1);
                    p12.add(strength);
                    if (!cursor.isLast()){
                        p12.add(", ");
                    }
                    } while (cursor.moveToNext());
                    }
                document.add(p12);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p13 = new Paragraph("Personal Details:\n", paraFont);
                p13.setFont(paraFont5);
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        address = cursor.getString(10);
                        dob = cursor.getString(4);
                        gender = cursor.getString(5);
                        father = cursor.getString(2);
                        mother = cursor.getString(3);
                        nationality = cursor.getString(6);
                        language = cursor.getString(7);
                        mother_ton = cursor.getString(8);
                        passport = cursor.getString(9);

                        if (address.equals("")) {
                            p13.add("Date of Birth        : " + dob + "\n");
                            p13.add("Gender                 : " + gender + "\n");
                            p13.add("Father's Name      : " + father + "\n");
                            p13.add("Mother's Name    : " + mother + "\n");
                            p13.add("Nationality           : " + nationality + "\n");
                            p13.add("Language             : " + language + "\n");
                            p13.add("Mother Tongue    : " + mother_ton + "\n");
                            if (!passport.equals("")) {
                                p13.add("Passport               : " + passport);
                            }
                        }
                        else {
                            p13.add("Address                : " + address + "\n");
                            p13.add("Date of Birth        : " + dob + "\n");
                            p13.add("Gender                 : " + gender + "\n");
                            p13.add("Father's Name      : " + father + "\n");
                            p13.add("Mother's Name    : " + mother + "\n");
                            p13.add("Nationality           : " + nationality + "\n");
                            p13.add("Language             : " + language + "\n");
                            p13.add("Mother Tongue    : " + mother_ton + "\n");
                            if (!passport.equals("")) {
                                p13.add("Passport               : " + passport);
                            }
                        }


                    } while (cursor.moveToNext());
                }
                document.add(p13);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRefer(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p14 = new Paragraph("References:\n", paraFont);
                p14.setFont(paraFont5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from REFER where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String re_name = cursor.getString(1);
                        String re_des = cursor.getString(2);
                        String re_org = cursor.getString(3);
                        String re_number = cursor.getString(4);
                        p14.add("\u2022");
                        p14.add(" " + re_name + "-" + re_des + ", " + re_org + " - " + re_number + "\n");
                    } while (cursor.moveToNext());
                }
                document.add(p14);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryDecleartion(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p15 = new Paragraph("Declaration:\n", paraFont);
                p15.setFont(paraFont5);
                cursor = adpt.queryDecleartion(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        declaration = cursor.getString(1);
                        p15.add(declaration);

                    } while (cursor.moveToNext());
                }
                document.add(p15);
            }

            Paragraph p16  = new Paragraph();
            Font paraFont16= new Font(Font.FontFamily.TIMES_ROMAN,14.0f,Font.BOLD, CMYKColor.BLACK);
            p16.setAlignment(Paragraph.ALIGN_LEFT);
            p16.setFont(paraFont16);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()){
                do {

                    name = cursor.getString(1);
                    p16.add("\nDate:");
                    p16.add("\nPlace:");
                    Chunk glue = new Chunk(new VerticalPositionMark());
                    p16.add(new Chunk(glue));
                    p16.add(name);



                }
                while (cursor.moveToNext());

            }
            document.add(p16);




              /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.RIGHT);

            //add image to document
            document.add(myImg);
*/



        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
            Intent intent = new Intent(GeneratePdf.this,ShowResume.class);
            startActivity(intent);

        }

    }

    private void createPdf2() {
        // TODO Auto-generated method stub
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CV Creator";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            Toast.makeText(this, "Resume Generated at" + path, Toast.LENGTH_LONG).show();

            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File file = new File(dir, pdfName + "-" + timestamp + ".pdf");
            OutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();


            Font paraFont = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFont5 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);
            Font paraFontSpace = new Font(Font.FontFamily.TIMES_ROMAN, 1.0f, 0, CMYKColor.BLACK);
            Font paraFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.ITALIC, CMYKColor.BLACK);
            Font paraFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, CMYKColor.BLACK);


            Paragraph p1 = new Paragraph();
            Font paraFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.BOLD, CMYKColor.BLACK);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont2);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {

                    name = cursor.getString(1);
                    p1.add(name + "\n");


                }
                while (cursor.moveToNext());

            }
            document.add(p1);

            Paragraph p2 = new Paragraph();
            Font paraFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont3);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {
                    email = cursor.getString(2);
                    contact = cursor.getString(3);
                    link = cursor.getString(4);


                    p2.add(email + "\n");
                    p2.add(contact + "\n");
                    p2.add(link);
                }
                while (cursor.moveToNext());

            }
            document.add(p2);
            Chunk linebreak = new Chunk(new LineSeparator(2.5f, 100f, CMYKColor.BLACK, Element.ALIGN_CENTER, -1));
            document.add(linebreak);



            adpt = new RegistrationAdapter(this);
            cursor = adpt.querynameobjecticveedt(rowId);
            if (this.cursor.getCount() > 0)
            {
                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Career Objective:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);

                Paragraph p3 = new Paragraph();
                p3.setFont(paraFont5);
                p3.setSpacingAfter(10f);
                p3.setSpacingBefore(5f);
                cursor = adpt.querynameobjecticveedt(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        carrier_obj = cursor.getString(1);

                        p3.add(carrier_obj);
                        p3.setIndentationLeft(5);

                    } while (cursor.moveToNext());


                }
                document.add(p3);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAcademicList(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Academic Records:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                p.setSpacingAfter(5f);
                document.add(p);


                PdfPTable p4 = new PdfPTable(4);
                p4.setWidths(new int[]{2,2,1,1});
                p4.setWidthPercentage(95);
                p4.setSpacingBefore(5f);
                p4.setSpacingAfter(10f);
                PdfPCell c1 = new PdfPCell(new Phrase("Education",paraFont1));
                c1.setPadding(3);
                c1.setPaddingBottom(5);
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell c2 = new PdfPCell(new Phrase("Board/University",paraFont1));
                c2.setPadding(3);
                c2.setPaddingBottom(5);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell c3 = new PdfPCell(new Phrase("Percentage",paraFont1));
                c3.setPadding(3);
                c3.setPaddingBottom(5);
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell c4 = new PdfPCell(new Phrase("Year",paraFont1));
                c4.setPadding(3);
                c4.setPaddingBottom(5);
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                p4.addCell(c1);
                p4.addCell(c2);
                p4.addCell(c3);
                p4.addCell(c4);

                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from ACADEMIC_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String degree = cursor.getString(1);
                        String college = cursor.getString(2);
                        String compelition = cursor.getString(4);
                        String percent = cursor.getString(5);
                        String percentage = cursor.getString(6);
                        String yr_from = cursor.getString(7);
                        String yr_to = cursor.getString(8);

                        PdfPCell c5 = new PdfPCell(new Phrase(degree,paraFont5));
                        c5.setPadding(3);
                        c5.setPaddingBottom(5);
                        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c6 = new PdfPCell(new Phrase(college,paraFont5));
                        c6.setPadding(3);
                        c6.setPaddingBottom(5);
                        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c7;
                        if (percentage.equals("")){
                            c7 = new PdfPCell(new Phrase("-", paraFont5));
                        }
                        else {
                            c7 = new PdfPCell(new Phrase(percentage + percent, paraFont5));
                        }
                        c7.setPadding(3);
                        c7.setPaddingBottom(5);
                        c7.setHorizontalAlignment(Element.ALIGN_CENTER);
                        if (compelition.equals("Completed")) {
                            PdfPCell c8 = new PdfPCell(new Phrase(yr_from + "-" + yr_to, paraFont5));
                            c8.setPadding(3);
                            c8.setPaddingBottom(5);
                            c8.setHorizontalAlignment(Element.ALIGN_CENTER);
                            p4.addCell(c5);
                            p4.addCell(c6);
                            p4.addCell(c7);
                            p4.addCell(c8);
                        }
                        else {
                            PdfPCell c8 = new PdfPCell(new Phrase(yr_from + "-Present" , paraFont5));
                            c8.setPadding(3);
                            c8.setPaddingBottom(5);
                            c8.setHorizontalAlignment(Element.ALIGN_CENTER);
                            p4.addCell(c5);
                            p4.addCell(c6);
                            p4.addCell(c7);
                            p4.addCell(c8);
                        }
                    } while (cursor.moveToNext());

                }

                document.add(p4);
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryName2(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Working Experience:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);

                Paragraph p5 = new Paragraph();
                p5.setFont(paraFont5);
                p5.setSpacingAfter(10f);
                p5.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String working = cursor.getString(3);
                        organization = cursor.getString(1);
                        String designation = cursor.getString(2);
                        String wr_yr_from = cursor.getString(4);
                        String wr_yr_to = cursor.getString(5);
                        String wr_role = cursor.getString(6);
                        p5.setFont(paraFont1);
                        p5.add(organization +"\n");
                        p5.setFont(paraFontItalic);
                        p5.add(designation +", ");
                        p5.setFont(paraFont5);
                        p5.add(wr_yr_from + " - ");
                        if (working.equals("Currently")){
                            p5.add("Present\n");
                        }
                        else {
                            p5.add(wr_yr_to +"\n");
                        }
                        p5.add(wr_role +"\n");

                    } while (cursor.moveToNext());
                }
                document.add(p5);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Technical Details:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);


                Paragraph p6 = new Paragraph();
                p6.setFont(paraFont5);
                p6.setSpacingAfter(10f);
                p6.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  TECHNICAL where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String technical = cursor.getString(1);
                        p6.add(technical);

                        if (!cursor.isLast()) {
                            p6.add(", ");
                        }

                    } while (cursor.moveToNext());
                }
                document.add(p6);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameTraining(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Training Details:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);


                Paragraph p7 = new Paragraph();
                p7.setFont(paraFont5);
                p7.setSpacingAfter(10f);
                p7.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TRAINING where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String tr_org = cursor.getString(1);
                        String tr_project = cursor.getString(2);
                        String tr_date_from = cursor.getString(4);
                        String tr_date_to = cursor.getString(5);
                        String tr_des = cursor.getString(3);
                        String tr_role = cursor.getString(6);
                        p7.setFont(paraFont1);
                        p7.add(tr_org + ": ");
                        p7.setFont(paraFont1);
                        p7.add(tr_project);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p7.setFont(paraFont5);
                        p7.add(new Chunk(glue));
                        p7.add(tr_date_from + "-" + tr_date_to + "\n");
                        p7.setAlignment(Paragraph.ALIGN_LEFT);
                        p7.setFont(paraFont5);
                        p7.add(tr_des + "\n");
                        if (!tr_role.equals("")) {
                            p7.add("Role: " + tr_role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                document.add(p7);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameProject(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Project Details:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);


                Paragraph p8 = new Paragraph();
                p8.setFont(paraFont5);
                p8.setSpacingAfter(10f);
                p8.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TAB_PROJECT where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String project = cursor.getString(1);
                        String org = cursor.getString(2);
                        String date_from = cursor.getString(4);
                        String date_to = cursor.getString(5);
                        String des = cursor.getString(3);
                        String role = cursor.getString(6);
                        p8.setFont(paraFont1);
                        p8.add(project + ": ");
                        p8.setFont(paraFont1);
                        p8.add(org);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p8.setFont(paraFont5);
                        p8.add(new Chunk(glue));
                        p8.add(date_from + "-" + date_to + "\n");
                        p8.setAlignment(Paragraph.ALIGN_LEFT);
                        p8.setFont(paraFont5);
                        p8.add(des + "\n");
                        if (!role.equals("")) {
                            p8.add("Role: " + role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                document.add(p8);
            }



            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRewards(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Achievements and Rewards:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);


                Paragraph p9 = new Paragraph();
                p9.setFont(paraFont5);
                p9.setSpacingAfter(10f);
                p9.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  REWARDS where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String rewards = cursor.getString(1);
                        p9.add("\u2022");
                        p9.add(" " + rewards + "\n");

                    } while (cursor.moveToNext());
                }
                document.add(p9);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameExtra(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Extracurricular Activites:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);


                Paragraph p10 = new Paragraph();
                p10.setFont(paraFont5);
                p10.setSpacingAfter(10f);
                p10.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  EXTRA_CURRICULAR where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String extra = cursor.getString(1);
                        p10.add("\u2022");
                        p10.add(" " + extra + "\n");

                    } while (cursor.moveToNext());
                }
                document.add(p10);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryHobby(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Hobbies:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);


                Paragraph p11 = new Paragraph();
                p11.setFont(paraFont5);
                p11.setSpacingAfter(10f);
                p11.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  HOBBIES where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String hobbies = cursor.getString(1);
                        p11.add(hobbies);
                        if (!cursor.isLast()){
                            p11.add(", ");
                        }
                    } while (cursor.moveToNext());
                }
                document.add(p11);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryStrength(rowId);
            if (this.cursor.getCount() > 0)
            {
                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Strength:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);

                Paragraph p12 = new Paragraph();
                p12.setFont(paraFont5);
                p12.setSpacingAfter(10f);
                p12.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  STRENGTH where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String strength = cursor.getString(1);
                        p12.add(strength);
                        if (!cursor.isLast()){
                            p12.add(", ");
                        }
                    } while (cursor.moveToNext());
                }
                document.add(p12);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {
                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Personal Details::\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);

                Paragraph p13 = new Paragraph();
                p13.setFont(paraFont5);
                p13.setSpacingAfter(10f);
                p13.setSpacingBefore(5f);
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        address = cursor.getString(10);
                        dob = cursor.getString(4);
                        gender = cursor.getString(5);
                        father = cursor.getString(2);
                        mother = cursor.getString(3);
                        nationality = cursor.getString(6);
                        language = cursor.getString(7);
                        mother_ton = cursor.getString(8);
                        passport = cursor.getString(9);

                        if (address.equals("")) {
                            p13.add("Date of Birth        : " + dob + "\n");
                            p13.add("Gender                 : " + gender + "\n");
                            p13.add("Father's Name      : " + father + "\n");
                            p13.add("Mother's Name    : " + mother + "\n");
                            p13.add("Nationality           : " + nationality + "\n");
                            p13.add("Language             : " + language + "\n");
                            p13.add("Mother Tongue    : " + mother_ton + "\n");
                            if (!passport.equals("")) {
                                p13.add("Passport               : " + passport);
                            }
                        }
                        else {
                            p13.add("Address                : " + address + "\n");
                            p13.add("Date of Birth        : " + dob + "\n");
                            p13.add("Gender                 : " + gender + "\n");
                            p13.add("Father's Name      : " + father + "\n");
                            p13.add("Mother's Name    : " + mother + "\n");
                            p13.add("Nationality           : " + nationality + "\n");
                            p13.add("Language             : " + language + "\n");
                            p13.add("Mother Tongue    : " + mother_ton + "\n");
                            if (!passport.equals("")) {
                                p13.add("Passport               : " + passport);
                            }
                        }

                    } while (cursor.moveToNext());
                }
                document.add(p13);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRefer(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("References:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);

                Paragraph p14 = new Paragraph();
                p14.setFont(paraFont5);
                p14.setSpacingAfter(10f);
                p14.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from REFER where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String re_name = cursor.getString(1);
                        String re_des = cursor.getString(2);
                        String re_org = cursor.getString(3);
                        String re_number = cursor.getString(4);
                        p14.add("\u2022");
                        p14.add(" " + re_name + "-" + re_des + ", " + re_org + " - " + re_number + "\n");
                    } while (cursor.moveToNext());
                }
                document.add(p14);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryDecleartion(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p = new Paragraph();
                Chunk c = new Chunk("Declaration:\n" , paraFont);
                p.add(c);
                p.setSpacingBefore(5f);
                document.add(p);

                Paragraph p15 = new Paragraph();
                p15.setFont(paraFont5);
                p15.setSpacingAfter(5f);
                p15.setSpacingBefore(5f);
                cursor = adpt.queryDecleartion(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        declaration = cursor.getString(1);
                        p15.add(declaration);

                    } while (cursor.moveToNext());
                }
                document.add(p15);
            }

            Paragraph p16  = new Paragraph();
            Font paraFont16= new Font(Font.FontFamily.TIMES_ROMAN,14.0f,Font.BOLD, CMYKColor.BLACK);
            p16.setAlignment(Paragraph.ALIGN_LEFT);
            p16.setFont(paraFont16);
            p16.setSpacingBefore(5f);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()){
                do {

                    name = cursor.getString(1);
                    p16.add("Date:");
                    p16.add("\nPlace:");
                    Chunk glue = new Chunk(new VerticalPositionMark());
                    p16.add(new Chunk(glue));
                    p16.add(name);



                }
                while (cursor.moveToNext());

            }
            document.add(p16);



              /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.RIGHT);

            //add image to document
            document.add(myImg);
*/



        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
            Intent intent = new Intent(GeneratePdf.this,ShowResume.class);
            startActivity(intent);

        }

    }



    private void createPdf3() {
        // TODO Auto-generated method stub
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CV Creator";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            Toast.makeText(this, "Resume Generated at" + path, Toast.LENGTH_LONG).show();

            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File file = new File(dir, pdfName + "-" + timestamp + ".pdf");
            OutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();

            Rectangle rect= new Rectangle(577,825,18,15);
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorder(2);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(2);
            rect.setBorderColor(BaseColor.BLACK);
            document.add(rect);

            Font paraFont = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFont5 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);
            Font paraFontSpace = new Font(Font.FontFamily.TIMES_ROMAN, 1.0f, 0, CMYKColor.BLACK);
            Font paraFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.ITALIC, CMYKColor.BLACK);
            Font paraFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFontWhite = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.WHITE);

            Paragraph p1 = new Paragraph();
            Font paraFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.BOLD, CMYKColor.BLACK);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont2);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {

                    name = cursor.getString(1);
                    p1.add(name + "\n");


                }
                while (cursor.moveToNext());

            }
            document.add(p1);

            Paragraph p2 = new Paragraph();
            Font paraFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont3);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {
                    email = cursor.getString(2);
                    contact = cursor.getString(3);
                    link = cursor.getString(4);


                    p2.add(email + "\n");
                    p2.add(contact + "\n");
                    p2.add(link);



                }
                while (cursor.moveToNext());

            }
            document.add(p2);
            Chunk linebreak = new Chunk(new LineSeparator(2f, 100f, CMYKColor.BLACK, Element.ALIGN_CENTER, -1));
            document.add(linebreak);



            adpt = new RegistrationAdapter(this);
            cursor = adpt.querynameobjecticveedt(rowId);
            if (this.cursor.getCount() > 0)
            {

               /* Chunk c = new Chunk("Career Objective :\n" , paraFont);
                c.setBackground(BaseColor.LIGHT_GRAY);*/
                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Career Objective:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table.addCell(cell);
                table.setWidthPercentage(100);
                document.add(table);

                Paragraph p3 = new Paragraph();
                p3.setFont(paraFont5);
                p3.setSpacingAfter(10f);
                p3.setSpacingBefore(5f);
                p3.setIndentationLeft(5);
                cursor = adpt.querynameobjecticveedt(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        carrier_obj = cursor.getString(1);

                        p3.add(carrier_obj);
                        p3.setIndentationLeft(5);

                    } while (cursor.moveToNext());


                }
                document.add(p3);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAcademicList(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Academic Records:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);

                table1.setWidthPercentage(100);
                document.add(table1);


                PdfPTable p4 = new PdfPTable(4);
                p4.setWidths(new int[]{2,2,1,1});
                p4.setWidthPercentage(95);
                p4.setSpacingAfter(10f);
                p4.setSpacingBefore(10f);
                PdfPCell c1 = new PdfPCell(new Phrase("Education",paraFont1));
                c1.setPadding(3);
                c1.setPaddingBottom(5);
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell c2 = new PdfPCell(new Phrase("Board/University",paraFont1));
                c2.setPadding(3);
                c2.setPaddingBottom(5);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell c3 = new PdfPCell(new Phrase("Percentage",paraFont1));
                c3.setPadding(3);
                c3.setPaddingBottom(5);
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell c4 = new PdfPCell(new Phrase("Year",paraFont1));
                c4.setPadding(3);
                c4.setPaddingBottom(5);
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                p4.addCell(c1);
                p4.addCell(c2);
                p4.addCell(c3);
                p4.addCell(c4);

                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from ACADEMIC_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String degree = cursor.getString(1);
                        String college = cursor.getString(2);
                        String compelition = cursor.getString(4);
                        String percent = cursor.getString(5);
                        String percentage = cursor.getString(6);
                        String yr_from = cursor.getString(7);
                        String yr_to = cursor.getString(8);

                        PdfPCell c5 = new PdfPCell(new Phrase(degree,paraFont5));
                        c5.setPadding(3);
                        c5.setPaddingBottom(5);
                        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c6 = new PdfPCell(new Phrase(college,paraFont5));
                        c6.setPadding(3);
                        c6.setPaddingBottom(5);
                        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c7;
                        if (percentage.equals("")){
                            c7 = new PdfPCell(new Phrase("-", paraFont5));
                        }
                        else {
                             c7 = new PdfPCell(new Phrase(percentage + percent, paraFont5));
                        }
                        c7.setPadding(3);
                        c7.setPaddingBottom(5);
                        c7.setHorizontalAlignment(Element.ALIGN_CENTER);
                        if (compelition.equals("Completed")) {
                            PdfPCell c8 = new PdfPCell(new Phrase(yr_from + "-" + yr_to, paraFont5));
                            c8.setPadding(3);
                            c8.setPaddingBottom(5);
                            c8.setHorizontalAlignment(Element.ALIGN_CENTER);
                            p4.addCell(c5);
                            p4.addCell(c6);
                            p4.addCell(c7);
                            p4.addCell(c8);
                        }
                        else {
                            PdfPCell c8 = new PdfPCell(new Phrase(yr_from + "-Present" , paraFont5));
                            c8.setPadding(3);
                            c8.setPaddingBottom(5);
                            c8.setHorizontalAlignment(Element.ALIGN_CENTER);
                            p4.addCell(c5);
                            p4.addCell(c6);
                            p4.addCell(c7);
                            p4.addCell(c8);
                        }

                    } while (cursor.moveToNext());

                }

                document.add(p4);
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryName2(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Working Experience:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p5 = new Paragraph();
                p5.setFont(paraFont5);
                p5.setSpacingAfter(10f);
                p5.setSpacingBefore(5f);
                p5.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String working = cursor.getString(3);
                        organization = cursor.getString(1);
                        String designation = cursor.getString(2);
                        String wr_yr_from = cursor.getString(4);
                        String wr_yr_to = cursor.getString(5);
                        String wr_role = cursor.getString(6);
                        p5.setFont(paraFont1);
                        p5.add(organization +"\n");
                        p5.setFont(paraFontItalic);
                        p5.add(designation +", ");
                        p5.setFont(paraFont5);
                        p5.add(wr_yr_from + " - ");
                        if (working.equals("Currently")){
                            p5.add("Present\n");
                        }
                        else {
                            p5.add(wr_yr_to +"\n");
                        }
                        p5.add(wr_role +"\n");

                    } while (cursor.moveToNext());
                }
                document.add(p5);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Technical Details:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p6 = new Paragraph();
                p6.setFont(paraFont5);
                p6.setSpacingAfter(10f);
                p6.setSpacingBefore(5f);
                p6.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  TECHNICAL where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String technical = cursor.getString(1);
                        p6.add(technical);

                        if (!cursor.isLast()) {
                            p6.add(", ");
                        }

                    } while (cursor.moveToNext());
                }
                document.add(p6);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameTraining(rowId);
            if (this.cursor.getCount() > 0) {
                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Training Details:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p7 = new Paragraph();
                p7.setFont(paraFont5);
                p7.setSpacingAfter(10f);
                p7.setSpacingBefore(5f);
                p7.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TRAINING where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String tr_org = cursor.getString(1);
                        String tr_project = cursor.getString(2);
                        String tr_date_from = cursor.getString(4);
                        String tr_date_to = cursor.getString(5);
                        String tr_des = cursor.getString(3);
                        String tr_role = cursor.getString(6);
                        p7.setFont(paraFont1);
                        p7.add(tr_org + ": ");
                        p7.setFont(paraFont1);
                        p7.add(tr_project);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p7.setFont(paraFont5);
                        p7.add(new Chunk(glue));
                        p7.add(tr_date_from + "-" + tr_date_to + "\n");
                        p7.setAlignment(Paragraph.ALIGN_LEFT);
                        p7.setFont(paraFont5);
                        p7.add(tr_des + "\n");
                        if (!tr_role.equals("")) {
                            p7.add("Role: " + tr_role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                document.add(p7);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameProject(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Project Details:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p8 = new Paragraph();
                p8.setFont(paraFont5);
                p8.setSpacingAfter(10f);
                p8.setSpacingBefore(5f);
                p8.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TAB_PROJECT where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String project = cursor.getString(1);
                        String org = cursor.getString(2);
                        String date_from = cursor.getString(4);
                        String date_to = cursor.getString(5);
                        String des = cursor.getString(3);
                        String role = cursor.getString(6);
                        p8.setFont(paraFont1);
                        p8.add(project + ": ");
                        p8.setFont(paraFont1);
                        p8.add(org);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p8.setFont(paraFont5);
                        p8.add(new Chunk(glue));
                        p8.add(date_from + "-" + date_to + "\n");
                        p8.setAlignment(Paragraph.ALIGN_LEFT);
                        p8.setFont(paraFont5);
                        p8.add(des + "\n");
                        if (!role.equals("")) {
                            p8.add("Role: " + role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                document.add(p8);
            }

            document.add(rect);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRewards(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Achievements and Rewards:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p9 = new Paragraph();
                p9.setFont(paraFont5);
                p9.setSpacingAfter(10f);
                p9.setSpacingBefore(5f);
                p9.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  REWARDS where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String rewards = cursor.getString(1);
                        p9.add("\u2022");
                        p9.add(" " + rewards + "\n");

                    } while (cursor.moveToNext());
                }
                document.add(p9);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameExtra(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Extracurricular Activites:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p10 = new Paragraph();
                p10.setFont(paraFont5);
                p10.setSpacingAfter(10f);
                p10.setSpacingBefore(5f);
                p10.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  EXTRA_CURRICULAR where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String extra = cursor.getString(1);
                        p10.add("\u2022");
                        p10.add(" " + extra + "\n");

                    } while (cursor.moveToNext());
                }
                document.add(p10);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryHobby(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Hobbies:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(4);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p11 = new Paragraph();
                p11.setFont(paraFont5);
                p11.setSpacingAfter(10f);
                p11.setSpacingBefore(5f);
                p11.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  HOBBIES where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String hobbies = cursor.getString(1);
                        p11.add(hobbies);
                        if (!cursor.isLast()){
                            p11.add(", ");
                        }
                    } while (cursor.moveToNext());
                }
                document.add(p11);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryStrength(rowId);
            if (this.cursor.getCount() > 0)
            {
                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Strength:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p12 = new Paragraph();
                p12.setFont(paraFont5);
                p12.setSpacingAfter(10f);
                p12.setSpacingBefore(5f);
                p12.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  STRENGTH where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String strength = cursor.getString(1);
                        p12.add(strength);
                        if (!cursor.isLast()){
                            p12.add(", ");
                        }
                    } while (cursor.moveToNext());
                }
                document.add(p12);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Personal Details:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p13 = new Paragraph();
                p13.setFont(paraFont5);
                p13.setSpacingAfter(10f);
                p13.setSpacingBefore(5f);
                p13.setIndentationLeft(5);
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        address = cursor.getString(10);
                        dob = cursor.getString(4);
                        gender = cursor.getString(5);
                        father = cursor.getString(2);
                        mother = cursor.getString(3);
                        nationality = cursor.getString(6);
                        language = cursor.getString(7);
                        mother_ton = cursor.getString(8);
                        passport = cursor.getString(9);

                        if (address.equals("")) {
                            p13.add("Date of Birth        : " + dob + "\n");
                            p13.add("Gender                 : " + gender + "\n");
                            p13.add("Father's Name      : " + father + "\n");
                            p13.add("Mother's Name    : " + mother + "\n");
                            p13.add("Nationality           : " + nationality + "\n");
                            p13.add("Language             : " + language + "\n");
                            p13.add("Mother Tongue    : " + mother_ton + "\n");
                            if (!passport.equals("")) {
                                p13.add("Passport               : " + passport);
                            }
                        }
                        else {
                            p13.add("Address                : " + address + "\n");
                            p13.add("Date of Birth        : " + dob + "\n");
                            p13.add("Gender                 : " + gender + "\n");
                            p13.add("Father's Name      : " + father + "\n");
                            p13.add("Mother's Name    : " + mother + "\n");
                            p13.add("Nationality           : " + nationality + "\n");
                            p13.add("Language             : " + language + "\n");
                            p13.add("Mother Tongue    : " + mother_ton + "\n");
                            if (!passport.equals("")) {
                                p13.add("Passport               : " + passport);
                            }
                        }

                    } while (cursor.moveToNext());
                }
                document.add(p13);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRefer(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("References:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p14 = new Paragraph();
                p14.setFont(paraFont5);
                p14.setSpacingAfter(10f);
                p14.setSpacingBefore(5f);
                p14.setIndentationLeft(5);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from REFER where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String re_name = cursor.getString(1);
                        String re_des = cursor.getString(2);
                        String re_org = cursor.getString(3);
                        String re_number = cursor.getString(4);
                        p14.add("\u2022");
                        p14.add(" " + re_name + "-" + re_des + ", " + re_org + " - " + re_number + "\n");
                    } while (cursor.moveToNext());
                }
                document.add(p14);
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryDecleartion(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table1 = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("Declaration:",paraFontWhite));
                cell.setBackgroundColor(new BaseColor(17,72,134));
                cell.setPadding(5);
                cell.setPaddingBottom(7);
                table1.addCell(cell);
                table1.setWidthPercentage(100);
                document.add(table1);

                Paragraph p15 = new Paragraph();
                p15.setFont(paraFont5);
                p15.setSpacingAfter(5f);
                p15.setSpacingBefore(5f);
                p15.setIndentationLeft(5);
                cursor = adpt.queryDecleartion(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        declaration = cursor.getString(1);
                        p15.add(declaration);

                    } while (cursor.moveToNext());
                }
                document.add(p15);
            }

            Paragraph p16  = new Paragraph();
            Font paraFont16= new Font(Font.FontFamily.TIMES_ROMAN,14.0f,Font.BOLD, CMYKColor.BLACK);
            p16.setAlignment(Paragraph.ALIGN_LEFT);
            p16.setFont(paraFont16);
            p16.setSpacingBefore(5f);
            p16.setIndentationLeft(5);
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()){
                do {

                    name = cursor.getString(1);
                    p16.add("Date:");
                    p16.add("\nPlace:");
                    Chunk glue = new Chunk(new VerticalPositionMark());
                    p16.add(new Chunk(glue));
                    p16.add(name);



                }
                while (cursor.moveToNext());

            }
            document.add(p16);



              /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.RIGHT);

            //add image to document
            document.add(myImg);
*/

            document.add(rect);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
            Intent intent = new Intent(GeneratePdf.this,ShowResume.class);
            startActivity(intent);
        }

    }




    private void createPdf4() {
        // TODO Auto-generated method stub


        PdfWriter writer = null;
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CV Creator";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            Toast.makeText(this, "Resume Generated at" + path, Toast.LENGTH_LONG).show();

            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File file = new File(dir, pdfName + "-" + timestamp + ".pdf");
            OutputStream fOut = new FileOutputStream(file);

            writer = PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();

            Rectangle rectangle = new Rectangle(700, 850, 0, 765);
            rectangle.setBackgroundColor(new BaseColor(51, 51, 51));
            document.add(rectangle);

            Font paraFont = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFont5 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);
            Font paraFontSpace = new Font(Font.FontFamily.TIMES_ROMAN, 1.0f, 0, CMYKColor.BLACK);
            Font paraFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.ITALIC, CMYKColor.BLACK);
            Font paraFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFontItalic1 = new Font(Font.FontFamily.TIMES_ROMAN, 17.0f, Font.ITALIC, CMYKColor.WHITE);
            Font paraFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD|Font.UNDERLINE, CMYKColor.BLACK);
            Font paraFont6 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, 0, CMYKColor.BLACK);
            Font paraFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f,Font.BOLD, CMYKColor.BLACK);
            Font paraFontItalic2 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.ITALIC, CMYKColor.BLACK);

            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(670,825,20,790);



            Font paraFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 22.0f, Font.BOLD, CMYKColor.WHITE);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {

                    name = cursor.getString(1);
                    ct.setText(new Phrase(name + "\n",paraFont2));
                    ct.go();

                }
                while (cursor.moveToNext());

            }


            ColumnText ct1 = new ColumnText(writer.getDirectContent());
            ct1.setSimpleColumn(670,797,20,775);

            db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
            cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
            if (cursor.moveToFirst()) {
                do {
                    String working = cursor.getString(3);
                    String designation = cursor.getString(2);
                    if (working.equals("Currently")){
                        ct1.setText(new Phrase(designation + "\n",paraFontItalic1));
                        ct1.go();
                    }


                } while (cursor.moveToNext());
            }

            ColumnText ct2 = new ColumnText(writer.getDirectContent());
            ct2.setSimpleColumn(415,760,20,0);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.querynameobjecticveedt(rowId);
            if (this.cursor.getCount() > 0)
            {

               /* Chunk c = new Chunk("Career Objective :\n" , paraFont);
                c.setBackground(BaseColor.LIGHT_GRAY);*/

               Paragraph p3 = new Paragraph();
                p3.setFont(paraFont5);
                p3.setSpacingAfter(5f);
                cursor = adpt.querynameobjecticveedt(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        carrier_obj = cursor.getString(1);
                        p3.add(carrier_obj + "\n");

                    } while (cursor.moveToNext());

                }
                ct2.addElement(p3);
                ct2.go();
            }

            Rectangle rect1 = new Rectangle(670, 765, 430, 0);
            rect1.setBackgroundColor(new BaseColor(242, 242, 242));
            document.add(rect1);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAcademicList(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl = new Paragraph();
                pl.setSpacingBefore(5f);
                pl.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Academic Records",paraFont);
                pl.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl.add(ls);
                ct2.addElement(pl);
                ct2.go();

                Paragraph p4 = new Paragraph();
                p4.setFont(paraFont5);
                p4.setSpacingAfter(5f);
                p4.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from ACADEMIC_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String compelition = cursor.getString(4);
                        String degree = cursor.getString(1);
                        String college = cursor.getString(2);
                        String stream = cursor.getString(3);
                        String percent = cursor.getString(5);
                        String percentage = cursor.getString(6);
                        String yr_from = cursor.getString(7);
                        String yr_to = cursor.getString(8);
                        p4.setFont(paraFont1);
                        p4.add(degree);
                        if (!stream.equals("")) {
                            p4.setFont(paraFontItalic);
                            p4.add(", "+stream);
                            p4.setFont(paraFont5);
                            p4.add(" - "+college);
                            if (compelition.equals("Completed")){
                                String last = yr_to.substring(yr_to.length()-2);
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add(yr_from+"-"+last +"\n");
                            }
                            else {
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add("Present\n");
                            }

                        } else {
                            p4.setFont(paraFont5);
                            p4.add(" - "+college);
                            if (compelition.equals("Completed")){
                                String last = yr_to.substring(yr_to.length()-2);
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add(yr_from+"-"+last +"\n");
                            }
                            else {
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add("Present\n");
                            }
                        }

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p4);
                ct2.go();

            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryName2(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl1 = new Paragraph();
                pl1.setSpacingBefore(5f);
                pl1.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Working Experience",paraFont);
                pl1.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl1.add(ls);
                ct2.addElement(pl1);
                ct2.go();

                Paragraph p5 = new Paragraph();
                p5.setFont(paraFont5);
                p5.setSpacingAfter(5f);
                p5.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String working = cursor.getString(3);
                        organization = cursor.getString(1);
                        String designation = cursor.getString(2);
                        String wr_yr_from = cursor.getString(4);
                        String wr_yr_to = cursor.getString(5);
                        String wr_role = cursor.getString(6);
                        p5.setFont(paraFont1);
                        p5.add(organization +"\n");
                        p5.setFont(paraFontItalic);
                        p5.add(designation +", ");
                        p5.setFont(paraFont5);
                        p5.add(wr_yr_from + " - ");
                        if (working.equals("Currently")){
                            p5.add("Present\n");
                        }
                        else {
                            p5.add(wr_yr_to +"\n");
                        }
                        if (!cursor.isLast()) {
                            p5.add(wr_role + "\n");
                        }
                        else {
                            p5.add(wr_role);
                        }

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p5);
                ct2.go();
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameTraining(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl3 = new Paragraph();
                pl3.setSpacingBefore(5f);
                pl3.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Training Details",paraFont);
                pl3.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl3.add(ls);
                ct2.addElement(pl3);
                ct2.go();

                Paragraph p7 = new Paragraph();
                p7.setFont(paraFont5);
                p7.setSpacingAfter(5f);
                p7.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TRAINING where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String tr_org = cursor.getString(1);
                        String tr_project = cursor.getString(2);
                        String tr_date_from = cursor.getString(4);
                        String tr_date_to = cursor.getString(5);
                        String tr_des = cursor.getString(3);
                        String tr_role = cursor.getString(6);
                        p7.setFont(paraFont1);
                        p7.add(tr_org + ": ");
                        p7.setFont(paraFont1);
                        p7.add(tr_project);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p7.setFont(paraFont5);
                        p7.add(new Chunk(glue));
                        p7.add(tr_date_from + "-" + tr_date_to + "\n");
                        p7.setAlignment(Paragraph.ALIGN_LEFT);
                        p7.setFont(paraFont5);
                        p7.add(tr_des + "\n");
                        if (!tr_role.equals("")) {
                            p7.add("Role: " + tr_role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                ct2.addElement(p7);
                ct2.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameProject(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl4 = new Paragraph();
                pl4.setSpacingBefore(5f);
                pl4.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Project Details",paraFont);
                pl4.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl4.add(ls);
                ct2.addElement(pl4);
                ct2.go();

                Paragraph p8 = new Paragraph();
                p8.setFont(paraFont5);
                p8.setSpacingAfter(5f);
                p8.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TAB_PROJECT where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String project = cursor.getString(1);
                        String org = cursor.getString(2);
                        String date_from = cursor.getString(4);
                        String date_to = cursor.getString(5);
                        String des = cursor.getString(3);
                        String role = cursor.getString(6);
                        p8.setFont(paraFont1);
                        p8.add(project + ": ");
                        p8.setFont(paraFont1);
                        p8.add(org);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p8.setFont(paraFont5);
                        p8.add(new Chunk(glue));
                        p8.add(date_from + "-" + date_to + "\n");
                        p8.setAlignment(Paragraph.ALIGN_LEFT);
                        p8.setFont(paraFont5);
                        p8.add(des + "\n");
                        if (!role.equals("")) {
                        p8.add("Role: " + role + "\n");
                    }
                    } while (cursor.moveToNext());
                }

                ct2.addElement(p8);
                ct2.go();
            }



            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRewards(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl5 = new Paragraph();
                pl5.setSpacingBefore(5f);
                pl5.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Achievements and Rewards",paraFont);
                pl5.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl5.add(ls);
                ct2.addElement(pl5);
                ct2.go();

                Paragraph p9 = new Paragraph();
                p9.setFont(paraFont5);
                p9.setSpacingAfter(5f);
                p9.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  REWARDS where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String rewards = cursor.getString(1);
                        p9.add("\u2022");
                        p9.add(" " + rewards + "\n");

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p9);
                ct2.go();
            }

/*
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameExtra(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl6 = new Paragraph();
                pl6.setSpacingBefore(5f);
                pl6.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Extracurricular Activites",paraFont);
                pl6.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl6.add(ls);
                ct2.addElement(pl6);
                ct2.go();

                Paragraph p10 = new Paragraph();
                p10.setFont(paraFont5);
                p10.setSpacingAfter(5f);
                p10.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  EXTRA_CURRICULAR where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String extra = cursor.getString(1);
                        p10.add("\u2022");
                        p10.add(" " + extra + "\n");

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p10);
                ct2.go();
            }
*/

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryDecleartion(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl10 = new Paragraph();
                pl10.setSpacingBefore(5f);
                pl10.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Declaration",paraFont);
                pl10.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl10.add(ls);
                ct2.addElement(pl10);
                ct2.go();

                Paragraph p15 = new Paragraph();
                p15.setFont(paraFont5);
                p15.setSpacingAfter(5f);
                p15.setSpacingBefore(5f);
                cursor = adpt.queryDecleartion(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        declaration = cursor.getString(1);
                        p15.add(declaration);

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p15);
                ct2.go();
            }

            ColumnText ct3 = new ColumnText(writer.getDirectContent());
            ct3.setSimpleColumn(590,760,440,0);

            adpt = new RegistrationAdapter(this);
            cursor =adpt.queryAll(rowId);
            if (cursor.getCount()>0) {
                Paragraph pl21 = new Paragraph();
                pl21.setSpacingBefore(5f);
                pl21.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Personal Info",paraFont);
                pl21.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl21.add(ls);
                ct3.addElement(pl21);
                ct3.go();

                Paragraph p2 = new Paragraph();
                p2.setAlignment(Paragraph.ALIGN_LEFT);
                p2.setSpacingAfter(5f);
                p2.setSpacingBefore(5f);
                adpt = new RegistrationAdapter(this);
                cursor = adpt.queryAll(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        email = cursor.getString(2);
                        contact = cursor.getString(3);
                        link = cursor.getString(4);
                        p2.setFont(paraFont3);
                        p2.add("Email\n");
                        p2.setFont(paraFont6);
                        p2.add(email + "\n");
                        p2.setFont(paraFont3);
                        p2.add("Contact\n");
                        p2.setFont(paraFont6);
                        p2.add(contact + "\n");
                        p2.setFont(paraFont3 );
                        p2.add("Url\n");
                        p2.setFont(paraFont6);
                        p2.add(link);


                    }
                    while (cursor.moveToNext());
                }
                ct3.addElement(p2);
                ct3.go();

            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl22 = new Paragraph();
                pl22.setSpacingBefore(5f);
                pl22.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Languages",paraFont);
                pl22.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl22.add(ls);
                ct3.addElement(pl22);
                ct3.go();

                Paragraph p13 = new Paragraph();
                p13.setFont(paraFont6);
                p13.setSpacingAfter(5f);
                p13.setSpacingBefore(5f);
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        language = cursor.getString(7);
                        p13.add(language);

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p13);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl23 = new Paragraph();
                pl23.setSpacingBefore(5f);
                pl23.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Technical Details",paraFont);
                pl23.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl23.add(ls);
                ct3.addElement(pl23);
                ct3.go();

                Paragraph p6 = new Paragraph();
                p6.setFont(paraFont6);
                p6.setSpacingAfter(5f);
                p6.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  TECHNICAL where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String technical = cursor.getString(1);
                        p6.add(technical +"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p6);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryHobby(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl24 = new Paragraph();
                pl24.setSpacingBefore(5f);
                pl24.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Hobbies",paraFont);
                pl24.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl24.add(ls);
                ct3.addElement(pl24);
                ct3.go();

                Paragraph p11 = new Paragraph();
                p11.setFont(paraFont6);
                p11.setSpacingAfter(5f);
                p11.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  HOBBIES where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String hobbies = cursor.getString(1);
                        p11.add(hobbies+"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p11);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryStrength(rowId);
            if (this.cursor.getCount() > 0)
            {
                Paragraph pl25 = new Paragraph();
                pl25.setSpacingBefore(5f);
                pl25.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Strength",paraFont);
                pl25.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl25.add(ls);
                ct3.addElement(pl25);
                ct3.go();


                Paragraph p12 = new Paragraph();
                p12.setFont(paraFont6);
                p12.setSpacingAfter(5f);
                p12.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  STRENGTH where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String strength = cursor.getString(1);
                        p12.add(strength+"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p12);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRefer(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl26 = new Paragraph();
                pl26.setSpacingBefore(5f);
                pl26.setSpacingAfter(4f);
                Phrase phrase = new Phrase("References",paraFont);
                pl26.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,90f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl26.add(ls);
                ct3.addElement(pl26);
                ct3.go();

                Paragraph p14 = new Paragraph();
                p14.setFont(paraFont6);
                p14.setSpacingAfter(5f);
                p14.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from REFER where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String re_name = cursor.getString(1);
                        String re_des = cursor.getString(2);
                        String re_org = cursor.getString(3);
                        String re_number = cursor.getString(4);
                        p14.setFont(paraFont3);
                        p14.add(re_name);
                        p14.setFont(paraFontItalic2);
                        p14.add(", "+re_des+"\n");
                        p14.setFont(paraFont6);
                        p14.add(re_org+"\n");
                    } while (cursor.moveToNext());
                }
                ct3.addElement(p14);
                ct3.go();
            }


           /* Paragraph p15 = new Paragraph();
            cursor = adpt.queryPersonal(rowId);
            if (cursor.moveToFirst()) {
                do {

                    String img = cursor.getString(1);

                    Image myImg = Image.getInstance(img);
                    myImg.scaleAbsolute(100f,100f);
                    myImg.setAbsolutePosition(450f,650f);
                    p15.add(myImg);
                }while (cursor.moveToNext());
            }
            //add image to document
            document.add(p15);
*/


              /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.RIGHT);

            //add image to document
            document.add(myImg);
*/

//            document.add(rect);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
            Intent intent = new Intent(GeneratePdf.this,ShowResume.class);
            startActivity(intent);
        }

    }


    private void createPdf5() {
        // TODO Auto-generated method stub


        PdfWriter writer = null;
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CV Creator";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            Toast.makeText(this, "Resume Generated at" + path, Toast.LENGTH_LONG).show();

            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File file = new File(dir, pdfName + "-" + timestamp + ".pdf");
            OutputStream fOut = new FileOutputStream(file);

            writer = PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();


            Rectangle rectangle = new Rectangle(180, 850, 0, 0);
            rectangle.setBackgroundColor(new BaseColor(0,0,104));
            document.add(rectangle);

            Font paraFont = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFont5 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);
            Font paraFontSpace = new Font(Font.FontFamily.TIMES_ROMAN, 1.0f, 0, CMYKColor.BLACK);
            Font paraFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.ITALIC, CMYKColor.BLACK);
            Font paraFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFontItalic1 = new Font(Font.FontFamily.TIMES_ROMAN, 17.0f, Font.ITALIC, CMYKColor.WHITE);
            Font paraFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD|Font.UNDERLINE, CMYKColor.BLACK);
            Font paraFont6 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, 0, CMYKColor.WHITE);
            Font paraFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f,Font.BOLD, CMYKColor.WHITE);
            Font paraFontItalic2 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.ITALIC, CMYKColor.WHITE);
            Font paraFontWhite = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.WHITE);

            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(170,825,15,0);



            Font paraFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 22.0f, Font.BOLD, CMYKColor.WHITE);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {

                    name = cursor.getString(1);
                    ct.setText(new Phrase(name + "\n",paraFont2));
                    ct.go();

                }
                while (cursor.moveToNext());

            }


            ColumnText ct1 = new ColumnText(writer.getDirectContent());
            ct1.setSimpleColumn(170,797,15,775);

            db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
            cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
            if (cursor.moveToFirst()) {
                do {
                    String working = cursor.getString(3);
                    String designation = cursor.getString(2);
                    if (working.equals("Currently")){
                        ct1.setText(new Phrase(designation + "\n",paraFontItalic1));
                        ct1.go();
                    }


                } while (cursor.moveToNext());
            }




            ColumnText ct2 = new ColumnText(writer.getDirectContent());
            ct2.setSimpleColumn(580,830,185,0);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.querynameobjecticveedt(rowId);
            if (this.cursor.getCount() > 0)
            {

               /* Chunk c = new Chunk("Career Objective :\n" , paraFont);
                c.setBackground(BaseColor.LIGHT_GRAY);*/

                Paragraph p3 = new Paragraph();
                p3.setFont(paraFont5);
                p3.setSpacingAfter(5f);
                cursor = adpt.querynameobjecticveedt(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        carrier_obj = cursor.getString(1);
                        p3.add(carrier_obj + "\n");

                    } while (cursor.moveToNext());

                }
                ct2.addElement(p3);
                ct2.go();
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAcademicList(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl = new Paragraph();
                pl.setSpacingBefore(5f);
                pl.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Academic Records",paraFont);
                pl.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl.add(ls);
                ct2.addElement(pl);
                ct2.go();

                Paragraph p4 = new Paragraph();
                p4.setFont(paraFont5);
                p4.setSpacingAfter(5f);
                p4.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from ACADEMIC_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String compelition = cursor.getString(4);
                        String degree = cursor.getString(1);
                        String college = cursor.getString(2);
                        String stream = cursor.getString(3);
                        String percent = cursor.getString(5);
                        String percentage = cursor.getString(6);
                        String yr_from = cursor.getString(7);
                        String yr_to = cursor.getString(8);
                        p4.setFont(paraFont1);
                        p4.add(degree);
                        if (!stream.equals("")) {
                            p4.setFont(paraFontItalic);
                            p4.add(", "+stream);
                            p4.setFont(paraFont5);
                            p4.add(" - "+college);
                            if (compelition.equals("Completed")){
                                String last = yr_to.substring(yr_to.length()-2);
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add(yr_from+"-"+last +"\n");
                            }
                            else {
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add("Present\n");
                            }

                        } else {
                            p4.setFont(paraFont5);
                            p4.add(" - "+college);
                            if (compelition.equals("Completed")){
                                String last = yr_to.substring(yr_to.length()-2);
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add(yr_from+"-"+last +"\n");
                            }
                            else {
                                Chunk glue = new Chunk(new VerticalPositionMark());
                                p4.setFont(paraFont5);
                                p4.add(new Chunk(glue));
                                p4.add("Present\n");
                            }
                        }

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p4);
                ct2.go();

            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryName2(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl1 = new Paragraph();
                pl1.setSpacingBefore(5f);
                pl1.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Working Experience",paraFont);
                pl1.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl1.add(ls);
                ct2.addElement(pl1);
                ct2.go();

                Paragraph p5 = new Paragraph();
                p5.setFont(paraFont5);
                p5.setSpacingAfter(5f);
                p5.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String working = cursor.getString(3);
                        organization = cursor.getString(1);
                        String designation = cursor.getString(2);
                        String wr_yr_from = cursor.getString(4);
                        String wr_yr_to = cursor.getString(5);
                        String wr_role = cursor.getString(6);
                        p5.setFont(paraFont1);
                        p5.add(organization +"\n");
                        p5.setFont(paraFontItalic);
                        p5.add(designation +", ");
                        p5.setFont(paraFont5);
                        p5.add(wr_yr_from + " - ");
                        if (working.equals("Currently")){
                            p5.add("Present\n");
                        }
                        else {
                            p5.add(wr_yr_to +"\n");
                        }
                        if (!cursor.isLast()) {
                            p5.add(wr_role + "\n");
                        }
                        else {
                            p5.add(wr_role);
                        }

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p5);
                ct2.go();
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameTraining(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl3 = new Paragraph();
                pl3.setSpacingBefore(5f);
                pl3.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Training Details",paraFont);
                pl3.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl3.add(ls);
                ct2.addElement(pl3);
                ct2.go();

                Paragraph p7 = new Paragraph();
                p7.setFont(paraFont5);
                p7.setSpacingAfter(5f);
                p7.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TRAINING where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String tr_org = cursor.getString(1);
                        String tr_project = cursor.getString(2);
                        String tr_date_from = cursor.getString(4);
                        String tr_date_to = cursor.getString(5);
                        String tr_des = cursor.getString(3);
                        String tr_role = cursor.getString(6);
                        p7.setFont(paraFont1);
                        p7.add(tr_org + ": ");
                        p7.setFont(paraFont1);
                        p7.add(tr_project);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p7.setFont(paraFont5);
                        p7.add(new Chunk(glue));
                        p7.add(tr_date_from + "-" + tr_date_to + "\n");
                        p7.setAlignment(Paragraph.ALIGN_LEFT);
                        p7.setFont(paraFont5);
                        p7.add(tr_des + "\n");
                        if (!tr_role.equals("")) {
                        p7.add("Role: " + tr_role + "\n");
                    }
                    } while (cursor.moveToNext());
                }

                ct2.addElement(p7);
                ct2.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameProject(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl4 = new Paragraph();
                pl4.setSpacingBefore(5f);
                pl4.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Project Details",paraFont);
                pl4.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl4.add(ls);
                ct2.addElement(pl4);
                ct2.go();

                Paragraph p8 = new Paragraph();
                p8.setFont(paraFont5);
                p8.setSpacingAfter(5f);
                p8.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TAB_PROJECT where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String project = cursor.getString(1);
                        String org = cursor.getString(2);
                        String date_from = cursor.getString(4);
                        String date_to = cursor.getString(5);
                        String des = cursor.getString(3);
                        String role = cursor.getString(6);
                        p8.setFont(paraFont1);
                        p8.add(project + ": ");
                        p8.setFont(paraFont1);
                        p8.add(org);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p8.setFont(paraFont5);
                        p8.add(new Chunk(glue));
                        p8.add(date_from + "-" + date_to + "\n");
                        p8.setAlignment(Paragraph.ALIGN_LEFT);
                        p8.setFont(paraFont5);
                        p8.add(des + "\n");
                        if (!role.equals("")) {
                        p8.add("Role: " + role + "\n");
                    }
                    } while (cursor.moveToNext());
                }

                ct2.addElement(p8);
                ct2.go();
            }



            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRewards(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl5 = new Paragraph();
                pl5.setSpacingBefore(5f);
                pl5.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Achievements and Rewards",paraFont);
                pl5.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl5.add(ls);
                ct2.addElement(pl5);
                ct2.go();

                Paragraph p9 = new Paragraph();
                p9.setFont(paraFont5);
                p9.setSpacingAfter(5f);
                p9.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  REWARDS where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String rewards = cursor.getString(1);
                        p9.add("\u2022");
                        p9.add(" " + rewards + "\n");

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p9);
                ct2.go();
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameExtra(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl6 = new Paragraph();
                pl6.setSpacingBefore(5f);
                pl6.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Extracurricular Activites",paraFont);
                pl6.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl6.add(ls);
                ct2.addElement(pl6);
                ct2.go();

                Paragraph p10 = new Paragraph();
                p10.setFont(paraFont5);
                p10.setSpacingAfter(5f);
                p10.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  EXTRA_CURRICULAR where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String extra = cursor.getString(1);
                        p10.add("\u2022");
                        p10.add(" " + extra + "\n");

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p10);
                ct2.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryDecleartion(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl10 = new Paragraph();
                pl10.setSpacingBefore(5f);
                pl10.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Declaration",paraFont);
                pl10.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl10.add(ls);
                ct2.addElement(pl10);
                ct2.go();

                Paragraph p15 = new Paragraph();
                p15.setFont(paraFont5);
                p15.setSpacingAfter(5f);
                p15.setSpacingBefore(5f);
                cursor = adpt.queryDecleartion(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        declaration = cursor.getString(1);
                        p15.add(declaration);

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p15);
                ct2.go();
            }

            ColumnText ct3 = new ColumnText(writer.getDirectContent());
            ct3.setSimpleColumn(170,770,15,0);

            adpt = new RegistrationAdapter(this);
            cursor =adpt.queryAll(rowId);
            if (cursor.getCount()>0) {

                Paragraph pl22 = new Paragraph();
                pl22.setSpacingBefore(5f);
                pl22.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Personal Info",paraFontWhite);
                pl22.add(phrase);
                ct3.addElement(pl22);
                ct3.go();

                Paragraph p2 = new Paragraph();
                p2.setAlignment(Paragraph.ALIGN_LEFT);
                p2.setSpacingAfter(5f);
                p2.setSpacingBefore(5f);
                adpt = new RegistrationAdapter(this);
                cursor = adpt.queryAll(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        email = cursor.getString(2);
                        contact = cursor.getString(3);
                        link = cursor.getString(4);
                        p2.setFont(paraFont3);
                        p2.add("Email\n");
                        p2.setFont(paraFont6);
                        p2.add(email + "\n");
                        p2.setFont(paraFont3);
                        p2.add("Contact\n");
                        p2.setFont(paraFont6);
                        p2.add(contact + "\n");
                        p2.setFont(paraFont3 );
                        p2.add("Url\n");
                        p2.setFont(paraFont6);
                        p2.add(link);


                    }
                    while (cursor.moveToNext());
                }
                ct3.addElement(p2);
                ct3.go();

            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl22 = new Paragraph();
                pl22.setSpacingBefore(5f);
                pl22.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Languages",paraFontWhite);
                pl22.add(phrase);
                ct3.addElement(pl22);
                ct3.go();


                Paragraph p13 = new Paragraph();
                p13.setFont(paraFont6);
                p13.setSpacingAfter(5f);
                p13.setSpacingBefore(5f);
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        language = cursor.getString(7);
                        p13.add(language);

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p13);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl22 = new Paragraph();
                pl22.setSpacingBefore(5f);
                pl22.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Technical Details",paraFontWhite);
                pl22.add(phrase);
                ct3.addElement(pl22);
                ct3.go();

                Paragraph p6 = new Paragraph();
                p6.setFont(paraFont6);
                p6.setSpacingAfter(5f);
                p6.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  TECHNICAL where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String technical = cursor.getString(1);
                        p6.add(technical +"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p6);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryHobby(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl22 = new Paragraph();
                pl22.setSpacingBefore(5f);
                pl22.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Hobbies",paraFontWhite);
                pl22.add(phrase);
                ct3.addElement(pl22);
                ct3.go();

                Paragraph p11 = new Paragraph();
                p11.setFont(paraFont6);
                p11.setSpacingAfter(5f);
                p11.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  HOBBIES where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String hobbies = cursor.getString(1);
                        p11.add(hobbies+"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p11);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryStrength(rowId);
            if (this.cursor.getCount() > 0)
            {

                Paragraph pl22 = new Paragraph();
                pl22.setSpacingBefore(5f);
                pl22.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Strength",paraFontWhite);
                pl22.add(phrase);
                ct3.addElement(pl22);
                ct3.go();

                Paragraph p12 = new Paragraph();
                p12.setFont(paraFont6);
                p12.setSpacingAfter(5f);
                p12.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  STRENGTH where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String strength = cursor.getString(1);
                        p12.add(strength+"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p12);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRefer(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl22 = new Paragraph();
                pl22.setSpacingBefore(5f);
                pl22.setSpacingAfter(4f);
                Phrase phrase = new Phrase("References",paraFontWhite);
                pl22.add(phrase);
                ct3.addElement(pl22);
                ct3.go();

                Paragraph p14 = new Paragraph();
                p14.setFont(paraFont6);
                p14.setSpacingAfter(5f);
                p14.setSpacingBefore(10f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from REFER where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String re_name = cursor.getString(1);
                        String re_des = cursor.getString(2);
                        String re_org = cursor.getString(3);
                        String re_number = cursor.getString(4);
                        p14.setFont(paraFont3);
                        p14.add(re_name);
                        p14.setFont(paraFontItalic2);
                        p14.add(", "+re_des+"\n");
                        p14.setFont(paraFont6);
                        p14.add(re_org+"\n");
                    } while (cursor.moveToNext());
                }
                ct3.addElement(p14);
                ct3.go();
            }



              /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.RIGHT);

            //add image to document
            document.add(myImg);
*/

//            document.add(rect);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
            Intent intent = new Intent(GeneratePdf.this,ShowResume.class);
            startActivity(intent);
        }

    }



    private void createPdf6() {
        // TODO Auto-generated method stub


        PdfWriter writer = null;
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CV Creator";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            Toast.makeText(this, "Resume Generated at" + path, Toast.LENGTH_LONG).show();

            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File file = new File(dir, pdfName + "-" + timestamp + ".pdf");
            OutputStream fOut = new FileOutputStream(file);

            writer = PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();


            Rectangle rectangle = new Rectangle(600, 850, 0, 695);
            rectangle.setBackgroundColor(new BaseColor(50,50,50));
            document.add(rectangle);

            Rectangle rectangle1 = new Rectangle(180, 815, 20, 8);
            rectangle1.setBackgroundColor(new BaseColor(250,236,236));
            document.add(rectangle1);

            Rectangle rectangle2 = new Rectangle(600, 6, 0, 0);
            rectangle2.setBackgroundColor(new BaseColor(50,50,50));
            document.add(rectangle2);


            Font paraFont = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFont5 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, 0, CMYKColor.BLACK);
            Font paraFontSpace = new Font(Font.FontFamily.TIMES_ROMAN, 1.0f, 0, CMYKColor.BLACK);
            Font paraFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.ITALIC, CMYKColor.BLACK);
            Font paraFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.BOLD, CMYKColor.BLACK);
            Font paraFontItalic1 = new Font(Font.FontFamily.TIMES_ROMAN, 17.0f, Font.ITALIC, CMYKColor.WHITE);
            Font paraFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD|Font.UNDERLINE, CMYKColor.BLACK);
            Font paraFont6 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, 0, CMYKColor.BLACK);
            Font paraFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f,Font.BOLD, CMYKColor.BLACK);
            Font paraFontItalic2 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.ITALIC, CMYKColor.BLACK);
            Font paraFontWhite = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.BOLD, CMYKColor.WHITE);
            Font paraFontWhite2 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, CMYKColor.WHITE);
            Font paraFont7 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, 0, CMYKColor.BLACK);


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {


                Paragraph p15 = new Paragraph();
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {

                        String img = cursor.getString(1);

                        if (cursor.getString(1) != null) {
                            Image myImg = Image.getInstance(img);
                            myImg.scaleAbsolute(90f, 90f);
                            myImg.setAbsolutePosition(55f, 695f);
                            p15.add(myImg);
                        }
                        else {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.profile_pic);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            Image myImg = Image.getInstance(stream.toByteArray());
                            myImg.scaleAbsolute(90f, 90f);
                            myImg.setAbsolutePosition(55f, 695f);
                            p15.add(myImg);

                        }
                    } while (cursor.moveToNext());
                }
                document.add(p15);
            }
            else{
                Paragraph p15 = new Paragraph();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.profile_pic);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                Image myImg = Image.getInstance(stream.toByteArray());
                myImg.scaleAbsolute(90f, 90f);
                myImg.setAbsolutePosition(55f, 695f);
                p15.add(myImg);
                document.add(p15);
            }

            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(580,780,200,730);



            Font paraFont2 = new Font(Font.FontFamily.HELVETICA, 30.0f, Font.BOLD, CMYKColor.WHITE);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAll(rowId);
            if (cursor.moveToFirst()) {
                do {

                    name = cursor.getString(1);
                    ct.setText(new Phrase(name + "\n",paraFont2));
                    ct.go();

                }
                while (cursor.moveToNext());

            }

            LineSeparator ls1 = new LineSeparator(2f,80f,CMYKColor.WHITE,Element.ALIGN_LEFT,-15f);
            ct.addElement(ls1);
            ct.go();

            ColumnText ct1 = new ColumnText(writer.getDirectContent());
            ct1.setSimpleColumn(580,735,200,700);

            Font paraFontP = new Font(Font.FontFamily.COURIER, 20.0f, Font.BOLD, CMYKColor.WHITE);
            db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
            cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
            if (cursor.moveToFirst()) {
                do {
                    String working = cursor.getString(3);
                    String designation = cursor.getString(2);
                    if (working.equals("Currently")){
                        ct1.setText(new Phrase(designation + "\n",paraFontP));
                        ct1.go();
                    }


                } while (cursor.moveToNext());
            }




            ColumnText ct2 = new ColumnText(writer.getDirectContent());
            ct2.setSimpleColumn(570,685,200,8);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.querynameobjecticveedt(rowId);
            if (this.cursor.getCount() > 0)
            {

               /* Chunk c = new Chunk("Career Objective :\n" , paraFont);
                c.setBackground(BaseColor.LIGHT_GRAY);*/

                Paragraph p3 = new Paragraph();
                p3.setFont(paraFont5);
                p3.setSpacingAfter(10f);
                cursor = adpt.querynameobjecticveedt(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        carrier_obj = cursor.getString(1);
                        p3.add(carrier_obj + "\n");

                    } while (cursor.moveToNext());

                }
                ct2.addElement(p3);
                ct2.go();
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryName2(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("E X P E R I E N C E",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct2.addElement(table);
                ct2.go();

                Paragraph p5 = new Paragraph();
                p5.setFont(paraFont5);
                p5.setSpacingAfter(10f);
                p5.setSpacingBefore(10f);
                p5.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from EXP_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String working = cursor.getString(3);
                        organization = cursor.getString(1);
                        String designation = cursor.getString(2);
                        String wr_yr_from = cursor.getString(4);
                        String wr_yr_to = cursor.getString(5);
                        String wr_role = cursor.getString(6);
                        p5.setFont(paraFont1);
                        p5.add(organization +"\n");
                        p5.setFont(paraFontItalic);
                        p5.add(designation +", ");
                        p5.setFont(paraFont5);
                        p5.add(wr_yr_from + " - ");
                        if (working.equals("Currently")){
                            p5.add("Present\n");
                        }
                        else {
                            p5.add(wr_yr_to +"\n");
                        }
                        if (!cursor.isLast()) {
                            p5.add(wr_role + "\n");
                        }
                        else {
                            p5.add(wr_role);
                        }

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p5);
                ct2.go();
            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameTraining(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("T R A I N I N G  D E T A I L S",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct2.addElement(table);
                ct2.go();

                Paragraph p7 = new Paragraph();
                p7.setFont(paraFont5);
                p7.setSpacingAfter(10f);
                p7.setSpacingBefore(10f);
                p7.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TRAINING where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String tr_org = cursor.getString(1);
                        String tr_project = cursor.getString(2);
                        String tr_date_from = cursor.getString(4);
                        String tr_date_to = cursor.getString(5);
                        String tr_des = cursor.getString(3);
                        String tr_role = cursor.getString(6);
                        p7.setFont(paraFont1);
                        p7.add(tr_org + ": ");
                        p7.setFont(paraFont1);
                        p7.add(tr_project);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p7.setFont(paraFont5);
                        p7.add(new Chunk(glue));
                        p7.add(tr_date_from + "-" + tr_date_to + "\n");
                        p7.setAlignment(Paragraph.ALIGN_LEFT);
                        p7.setFont(paraFont5);
                        p7.add(tr_des + "\n");
                        if (!tr_role.equals("")) {
                            p7.add("Role: " + tr_role + "\n");
                        }
                    } while (cursor.moveToNext());
                }

                ct2.addElement(p7);
                ct2.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameProject(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("P R O J E C T  D E T A I L S",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct2.addElement(table);
                ct2.go();

                Paragraph p8 = new Paragraph();
                p8.setFont(paraFont5);
                p8.setSpacingAfter(10f);
                p8.setSpacingBefore(10f);
                p8.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from TAB_PROJECT where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String project = cursor.getString(1);
                        String org = cursor.getString(2);
                        String date_from = cursor.getString(4);
                        String date_to = cursor.getString(5);
                        String des = cursor.getString(3);
                        String role = cursor.getString(6);
                        p8.setFont(paraFont1);
                        p8.add(project + ": ");
                        p8.setFont(paraFont1);
                        p8.add(org);
                        Chunk glue = new Chunk(new VerticalPositionMark());
                        p8.setFont(paraFont5);
                        p8.add(new Chunk(glue));
                        p8.add(date_from + "-" + date_to + "\n");
                        p8.setAlignment(Paragraph.ALIGN_LEFT);
                        p8.setFont(paraFont5);
                        p8.add(des + "\n");
                        if (!role.equals("")) {
                            p8.add("Role: " + role + "\n");
                        }
                        } while (cursor.moveToNext());
                }

                ct2.addElement(p8);
                ct2.go();
            }



            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRewards(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("A C H I E V E M E N T S  A N D  R E W A R D S",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct2.addElement(table);
                ct2.go();

                Paragraph p9 = new Paragraph();
                p9.setFont(paraFont5);
                p9.setSpacingAfter(10f);
                p9.setSpacingBefore(10f);
                p9.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  REWARDS where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String rewards = cursor.getString(1);
                        p9.add("\u2022");
                        p9.add(" " + rewards + "\n");

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p9);
                ct2.go();
            }


            /*adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameExtra(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph pl6 = new Paragraph();
                pl6.setSpacingBefore(5f);
                pl6.setSpacingAfter(4f);
                Phrase phrase = new Phrase("Extracurricular Activites",paraFont);
                pl6.add(phrase);
                LineSeparator ls = new LineSeparator(0.5f,100f,CMYKColor.BLACK,Element.ALIGN_LEFT,-5f);
                pl6.add(ls);
                ct2.addElement(pl6);
                ct2.go();

                Paragraph p10 = new Paragraph();
                p10.setFont(paraFont5);
                p10.setSpacingAfter(5f);
                p10.setSpacingBefore(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  EXTRA_CURRICULAR where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String extra = cursor.getString(1);
                        p10.add("\u2022");
                        p10.add(" " + extra + "\n");

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p10);
                ct2.go();
            }
*/
            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryDecleartion(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("D E C L A R A T I O N",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct2.addElement(table);
                ct2.go();

                Paragraph p15 = new Paragraph();
                p15.setFont(paraFont5);
                p15.setSpacingAfter(10f);
                p15.setSpacingBefore(10f);
                p15.setIndentationLeft(5f);
                cursor = adpt.queryDecleartion(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        declaration = cursor.getString(1);
                        p15.add(declaration);

                    } while (cursor.moveToNext());
                }
                ct2.addElement(p15);
                ct2.go();
            }



            ColumnText ct3 = new ColumnText(writer.getDirectContent());
            ct3.setSimpleColumn(170,690,30,8);

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {

                Paragraph p1 = new Paragraph();
                p1.setFont(paraFont7);
                p1.setIndentationLeft(5f);
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        address = cursor.getString(10);
                        if (cursor.getString(10) != null){
                            p1.add(address);
                        }

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p1);
                ct3.go();
            }


            adpt = new RegistrationAdapter(this);
            cursor =adpt.queryAll(rowId);
            if (cursor.getCount()>0) {

                Paragraph p2 = new Paragraph();
                p2.setAlignment(Paragraph.ALIGN_LEFT);
                p2.setSpacingAfter(10f);
                p2.setIndentationLeft(5f);
                adpt = new RegistrationAdapter(this);
                cursor = adpt.queryAll(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        email = cursor.getString(2);
                        contact = cursor.getString(3);
                        link = cursor.getString(4);
                        p2.setFont(paraFont7);
                        p2.add(email + "\n");
                        p2.add(contact + "\n");
                        p2.add(link);
                    }
                    while (cursor.moveToNext());
                }
                ct3.addElement(p2);
                ct3.go();

            }


            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryAcademicList(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("E D U C A T I O N",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct3.addElement(table);
                ct3.go();

                Paragraph p4 = new Paragraph();
                Font paraFont8 = new Font(Font.FontFamily.TIMES_ROMAN,12.0f,Font.BOLD,CMYKColor.BLACK);
                p4.setFont(paraFont8);
                p4.setSpacingAfter(10f);
                p4.setSpacingBefore(10f);
                p4.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from ACADEMIC_INFO where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String compelition = cursor.getString(4);
                        String degree = cursor.getString(1);
                        String college = cursor.getString(2);
                        String yr_from = cursor.getString(7);
                        String yr_to = cursor.getString(8);

                        p4.setFont(paraFont8);
                        p4.add(degree + "\n");
                        p4.setFont(paraFont6);
                        p4.add(college + "\n");
                        if (compelition.equals("Completed")){
                            p4.setFont(paraFont6);
                            p4.add(yr_from+"-"+yr_to +"\n");
                        }
                        else {
                            p4.setFont(paraFont6);
                            p4.add(yr_from+"-"+"Present\n");
                        }

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p4);
                ct3.go();

            }



            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryPersonal(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("L A N G U A G E",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct3.addElement(table);
                ct3.go();

                Paragraph p13 = new Paragraph();
                p13.setFont(paraFont6);
                p13.setSpacingAfter(10f);
                p13.setSpacingBefore(10f);
                p13.setIndentationLeft(5f);
                cursor = adpt.queryPersonal(rowId);
                if (cursor.moveToFirst()) {
                    do {
                        language = cursor.getString(7);
                        p13.add(language);

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p13);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryTechnical(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("S K I L L S",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct3.addElement(table);
                ct3.go();

                Paragraph p6 = new Paragraph();
                p6.setFont(paraFont6);
                p6.setSpacingAfter(10f);
                p6.setSpacingBefore(10f);
                p6.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  TECHNICAL where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String technical = cursor.getString(1);
                        p6.add(technical +"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p6);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryHobby(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("H O B B I E S",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct3.addElement(table);
                ct3.go();

                Paragraph p11 = new Paragraph();
                p11.setFont(paraFont6);
                p11.setSpacingAfter(10f);
                p11.setSpacingBefore(10f);
                p11.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  HOBBIES where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {

                        String hobbies = cursor.getString(1);
                        p11.add(hobbies+"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p11);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryStrength(rowId);
            if (this.cursor.getCount() > 0)
            {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("S T R E N G T H",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct3.addElement(table);
                ct3.go();

                Paragraph p12 = new Paragraph();
                p12.setFont(paraFont6);
                p12.setSpacingAfter(10f);
                p12.setSpacingBefore(10f);
                p12.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from  STRENGTH where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String strength = cursor.getString(1);
                        p12.add(strength+"\n");

                    } while (cursor.moveToNext());
                }
                ct3.addElement(p12);
                ct3.go();
            }

            adpt = new RegistrationAdapter(this);
            cursor = adpt.queryNameRefer(rowId);
            if (this.cursor.getCount() > 0) {

                PdfPTable table = new PdfPTable(1);
                PdfPCell cell = new PdfPCell(new Phrase("R E F E R E N C E S",paraFontWhite2));
                cell.setBackgroundColor(new BaseColor(50,50 ,50));
                cell.setPadding(3);
                cell.setPaddingLeft(5);
                cell.setPaddingBottom(6);
                table.addCell(cell);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(100);
                ct3.addElement(table);
                ct3.go();


                Paragraph p14 = new Paragraph();
                p14.setFont(paraFont6);
                p14.setSpacingAfter(10f);
                p14.setSpacingBefore(10f);
                p14.setIndentationLeft(5f);
                db = openOrCreateDatabase("REGISTRATION_DB", MODE_PRIVATE, null);
                cursor = db.rawQuery("select * from REFER where R_ID=" + rowId, null);
                if (cursor.moveToFirst()) {
                    do {
                        String re_name = cursor.getString(1);
                        String re_des = cursor.getString(2);
                        String re_org = cursor.getString(3);
                        String re_number = cursor.getString(4);
                        p14.setFont(paraFont3);
                        p14.add(re_name);
                        p14.setFont(paraFontItalic2);
                        p14.add("\n"+re_des+"\n");
                        p14.setFont(paraFont6);
                        p14.add(re_org+"\n");
                    } while (cursor.moveToNext());
                }
                ct3.addElement(p14);
                ct3.go();
            }



              /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.RIGHT);

            //add image to document
            document.add(myImg);
*/

//            document.add(rect);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
            Intent intent = new Intent(GeneratePdf.this,ShowResume.class);
            startActivity(intent);
        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(GeneratePdf.this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(GeneratePdf.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(GeneratePdf.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(GeneratePdf.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }
}
