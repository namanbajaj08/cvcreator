package com.naman.resumemaker.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman.resumemaker.CreateResume;
import com.naman.resumemaker.R;
import com.naman.resumemaker.ResumeCreateActivity.AcademicRecord;
import com.naman.resumemaker.ResumeCreateActivity.ApplicantActivity;
import com.naman.resumemaker.ResumeCreateActivity.CareerObjective;
import com.naman.resumemaker.ResumeCreateActivity.DeclerationActivity;
import com.naman.resumemaker.ResumeCreateActivity.ExperienceActivity;
import com.naman.resumemaker.ResumeCreateActivity.ExtraCurricular;
import com.naman.resumemaker.ResumeCreateActivity.HobbiesActivity;
import com.naman.resumemaker.ResumeCreateActivity.PersonalDetail;
import com.naman.resumemaker.ResumeCreateActivity.ProjectActivity;
import com.naman.resumemaker.ResumeCreateActivity.ReferenceActivity;
import com.naman.resumemaker.ResumeCreateActivity.RewardsActivty;
import com.naman.resumemaker.ResumeCreateActivity.StrengthActivty;
import com.naman.resumemaker.ResumeCreateActivity.TechnicalDetails;
import com.naman.resumemaker.ResumeCreateActivity.TrainingActivity;

public class    CustomAdapter  extends BaseAdapter{

    String [] name;
    int [] image;
    Context context;

    LayoutInflater inflater;


    public CustomAdapter(CreateResume createResume, int[] img, String[] attribute) {
    context = createResume;
        image=img;
        name=attribute;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  class Holder{

        ImageView img;
        TextView data;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        Holder holder = new Holder();
        final View root = inflater.inflate(R.layout.create_resume , null);
        holder.img=(ImageView)root.findViewById(R.id.image_view);
        holder.data=(TextView)root.findViewById(R.id.textView);
        holder.data.setText(name[i]);
        holder.img.setImageResource(image[i]);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(i)
                {
                    case 0:
                        Intent intent = new Intent(context , ApplicantActivity.class);
                        context.startActivity(intent);
                        break;

                    case 1:Intent intent_1 = new Intent(context , CareerObjective.class);
                        context.startActivity(intent_1);
                        break;
                    case 2:Intent intent_2  = new Intent(context , AcademicRecord.class);
                        context.startActivity(intent_2 );
                        break;
                    case 3:Intent intent_3  = new Intent(context , ExperienceActivity.class);
                        context.startActivity(intent_3 );
                        break;
                    case 4:Intent intent_4  = new Intent(context , TechnicalDetails.class);
                        context.startActivity(intent_4 );
                        break;
                    case 5:Intent intent_5  = new Intent(context , TrainingActivity.class);
                        context.startActivity(intent_5);
                        break;
                    case 6:Intent intent_6  = new Intent(context , ProjectActivity.class);
                        context.startActivity(intent_6);
                        break;
                    case 7:Intent intent_7  = new Intent(context , RewardsActivty.class);
                        context.startActivity(intent_7);
                        break;
                    case 8:Intent intent_8  = new Intent(context , ExtraCurricular.class);
                        context.startActivity(intent_8);
                        break;
                    case 9:Intent intent_9  = new Intent(context , HobbiesActivity.class);
                        context.startActivity(intent_9);
                        break;
                    case 10:Intent intent_10  = new Intent(context , StrengthActivty.class);
                        context.startActivity(intent_10);
                        break;
                    case 11:Intent intent_11  = new Intent(context , PersonalDetail.class);
                        context.startActivity(intent_11);
                        break;
                    case 12:Intent intent_12  = new Intent(context , ReferenceActivity.class);
                        context.startActivity(intent_12);
                        break;

                    case 13:Intent intent_13  = new Intent(context , DeclerationActivity.class);
                        context.startActivity(intent_13);
                        break;


                }


            }
        });

        return root;
    }
}
