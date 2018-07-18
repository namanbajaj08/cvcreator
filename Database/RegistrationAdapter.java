package com.naman.resumemaker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naman.resumemaker.Util;

/**
 * Created by SSB-2 on 27-09-2017.
 */

public class RegistrationAdapter {

    Context context;
    SQLiteDatabase database_ob;
    RegistrationOpenHelper openHelper_ob;

    public RegistrationAdapter(Context c) {
        this.context = c;
    }

    public RegistrationAdapter opnToRead() {
        this.openHelper_ob = new RegistrationOpenHelper(this.context, RegistrationOpenHelper.DATABASE_NAME, null, 2);
        this.database_ob = this.openHelper_ob.getReadableDatabase();
        return this;
    }

    public RegistrationAdapter opnToWrite() {
        this.openHelper_ob = new RegistrationOpenHelper(this.context, RegistrationOpenHelper.DATABASE_NAME, null, 2);
        this.database_ob = this.openHelper_ob.getWritableDatabase();
        return this;
    }

    public void Close() {
        this.database_ob.close();
    }

    public long insertDetail(String fname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.RNAME, fname);
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_NAME, null, contentValues);
        Close();
        return val;
    }

    public long insertappilcantDetail(String name, String email, String contact, String link, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.NAME, name);
        contentValues.put(RegistrationOpenHelper.EMAIL, email);
        contentValues.put(RegistrationOpenHelper.CONTACT, contact);
        contentValues.put(RegistrationOpenHelper.LINK, link);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_APPLICANT, null, contentValues);
        Close();
        return val;
    }


    public long updateapplicantDetail(String name, String email, String contact, String link, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.NAME, name);
        contentValues.put(RegistrationOpenHelper.EMAIL, email);
        contentValues.put(RegistrationOpenHelper.CONTACT, contact);
        contentValues.put(RegistrationOpenHelper.LINK, link);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_APPLICANT, contentValues, RegistrationOpenHelper.KEY_ID1 + "=" + r_id, null);
        Close();
        return val;
    }

    public long insertobjectiveDetail(String objective, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.OBJECTIVE, objective);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_OBJECTIVE_DETAIL, null, contentValues);
        Close();
        return val;
    }

    public long updateinsertobjectiveDetail(String objective, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.OBJECTIVE, objective);

        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_OBJECTIVE_DETAIL, contentValues, RegistrationOpenHelper.KEY_ID1 + "=" + r_id, null);
        Close();
        return val;
    }

    public long insertDecleration(String description, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.DECLERATION, description);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_DECLERATION, null, contentValues);
        Close();
        return val;
    }

    public long updateDecleration(String description, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.DECLERATION, description);

        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_DECLERATION, contentValues, RegistrationOpenHelper.KEY_ID1 + "=" + r_id, null);
        Close();
        return val;
    }


    public long academicinfo(String degree, String college, String specialization, String completion, String percentage, String percentile, String from, String to, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.DEGREE, degree);
        contentValues.put(RegistrationOpenHelper.COLLEGE, college);
        contentValues.put(RegistrationOpenHelper.SPECIALIZATION, specialization);
        contentValues.put(RegistrationOpenHelper.COMPLETION, completion);
        contentValues.put(RegistrationOpenHelper.PERCENTAGE, percentage);
        contentValues.put(RegistrationOpenHelper.PERCENTILE, percentile);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_ACADEMICAL_DETAIL, null, contentValues);
        Close();
        return val;
    }
    public long updateacademic(String degree, String college, String specialization, String completion, String percentage, String percentile, String from, String to, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.DEGREE, degree);
        contentValues.put(RegistrationOpenHelper.COLLEGE, college);
        contentValues.put(RegistrationOpenHelper.SPECIALIZATION, specialization);
        contentValues.put(RegistrationOpenHelper.COMPLETION, completion);
        contentValues.put(RegistrationOpenHelper.PERCENTAGE, percentage);
        contentValues.put(RegistrationOpenHelper.PERCENTILE, percentile);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_ACADEMICAL_DETAIL, contentValues, RegistrationOpenHelper.KEY_ID + "=" + Util.acdemic_id, null);
        Close();
        return val;
    }

    public long workexpinfo(String org, String pos, String work, String from, String to, String role, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.POST, pos);
        contentValues.put(RegistrationOpenHelper.WORKING, work);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.ROLE, role);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_EXP_DETAIL, null, contentValues);
        Close();
        return val;
    }

    public long updateworkexpinfo(String org, String pos, String work, String from, String to, String role, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.POST, pos);
        contentValues.put(RegistrationOpenHelper.WORKING, work);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.ROLE, role);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_EXP_DETAIL, contentValues, RegistrationOpenHelper.KEY_ID + "=" + Util.workinfo_id, null);
        Close();
        return val;
    }

    public long trainingInfo(String org, String project, String des, String from, String to, String role, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.PROJECT, project);
        contentValues.put(RegistrationOpenHelper.DESCRIPTION, des);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.ROLE, role);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_TRAINING, null, contentValues);
        Close();
        return val;
    }

    public long updatetraininginfo(String org, String project, String des, String from, String to, String role, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.PROJECT, project);
        contentValues.put(RegistrationOpenHelper.DESCRIPTION, des);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.ROLE, role);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_TRAINING, contentValues, RegistrationOpenHelper.KEY_ID + "=" + Util.training_id, null);
        Close();
        return val;
    }


    public long projectInfo(String project,String org, String des, String from, String to, String role, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.PROJECT, project);
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.DESCRIPTION, des);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.ROLE, role);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_PROJECT, null, contentValues);
        Close();
        return val;
    }

    public long updateprojectinfo(String project, String org, String des, String from, String to, String role, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.PROJECT, project);
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.DESCRIPTION, des);
        contentValues.put(RegistrationOpenHelper.DATE_FROM, from);
        contentValues.put(RegistrationOpenHelper.DATE_TO, to);
        contentValues.put(RegistrationOpenHelper.ROLE, role);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_PROJECT, contentValues, RegistrationOpenHelper.KEY_ID + "=" + Util.project_id, null);
        Close();
        return val;
    }


    public long referInfo(String name, String post, String org, String contact, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.NAME, name);
        contentValues.put(RegistrationOpenHelper.POST, post);
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.CONTACT, contact);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_REFER, null, contentValues);
        Close();
        return val;
    }

    public long updatereferinfo(String name, String post, String org, String contact, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.NAME, name);
        contentValues.put(RegistrationOpenHelper.POST, post);
        contentValues.put(RegistrationOpenHelper.ORG, org);
        contentValues.put(RegistrationOpenHelper.CONTACT, contact);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_REFER, contentValues, RegistrationOpenHelper.KEY_ID + "=" + Util.ref_id, null);
        Close();
        return val;
    }

    public long insertTechnicalDetail(String skill, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.SKILLS, skill);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_TECHNICAL, null, contentValues);
        Close();
        return val;
    }

    public long insertHobbies(String hobby, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.HOBBY, hobby);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_HOBBY, null, contentValues);
        Close();
        return val;
    }

    public long insertStrength(String stength, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.STRENGTH, stength);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_STRENGTH, null, contentValues);
        Close();
        return val;
    }

    public long rewards(String reward,int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.REWARD, reward);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_REWARDS, null, contentValues);
        Close();
        return val;
    }

    public long updaterewards(String reward,int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.REWARD, reward);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_REWARDS, contentValues, RegistrationOpenHelper.KEY_ID + "=" + Util.rewards_id, null);
        Close();
        return val;
    }

    public long extra_curricular(String extra,int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.EXTRA, extra);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_EXTRA, null, contentValues);
        Close();
        return val;
    }

    public long updateextra(String extra,int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.EXTRA, extra);
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_EXTRA, contentValues, RegistrationOpenHelper.KEY_ID + "=" + Util.extra_id, null);
        Close();
        return val;
    }

    public long insertpersonaltDetail(String profile, String father, String mother, String dob, String gender, String nationality, String language, String mother_tongue, String passport, String address, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.PROFILE_IMG, profile);
        contentValues.put(RegistrationOpenHelper.FATHER, father);
        contentValues.put(RegistrationOpenHelper.MOTHER, mother);
        contentValues.put(RegistrationOpenHelper.DOB, dob );
        contentValues.put(RegistrationOpenHelper.GENDER, gender );
        contentValues.put(RegistrationOpenHelper.NATIONALITY, nationality );
        contentValues.put(RegistrationOpenHelper.LANGUAGE, language );
        contentValues.put(RegistrationOpenHelper.MOTHER_TONGUE, mother_tongue );
        contentValues.put(RegistrationOpenHelper.PASSPORT, passport );
        contentValues.put(RegistrationOpenHelper.ADDRESS, address );
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = this.database_ob.insert(RegistrationOpenHelper.TABLE_PERSONAL_DETAIL, null, contentValues);
        Close();
        return val;
    }


    public long updatepersonalDetail(String profile, String father, String mother, String dob, String gender, String nationality, String language, String mother_tongue, String passport, String address, int r_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationOpenHelper.PROFILE_IMG, profile);
        contentValues.put(RegistrationOpenHelper.FATHER, father);
        contentValues.put(RegistrationOpenHelper.MOTHER, mother);
        contentValues.put(RegistrationOpenHelper.DOB, dob );
        contentValues.put(RegistrationOpenHelper.GENDER, gender );
        contentValues.put(RegistrationOpenHelper.NATIONALITY, nationality );
        contentValues.put(RegistrationOpenHelper.LANGUAGE, language );
        contentValues.put(RegistrationOpenHelper.MOTHER_TONGUE, mother_tongue );
        contentValues.put(RegistrationOpenHelper.PASSPORT, passport );
        contentValues.put(RegistrationOpenHelper.ADDRESS, address );
        contentValues.put(RegistrationOpenHelper.KEY_ID1, Integer.valueOf(r_id));
        opnToWrite();
        long val = (long) this.database_ob.update(RegistrationOpenHelper.TABLE_PERSONAL_DETAIL, contentValues, RegistrationOpenHelper.KEY_ID1 + "=" + r_id, null);
        Close();
        return val;
    }



    public Cursor queryTechnical(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.SKILLS};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_TECHNICAL, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryHobby(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.HOBBY};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_HOBBY, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryStrength(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.STRENGTH};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_STRENGTH, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryName() {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.RNAME};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_NAME, cols, null, null, null, null, null);
    }
    public Cursor queryName2(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.ORG};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_EXP_DETAIL, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryNameTraining(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.ORG};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_TRAINING, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryNameProject(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.PROJECT};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_PROJECT, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryNameRefer(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.NAME};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_REFER, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryNameRewards(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.REWARD};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_REWARDS, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryNameExtra(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.EXTRA};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_EXTRA, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryAll(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.NAME, RegistrationOpenHelper.EMAIL, RegistrationOpenHelper.CONTACT, RegistrationOpenHelper.LINK};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_APPLICANT, cols, RegistrationOpenHelper.KEY_ID1 + "=" + nameId, null, null, null, null, null);
    }

    public Cursor querynameobjecticveedt(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.OBJECTIVE};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_OBJECTIVE_DETAIL, cols, RegistrationOpenHelper.KEY_ID1 + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryDecleartion(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.DECLERATION};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_DECLERATION, cols, RegistrationOpenHelper.KEY_ID1 + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryAcademicList(int rid) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.DEGREE};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_ACADEMICAL_DETAIL, cols, RegistrationOpenHelper.KEY_ID1 + "=" + rid, null, null, null, null, null);
    }

    public Cursor queryacademic1(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.DEGREE, RegistrationOpenHelper.COLLEGE,RegistrationOpenHelper.SPECIALIZATION, RegistrationOpenHelper.COMPLETION,RegistrationOpenHelper.PERCENTAGE, RegistrationOpenHelper.PERCENTILE, RegistrationOpenHelper.DATE_FROM,RegistrationOpenHelper.DATE_TO, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_ACADEMICAL_DETAIL, cols, RegistrationOpenHelper.KEY_ID + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryexpinfo(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.ORG, RegistrationOpenHelper.POST, RegistrationOpenHelper.DATE_FROM, RegistrationOpenHelper.DATE_TO, RegistrationOpenHelper.ROLE, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_EXP_DETAIL, cols, RegistrationOpenHelper.KEY_ID1 + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryexpinfo1(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.ORG, RegistrationOpenHelper.POST, RegistrationOpenHelper.WORKING, RegistrationOpenHelper.DATE_FROM, RegistrationOpenHelper.DATE_TO, RegistrationOpenHelper.ROLE, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_EXP_DETAIL, cols, RegistrationOpenHelper.KEY_ID + "=" + nameId, null, null, null, null, null);
    }

    public Cursor querytraining(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.ORG, RegistrationOpenHelper.PROJECT,  RegistrationOpenHelper.DESCRIPTION, RegistrationOpenHelper.DATE_FROM, RegistrationOpenHelper.DATE_TO, RegistrationOpenHelper.ROLE, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_TRAINING, cols, RegistrationOpenHelper.KEY_ID + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryproject(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.PROJECT, RegistrationOpenHelper.ORG,  RegistrationOpenHelper.DESCRIPTION, RegistrationOpenHelper.DATE_FROM, RegistrationOpenHelper.DATE_TO, RegistrationOpenHelper.ROLE, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_PROJECT, cols, RegistrationOpenHelper.KEY_ID + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryrefer(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.NAME, RegistrationOpenHelper.POST,RegistrationOpenHelper.ORG,  RegistrationOpenHelper.CONTACT, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_REFER, cols, RegistrationOpenHelper.KEY_ID + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryrewards(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.REWARD, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_REWARDS, cols, RegistrationOpenHelper.KEY_ID + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryextra(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.EXTRA, RegistrationOpenHelper.KEY_ID1};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_EXTRA, cols, RegistrationOpenHelper.KEY_ID + "=" + nameId, null, null, null, null, null);
    }

    public Cursor queryPersonal(int nameId) {
        String[] cols = new String[]{RegistrationOpenHelper.KEY_ID, RegistrationOpenHelper.PROFILE_IMG, RegistrationOpenHelper.FATHER, RegistrationOpenHelper.MOTHER, RegistrationOpenHelper.DOB, RegistrationOpenHelper.GENDER, RegistrationOpenHelper.NATIONALITY, RegistrationOpenHelper.LANGUAGE, RegistrationOpenHelper.MOTHER_TONGUE, RegistrationOpenHelper.PASSPORT, RegistrationOpenHelper.ADDRESS};
        opnToWrite();
        return this.database_ob.query(RegistrationOpenHelper.TABLE_PERSONAL_DETAIL, cols, RegistrationOpenHelper.KEY_ID1 + "=" + nameId, null, null, null, null, null);
    }


    public int deletOneRecord(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_NAME, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordacademicinfo(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_ACADEMICAL_DETAIL, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordworkexo(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_EXP_DETAIL, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordTraining(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_TRAINING, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordProject(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_PROJECT, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordRefer(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_REFER, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordTechnical(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_TECHNICAL, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordHobby(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_HOBBY, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordStrength(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_STRENGTH, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }


    public int deletOneRecordRewards(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_REWARDS, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordExtra(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_EXTRA, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

    public int deletOneRecordPersonal(String rowId) {
        opnToWrite();
        int val = this.database_ob.delete(RegistrationOpenHelper.TABLE_PERSONAL_DETAIL, RegistrationOpenHelper.KEY_ID + "=" + rowId, null);
        Close();
        return val;
    }

}
