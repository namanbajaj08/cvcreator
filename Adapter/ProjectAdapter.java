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
import com.naman.resumemaker.DetailedActivity.ProjectDetails;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.ProjectActivity;
import com.naman.resumemaker.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProjectAdapter extends BaseAdapter {

    Context context;
    String [] id_project;
    String [] name_project;
    LayoutInflater inflater;
    RegistrationAdapter adapter;

    public ProjectAdapter(ProjectActivity project , String [] project_id , String [] project_name){
        context =project;
        id_project =project_id;
        name_project = project_name;
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return name_project.length;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class Project{
        TextView tv;
        ImageView delete;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Project project = new Project();
        View root = inflater.inflate(R.layout.project_list,null);
        project.tv = (TextView)root.findViewById(R.id.project_name);
        project.delete = (ImageView)root.findViewById(R.id.delete);
        project.tv.setText(name_project[i]);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.project_id = Integer.parseInt(id_project[i]);
                Intent intent = new Intent(context , ProjectDetails.class);
                intent.putExtra("item",id_project[i]);
                context.startActivity(intent);
            }
        });
        
        project.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter = new RegistrationAdapter(context);
                adapter.deletOneRecordProject(id_project[i]);
                String [] b = name_project;
                List<String> list = new ArrayList(Arrays.asList(name_project));
                List<String> list1 = new ArrayList(Arrays.asList(id_project));
                list.remove(i);
                list1.remove(i);
                name_project = (String[])list.toArray(new String[0]);
                id_project = (String[])list1.toArray(new String[0]);
                notifyDataSetChanged();
            }
        });
        return root;
    }
}
