package com.naman.resumemaker.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.DetailedActivity.AcademicDetail;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.AcademicRecord;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AcademicAdapter extends BaseAdapter {

    String [] id_aca;
    String [] name_aca;
    Context context;
    LayoutInflater inflater;
    RegistrationAdapter adpt;

    public AcademicAdapter(AcademicRecord record , String [] aca_id , String [] aca_name){
        context = record;
        id_aca = aca_id;
        name_aca = aca_name;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return name_aca.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Academic{
        TextView tv;
        ImageView delete;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Academic academic = new Academic();
        View root = inflater.inflate(R.layout.academic_list,null);
        academic.tv =(TextView)root.findViewById(R.id.academic_name);
        academic.delete = (ImageView)root.findViewById(R.id.delete);
        academic.tv.setText(name_aca[i]);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.acdemic_id = Integer.parseInt(id_aca[i]);
                Intent intent = new Intent(context , AcademicDetail.class);
                intent.putExtra("item",id_aca[i]);
                context.startActivity(intent);
            }
        });

        academic.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecordacademicinfo(id_aca[i]);
                String [] b = name_aca;
                List<String> list = new ArrayList(Arrays.asList(name_aca));
                List<String> list1 = new ArrayList(Arrays.asList(id_aca));
                list.remove(i);
                list1.remove(i);
                name_aca = (String[])list.toArray(new String[0]);
                id_aca = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();

            }
        });


        return root;
    }

    public void addrecord(String value){
        name_aca[name_aca.length + 1] = value;
        notifyDataSetInvalidated();
    }
}
