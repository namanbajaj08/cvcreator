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
import com.naman.resumemaker.DetailedActivity.TrainingDetail;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.TrainingActivity;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SSB-2 on 03-10-2017.
 */

public class TrainingAdapter extends BaseAdapter {

    Context context;
    String [] id_training;
    String [] name_training;
    LayoutInflater inflater;
    RegistrationAdapter adapter;

    public TrainingAdapter(TrainingActivity training , String [] training_id ,String [] training_name){
        context =training;
        id_training =training_id;
        name_training = training_name;
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return name_training.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Industrial{
        TextView tv;
        ImageView delete;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Industrial industrial = new Industrial();
        View root =inflater.inflate(R.layout.training_list,null);
        industrial.tv = (TextView)root.findViewById(R.id.training_name);
        industrial.delete = (ImageView)root.findViewById(R.id.delete);
        industrial.tv.setText(name_training[i]);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.training_id = Integer.parseInt(id_training[i]);
                Intent intent = new Intent(context , TrainingDetail.class);
                intent.putExtra("item",id_training[i]);
                context.startActivity(intent);
            }
        });

        industrial.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter = new RegistrationAdapter(context);
                adapter.deletOneRecordTraining(id_training[i]);
                String [] b = name_training;
                List<String> list = new ArrayList(Arrays.asList(name_training));
                List<String> list1 = new ArrayList(Arrays.asList(id_training));
                list.remove(i);
                list1.remove(i);
                name_training = (String[])list.toArray(new String[0]);
                id_training = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();
            }
        });
        return root;
    }
}
