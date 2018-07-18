package com.naman.resumemaker;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edmodo.cropper.CropImageView;
import com.edmodo.cropper.cropwindow.handle.Handle;
import com.naman.resumemaker.ResumeCreateActivity.PersonalDetail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Crop_Image extends AppCompatActivity {

    String ImagePath;
    ImageView backdone;
    Bitmap bmp;
    ImageView btnDone;
    CropImageView cropImageView;
    Boolean isFromMain = Boolean.valueOf(false);
    private File mFileTemp;
    String pathimage;
    PersonalDetail ph;
    int screenHeight;
    int screenWidth;
    Uri selectedImageUri;
    ImageView skipe;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.crop_view);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView show= (TextView)findViewById(R.id.show_name);
        action = getIntent().getAction();



        cropImageView = (CropImageView)findViewById(R.id.crop_image);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setAspectRatio(5 , 7);
        new Handler().postDelayed(new crop() , 500);
        btnDone = (ImageView)findViewById(R.id.done);

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth =metrics.widthPixels;
        screenHeight =metrics.heightPixels;
        isFromMain = Boolean.valueOf(getIntent().getBooleanExtra("isFromMain", false));
        if (Util.selectedImageUri != null) {
            this.selectedImageUri = Util.selectedImageUri;
            try {
                this.bmp = BitmapCompression.getCorrectlyOrientedImage(getApplicationContext(), this.selectedImageUri, this.screenHeight);
                this.bmp = this.bmp.copy(Bitmap.Config.ARGB_8888, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                this.mFileTemp = new File(Environment.getExternalStorageDirectory(), Util.TEMP_FILE_NAME);
            } else {
                this.mFileTemp = new File(getFilesDir(), Util.TEMP_FILE_NAME);
            }
            this.bmp = BitmapCompression.decodeFile(this.mFileTemp, this.screenHeight, this.screenWidth);
            this.bmp = BitmapCompression.adjustImageOrientation(this.mFileTemp, this.bmp);
            this.bmp = this.bmp.copy(Bitmap.Config.ARGB_8888, true);
        }
        this.cropImageView.setImageBitmap(this.bmp);
        super.onCreate(savedInstanceState);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = cropImageView.getCroppedImage();
                if (Crop_Image.this.isFromMain.booleanValue()) {
                    Util.bits = cropImageView.getCroppedImage();
                    savephoto();
                    Util.flag = true;
                    finish();
                }
            }
        });

    }



    class crop implements Runnable{
        crop(){
        }
        @Override
        public void run() {
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void savephoto() {
        String root = Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "photo" + new Random().nextInt(100) + ".png";
        File file = new File(myDir, fname);
        Util.pathofimagae = new StringBuilder(String.valueOf(root)).append("/").append(fname).toString();
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            Util.bits.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri, new String[]{"_data"}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            String string = cursor.getString(column_index);
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void onBackPressed() {
        if (this.isFromMain.booleanValue()) {
            finish();
        } else {
            finish();
        }
    }

}
