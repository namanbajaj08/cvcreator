package com.naman.resumemaker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.HobbiesActivity;
import com.naman.resumemaker.ResumeCreateActivity.TechnicalDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HobbiesAdapter extends BaseAdapter {
    RegistrationAdapter adpt;
    public  String[] hobby;
    private String[] id_hobbies;
    Context context;
    public static LayoutInflater layoutinflater = null;

    public HobbiesAdapter(HobbiesActivity hobbiesActivity, String[] id, String[] hobbies){
        context=hobbiesActivity;
        hobby=hobbies;
        id_hobbies=id;
        layoutinflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public int getCount() {
        return hobby.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Hobby{
        TextView tv;
        ImageView delete;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Hobby hb = new Hobby();
        View root = layoutinflater.inflate(R.layout.hobbies_list,null);
        hb.tv = (TextView)root.findViewById(R.id.hobby_name);
        hb.delete =(ImageView)root.findViewById(R.id.delete);
        hb.tv.setText(hobby[i]);

        hb.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecordHobby(id_hobbies[i]);
                String [] b = hobby;
                List<String> list = new ArrayList(Arrays.asList(hobby));
                List<String> list1 = new ArrayList(Arrays.asList(id_hobbies));
                list.remove(i);
                list1.remove(i);
                hobby = (String[])list.toArray(new String[0]);
                id_hobbies = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();
            }
        });
        return root;
    }
}
