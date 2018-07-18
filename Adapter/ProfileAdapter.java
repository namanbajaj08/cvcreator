package com.naman.resumemaker.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman.resumemaker.CreateProfile;
import com.naman.resumemaker.CreateResume;
import com.naman.resumemaker.Database.RegistrationAdapter;
import com.naman.resumemaker.R;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileAdapter extends BaseAdapter{

    RegistrationAdapter adpt;
    Cursor cursor;
    CreateProfile createProfile;
    public  static String[] name;
    private String[] id;
    Context context;
    public static LayoutInflater layoutinflater = null;

    public ProfileAdapter(CreateProfile add_profile ,String[] item_id,String[] profile){
        context=add_profile;
        name=profile;
        id=item_id;
        layoutinflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Hold{
        TextView tv;
        ImageView delete;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Hold hold= new Hold();
        final View root= layoutinflater.inflate(R.layout.profile_view,null);
        hold.tv=(TextView)root.findViewById(R.id.profile_name);
        hold.delete=(ImageView)root.findViewById(R.id.delete);
        hold.tv.setText(name[i]);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Util.resumeid=Integer.parseInt(ProfileAdapter.this.id[i]);
                Util.resumename=ProfileAdapter.this.name[i];
                Intent intent = new Intent(context, CreateResume.class);
                intent.putExtra("item",ProfileAdapter.this.id[i]);
                intent.putExtra("name",ProfileAdapter.this.name[i]);
                context.startActivity(intent);
            }
        });

        hold.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecord(id[i]);
                List<String> list = new ArrayList(Arrays.asList(name));
                List<String> list1 = new ArrayList(Arrays.asList(id));
                list.remove(i);
                list1.remove(i);
                name = (String[]) list.toArray(new String[0]);
                id = (String[]) list1.toArray(new String[0]);
                notifyDataSetChanged();

            }
        });
        return root;
    }
}
