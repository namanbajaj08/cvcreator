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
import com.naman.resumemaker.DetailedActivity.ExtraDetail;
import com.naman.resumemaker.DetailedActivity.RewardsDetail;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.ExtraCurricular;
import com.naman.resumemaker.ResumeCreateActivity.RewardsActivty;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtraAdapter extends BaseAdapter {

    String [] id_extra;
    String [] name_extra;
    Context context;
    LayoutInflater inflater;
    RegistrationAdapter adpt;

    public ExtraAdapter(ExtraCurricular extra , String [] extra_id , String [] extra_name){
        context = extra;
        id_extra = extra_id;
        name_extra = extra_name;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return name_extra.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Extra{
        TextView tv;
        ImageView delete;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Extra extra = new Extra();
        View root = inflater.inflate(R.layout.extra_list,null);
        extra.tv =(TextView)root.findViewById(R.id.extra_name);
        extra.delete = (ImageView)root.findViewById(R.id.delete);
        extra.tv.setText(name_extra[i]);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.extra_id = Integer.parseInt(id_extra[i]);
                Intent intent = new Intent(context , ExtraDetail.class);
                intent.putExtra("item",id_extra[i]);
                context.startActivity(intent);
            }
        });

        extra.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecordExtra(id_extra[i]); 
                String [] b = name_extra;
                List<String> list = new ArrayList(Arrays.asList(name_extra));
                List<String> list1 = new ArrayList(Arrays.asList(id_extra));
                list.remove(i);
                list1.remove(i);
                name_extra = (String[])list.toArray(new String[0]);
                id_extra = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();

            }
        });


        return root;
    }


}
