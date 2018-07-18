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
import com.naman.resumemaker.DetailedActivity.ReferenceDetails;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.ReferenceActivity;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SSB-2 on 03-10-2017.
 */

public class ReferenceAdapter extends BaseAdapter {

    Context context;
    String [] id_ref;
    String [] name_ref;
    LayoutInflater inflater;
    RegistrationAdapter adapter;

    public ReferenceAdapter(ReferenceActivity ref , String [] ref_id , String [] ref_name){
        context = ref;
        id_ref = ref_id;
        name_ref = ref_name;
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return name_ref.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Ref{
        TextView tv;
        ImageView delete;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Ref ref = new Ref();
        View root =inflater.inflate(R.layout.ref_list , null);
        ref.tv = (TextView)root.findViewById(R.id.ref_name);
        ref.delete = (ImageView)root.findViewById(R.id.delete);
        ref.tv.setText(name_ref[i]);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.ref_id = Integer.parseInt(id_ref[i]);
                Intent intent = new Intent(context , ReferenceDetails.class);
                intent.putExtra("item",id_ref[i]);
                context.startActivity(intent);
            }
        });
        ref.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new RegistrationAdapter(context);
                adapter.deletOneRecordRefer(id_ref[i]);
                String [] b = name_ref;
                List<String> list = new ArrayList(Arrays.asList(name_ref));
                List<String> list1 = new ArrayList(Arrays.asList(id_ref));
                list.remove(i);
                list1.remove(i);
                name_ref = (String[])list.toArray(new String[0]);
                id_ref = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();
            }
        });
        return root;
    }
}
