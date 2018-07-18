package com.naman.resumemaker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman.resumemaker.GeneratePdf;
import com.naman.resumemaker.Grid_View;
import com.naman.resumemaker.R;


public class GridAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    int [] pdfFormates ;
    String [] pdfNames ;


    public GridAdapter(Grid_View pdf , int[] pdfFormate , String [] pdfName){
        context = pdf;
        pdfFormates = pdfFormate;
        pdfNames = pdfName;
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pdfNames.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Formate{
        ImageView resume;
        TextView  formate_name;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Formate formate = new Formate();
        final  View root = inflater.inflate(R.layout.resumeformate,null);
        formate.resume = (ImageView)root.findViewById(R.id.formate_img);
        formate.formate_name = (TextView)root.findViewById(R.id.formate_name);
        formate.resume.setImageResource(pdfFormates[i]);
        formate.formate_name.setText(pdfNames[i]);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , GeneratePdf.class);
                intent.putExtra("img_formate" , pdfFormates[i]);
                intent.putExtra("formate_name", pdfNames[i]);
                context.startActivity(intent);
            }
        });
        return root;
    }
}
