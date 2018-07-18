package com.naman.resumemaker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class RegistrationOpenHelper extends SQLiteOpenHelper {
    public static final String ADDRESS = "ADDRESS";
    public static final String COLLEGE = "COLLEGE";
    public static final String CONTACT = "CONTACT";
    public static final String COMPLETION = "COMPLETION";
    public static final String DATABASE_NAME = "REGISTRATION_DB";
    public static final String DATE_FROM = "DATE_FROM";
    public static final String DATE_TO = "DATE_TO";
    public static final String DECLERATION = "DECLERATION";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String DEGREE = "DEGREE";
    public static final String DOB = "DOB";
    public static final String EXTRA = "EXTRA";
    public static final String EMAIL = "EMAIL";
    public static final String FATHER = "FATHER";
    public static final String GENDER = "GENDER";
    public static final String HOBBY = "HOBBY";
    public static final String KEY_ID = "_id";
    public static final String KEY_ID1 = "R_ID";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String LINK = "LINK";
    public static final String MOTHER = "MOTHER";
    public static final String MOTHER_TONGUE = "MOTHER_TONGUE";
    public static final String NAME = "NAME";
    public static final String NATIONALITY = "NAITIONALITY";
    public static final String OBJECTIVE = "OBJECTIVE";
    public static final String ORG = "ORG";
    public static final String PASSPORT = "PASSPORT";
    public static final String PERCENTAGE = "PERCENTAGE";
    public static final String PERCENTILE = "PERCENTILE";
    public static final String POST = "POSITION";
    public static final String PROFILE_IMG = "PROFILE_IMG";
    public static final String PROJECT = "PROJECT";
    public static final String REWARD = "REWARD";
    public static final String RNAME = "R_NAME";
    public static final String ROLE = "ROLE";
    public static final String SCRIPT = "create table RESUMNAME (_id integer primary key autoincrement, R_NAME text not null);";
    public static final String SCRIPT2 = "create table APPLICANT(_id integer primary key autoincrement, NAME text not null, EMAIL text not null, CONTACT text not null, LINK text not null, R_ID integer );";
    public static final String SCRIPT3 = "create table objective_INFO (_id integer primary key autoincrement, OBJECTIVE TEXT, R_ID INTEGER);";
    public static final String SCRIPT4 = "create table ACADEMIC_INFO (_id integer primary key autoincrement, DEGREE text not null, COLLEGE text not null, SPECIALIZATION text not null,COMPLETION text not null, PERCENTAGE text not null, PERCENTILE text not null, DATE_FROM text not null, DATE_TO text not null, R_ID integer );";
    public static final String SCRIPT5 = "create table EXP_INFO (_id integer primary key autoincrement, ORG TEXT not null, POSITION TEXT, WORK TEXT not null, DATE_FROM TEXT, DATE_TO TEXT, ROLE TEXT, R_ID INTEGER);";
    public static final String SCRIPT6 = "create table TRAINING (_id integer primary key autoincrement, ORG TEXT not null, PROJECT TEXT, DESCRIPTION TEXT not null, DATE_FROM TEXT, DATE_TO TEXT, ROLE TEXT, R_ID INTEGER);";
    public static final String SCRIPT7 = "create table TAB_PROJECT  (_id integer primary key autoincrement, PROJECT TEXT not null, ORG TEXT, DESCRIPTION TEXT not null, DATE_FROM TEXT, DATE_TO TEXT, ROLE TEXT, R_ID INTEGER);";
    public static final String SCRIPT8 = "create table TECHNICAL (_id integer primary key autoincrement, SKILLS text not null, R_ID INTEGER);";
    public static final String SCRIPT9 = "create table HOBBIES (_id integer primary key autoincrement, HOBBY text not null, R_ID INTEGER);";
    public static final String SCRIPT10 = "create table STRENGTH (_id integer primary key autoincrement, STRENGTH text not null, R_ID INTEGER);";
    public static final String SCRIPT11 = "create table REWARDS (_id integer primary key autoincrement, REWARD text not null, R_ID INTEGER);";
    public static final String SCRIPT12 = "create table EXTRA_CURRICULAR (_id integer primary key autoincrement, EXTRA text not null, R_ID INTEGER);";
    public static final String SCRIPT13 = "create table REFER (_id integer primary key autoincrement, NAME text not null, POSITION text not null, ORG text not null, CONTACT text not null, R_ID integer );";
    public static final String SCRIPT14 = "create table DECLERATION (_id integer primary key autoincrement, DECLERATION TEXT, R_ID INTEGER);";
    public static final String SCRIPT15 = "create table PERSONAL_DETAIL (_id integer primary key autoincrement, PROFILE_IMG text, FATHER text not null, MOTHER text not null, DOB text not null, GENDER text not null, NAITIONALITY text not null, LANGUAGE text not null, MOTHER_TONGUE text not null, PASSPORT text not null, ADDRESS text not null, R_ID integer );";
    public static final String SKILLS = "SKILLS";
    public static final String SPECIALIZATION = "SPECIALIZATION";
    public static final String STRENGTH = "STRENGTH";
    public static final String TABLE_ACADEMICAL_DETAIL = "ACADEMIC_INFO";
    public static final String TABLE_APPLICANT = "APPLICANT";
    public static final String TABLE_DECLERATION = "DECLERATION";
    public static final String TABLE_EXP_DETAIL = "EXP_INFO";
    public static final String TABLE_EXTRA = "EXTRA_CURRICULAR";
    public static final String TABLE_HOBBY = "HOBBIES";
    public static final String TABLE_NAME = "RESUMNAME";
    public static final String TABLE_OBJECTIVE_DETAIL = "objective_INFO";
    public static final String TABLE_PERSONAL_DETAIL = "PERSONAL_DETAIL";
    public static final String TABLE_PROJECT = "TAB_PROJECT";
    public static final String TABLE_REFER = "REFER";
    public static final String TABLE_REWARDS = "REWARDS";
    public static final String TABLE_STRENGTH = "STRENGTH";
    public static final String TABLE_TECHNICAL = "TECHNICAL";
    public static final String TABLE_TRAINING = "TRAINING";
    public static final String WORKING = "WORK";
    public RegistrationOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SCRIPT);
        sqLiteDatabase.execSQL(SCRIPT2);
        sqLiteDatabase.execSQL(SCRIPT3);
        sqLiteDatabase.execSQL(SCRIPT4);
        sqLiteDatabase.execSQL(SCRIPT5);
        sqLiteDatabase.execSQL(SCRIPT6);
        sqLiteDatabase.execSQL(SCRIPT7);
        sqLiteDatabase.execSQL(SCRIPT8);
        sqLiteDatabase.execSQL(SCRIPT9);
        sqLiteDatabase.execSQL(SCRIPT10);
        sqLiteDatabase.execSQL(SCRIPT11);
        sqLiteDatabase.execSQL(SCRIPT12);
        sqLiteDatabase.execSQL(SCRIPT13);
        sqLiteDatabase.execSQL(SCRIPT14);
        sqLiteDatabase.execSQL(SCRIPT15);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table RESUMNAME");
        db.execSQL("drop table APPLICANT");
        db.execSQL("drop table objective_INFO");
        db.execSQL("drop table ACADEMIC_INFO");
        db.execSQL("drop table DECLERATION");
        db.execSQL("drop table EXP_INFO");
        db.execSQL("drop table TRAINING");
        db.execSQL("drop table TAB_PROJECT");
        db.execSQL("drop table REFER");
        db.execSQL("drop table TECHNICAL");
        db.execSQL("drop table HOBBIES");
        db.execSQL("drop table STRENGTH");
        db.execSQL("drop table REWARDS");
        db.execSQL("drop table EXTRA_CURRICULAR");
        db.execSQL("drop table PERSONAL_DETAIL");
        onCreate(db);
    }
}
