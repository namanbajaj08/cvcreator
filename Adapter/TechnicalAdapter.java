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
import com.naman.resumemaker.ResumeCreateActivity.TechnicalDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TechnicalAdapter extends BaseAdapter {
    RegistrationAdapter adpt;
    public  String[] skills;
    private String[] id_tech;
    Context context;
    public static LayoutInflater layoutinflater = null;

    public TechnicalAdapter(TechnicalDetails add_profile , String[] id, String[] technical_skills){
        context=add_profile;
        skills=technical_skills;
        id_tech=id;
        layoutinflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public int getCount() {
        return skills.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Technical{
        TextView tv;
        ImageView delete;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Technical technical = new Technical();
        View root = layoutinflater.inflate(R.layout.technical_list,null);
        technical.tv = (TextView)root.findViewById(R.id.technical_name);
        technical.delete =(ImageView)root.findViewById(R.id.delete);
        technical.tv.setText(skills[i]);

        technical.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecordTechnical(  id_tech[i]);
                String [] b = skills;
                List<String> list = new ArrayList(Arrays.asList(skills));
                List<String> list1 = new ArrayList(Arrays.asList(id_tech));
                list.remove(i);
                list1.remove(i);
                skills = (String[])list.toArray(new String[0]);
                id_tech = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();
            }
        });
        return root;
    }
}
