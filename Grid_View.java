package com.naman.resumemaker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.TextView;

import com.naman.resumemaker.Adapter.GridAdapter;

public class Grid_View extends AppCompatActivity {

    String action;
    Context context;
    GridView gridView;

    private  int [] pdfFormate = {R.drawable.format_1 , R.drawable.format_2 , R.drawable.format_3,R.drawable.format_4, R.drawable.format_5, R.drawable.format_6};
    private  String [] pdfName = {"Format 1", "Format 2" , "Format 3" , "Format 4" ,"Format 5" , "Format 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid__view);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();


        gridView = (GridView)findViewById(R.id.grid_view);
        gridView.setAdapter(new GridAdapter(this , pdfFormate , pdfName));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
