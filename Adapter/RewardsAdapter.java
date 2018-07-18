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
import com.naman.resumemaker.DetailedActivity.RewardsDetail;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.RewardsActivty;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RewardsAdapter extends BaseAdapter {

    String [] id_reward;
    String [] name_reward;
    Context context;
    LayoutInflater inflater;
    RegistrationAdapter adpt;

    public RewardsAdapter(RewardsActivty record , String [] reward_id , String [] reward_name){
        context = record;
        id_reward = reward_id;
        name_reward = reward_name;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return name_reward.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Reward{
        TextView tv;
        ImageView delete;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Reward reward = new Reward();
        View root = inflater.inflate(R.layout.reward_list,null);
        reward.tv =(TextView)root.findViewById(R.id.reward_name);
        reward.delete = (ImageView)root.findViewById(R.id.delete);
        reward.tv.setText(name_reward[i]);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.rewards_id = Integer.parseInt(id_reward[i]);
                Intent intent = new Intent(context , RewardsDetail.class);
                intent.putExtra("item",id_reward[i]);
                context.startActivity(intent);
            }
        });

        reward.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adpt = new RegistrationAdapter(context);
                adpt.deletOneRecordRewards(id_reward[i]); //change
                String [] b = name_reward;
                List<String> list = new ArrayList(Arrays.asList(name_reward));
                List<String> list1 = new ArrayList(Arrays.asList(id_reward));
                list.remove(i);
                list1.remove(i);
                name_reward = (String[])list.toArray(new String[0]);
                id_reward = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();

            }
        });


        return root;
    }


}
