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
import com.naman.resumemaker.DetailedActivity.ExperienceDetail;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.ExperienceActivity;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SSB-2 on 30-09-2017.
 */

public class ExperienceAdapter extends BaseAdapter {

    String [] id_work;
    String [] name_work;
    Context context;
    LayoutInflater inflater;
    RegistrationAdapter adpt;

   public ExperienceAdapter(ExperienceActivity experience , String [] work_id , String [] work_name){

       context = experience;
       id_work =work_id;
       name_work =work_name;
       inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return name_work.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Work{
        TextView tv;
        ImageView delete;

    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Work work = new Work();
        View root =inflater.inflate(R.layout.work_list,null);
        work.tv =(TextView)root.findViewById(R.id.work_name);
        work.delete = (ImageView)root.findViewById(R.id.delete);
        work.tv.setText(name_work[i]);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.workinfo_id = Integer.parseInt(id_work[i]);
                Intent intent = new Intent(context , ExperienceDetail.class);
                intent.putExtra("item",id_work[i]);
                context.startActivity(intent);

            }
        });

        work.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecordworkexo(id_work[i]);
                String [] b = name_work;
                List<String> list = new ArrayList(Arrays.asList(name_work));
                List<String> list1 = new ArrayList(Arrays.asList(id_work));
                list.remove(i);
                list1.remove(i);
                name_work = (String[])list.toArray(new String[0]);
                id_work = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();

            }
        });

        return root;
    }
}
