package com.naman.resumemaker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.naman.resumemaker.Adapter.ShowAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ShowResume extends AppCompatActivity {

    ListView listView;
    String action;
    TextView textView;
    ArrayList<String> resumelist;
    public static String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resume);

        textView = (TextView)findViewById(R.id.add_academic);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();


        resumelist = new ArrayList<String>();

        listView =(ListView)findViewById(R.id.resume_list);
        listView.setEmptyView(textView);

         path= Environment.getExternalStorageDirectory().getAbsolutePath() + "/CV Creator";
        File file = new File(path);
        File [] list = file.listFiles();

        if (list != null && list.length > 1) {
            Arrays.sort(list, new Comparator<File>() {
                @Override
                public int compare(File object1, File object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
        }


        if (list!=null) {
            for (int i = 0; i < list.length; i++) {
                resumelist.add(list[i].getName());
            }
        }
        else {
            listView.setEmptyView(textView);
        }

        /*if (list !=null && list.length>1){
            Arrays.sort(list, new Comparator<File>() {
                @Override
                public int compare(File file, File t1) {
                    long lastModifiedO1 = file.lastModified();
                    long lastModifiedO2 = t1.lastModified();
                    return (lastModifiedO2 < lastModifiedO1) ? -1 : ((lastModifiedO1 > lastModifiedO2) ? 1 : 0);
                }
            });
        }*/
        listView.setAdapter(new ShowAdapter(this, resumelist));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Intent intent = new Intent(ShowResume.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
