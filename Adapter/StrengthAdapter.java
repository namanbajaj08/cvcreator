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
import com.naman.resumemaker.ResumeCreateActivity.StrengthActivty;
import com.naman.resumemaker.ResumeCreateActivity.TechnicalDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StrengthAdapter extends BaseAdapter {
    RegistrationAdapter adpt;
    public  String[] name_strength;
    private String[] id_strength;
    Context context;
    public static LayoutInflater layoutinflater = null;

    public StrengthAdapter(StrengthActivty add_strength , String[] strength_id, String[] strength_name){
        context = add_strength;
        name_strength = strength_name;
        id_strength = strength_id;
        layoutinflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public int getCount() {
        return name_strength.length;
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
        View root = layoutinflater.inflate(R.layout.strength_list,null);
        technical.tv = (TextView)root.findViewById(R.id.strength_name);
        technical.delete =(ImageView)root.findViewById(R.id.delete);
        technical.tv.setText(name_strength[i]);

        technical.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecordStrength(id_strength[i]);
                String [] b = name_strength;
                List<String> list = new ArrayList(Arrays.asList(name_strength));
                List<String> list1 = new ArrayList(Arrays.asList(id_strength));
                list.remove(i);
                list1.remove(i);
                name_strength = (String[])list.toArray(new String[0]);
                id_strength = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();
            }
        });
        return root;
    }
}
