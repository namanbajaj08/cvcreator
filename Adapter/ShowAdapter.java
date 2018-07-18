package com.naman.resumemaker.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.resumemaker.R;
import com.naman.resumemaker.ShowResume;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by SSB-2 on 30-10-2017.
 */

public class ShowAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> list;
    LayoutInflater inflater;

    public ShowAdapter(ShowResume resume , ArrayList<String > resumelist){
        context = resume;
        list = resumelist;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class ResumeList{
        TextView tv;
        ImageView share;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final String st = (String)list.get(i);
        ResumeList resumeList = new ResumeList();
        final View root = inflater.inflate(R.layout.resume_list,null);
        resumeList.tv = (TextView)root.findViewById(R.id.resume);
        resumeList.share = (ImageView)root.findViewById(R.id.share);

        resumeList.tv.setText(list.get(i));

        resumeList.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    try {
                        Uri uri = Uri.parse("file://" + ShowResume.path + "/" + st);
                        File f = new File(uri.getPath());

                        Uri openPdf = /*Uri.parse("file://" + ShowResume.path + "/" + st)*/FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
                        Intent intent = new Intent();
/*
                    intent.setAction("android.intent.action.VIEW");
*/
                        intent.setDataAndType(openPdf, "pdf");
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        context.startActivity(intent);
                    } catch (Exception e) {

                        Toast.makeText(context , "You Have No Application To Open Pdf File",Toast.LENGTH_LONG).show();
                        Log.d("Erroris---->", e + "");
                    }
                }
                else
                {
                    try{

                        Uri openPdf = Uri.parse("file://" + ShowResume.path + "/" + st);
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setDataAndType(openPdf, "application/pdf");
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        context.startActivity(intent);
                    } catch (Exception e) {

                        Toast.makeText(context , "You Have No Application To Open Pdf File",Toast.LENGTH_LONG).show();
                        Log.d("Erroris---->", e + "");
                    }
                }
            }
        });


        resumeList.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT>=24){
                    try{
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                Uri uri = Uri.fromFile((new File(ShowResume.path + "/" +st)));
                Intent sharing = new Intent();
                sharing.setAction(Intent.ACTION_SEND);
                sharing.setType("text/plain");
                sharing.putExtra(Intent.EXTRA_STREAM , uri);
                context.startActivity(sharing);

            }
        });

        return root;
    }
}
