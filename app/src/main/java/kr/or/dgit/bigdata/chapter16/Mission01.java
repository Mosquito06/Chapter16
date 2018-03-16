package kr.or.dgit.bigdata.chapter16;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mission01 extends AppCompatActivity implements DialogInterface.OnClickListener{
    ImageView imageView;
    EditText editText;
    RelativeLayout layout;
    String ExFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfile";
    String authorities = "kr.od.dgit.bigdata.chapter16.fileprovider";
    File imgFile;
    int reqWidth;
    int reqHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission01);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout);


        imageView = findViewById(R.id.main_photo_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(Mission01.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Mission01.this);
                    builder.setTitle("촬영");
                    builder.setCancelable(false);
                    builder.setItems(new CharSequence[] {"사진 촬영", "동영상 촬영"}, Mission01.this);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    ActivityCompat.requestPermissions(Mission01.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        });

        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == 0){
            try {
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String imgFileName = timeStamp + "_";
                File storeDir = new File(ExFolderPath);

                imgFile = File.createTempFile("IMG" + "-" + imgFileName, ".jpg", storeDir);
                if(!imgFile.exists()){
                    imgFile.createNewFile();
                }

                Uri uri = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    uri = FileProvider.getUriForFile(Mission01.this, authorities, imgFile);
                }else{
                    uri = Uri.fromFile(imgFile);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try{
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String imgFileName = timeStamp + "_";
                File storeDir = new File(ExFolderPath);

                imgFile = File.createTempFile("VIDEO" + "-" + imgFileName, ".mp4", storeDir);
                if(!imgFile.exists()){
                    imgFile.createNewFile();
                }

                Uri uri = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    uri = FileProvider.getUriForFile(Mission01.this, authorities, imgFile);
                }else{
                    uri = Uri.fromFile(imgFile);
                }

                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024*1024*10);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 20);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 10 && resultCode == RESULT_OK){
            if(imgFile != null){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                try {
                    InputStream in = new FileInputStream(imgFile);
                    BitmapFactory.decodeStream(in, null, options);
                    in.close();
                    in = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;

                if(height < reqHeight || width > reqWidth){
                    final int heightRatio = Math.round((float) height / (float) reqHeight);
                    final int widthRatio = Math.round((float) width / (float) reqWidth);

                    inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
                }

                BitmapFactory.Options imgOptions = new BitmapFactory.Options();
                imgOptions.inSampleSize = inSampleSize;
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), imgOptions);
                ImageView imgView = new ImageView(Mission01.this);
                imgView.setImageBitmap(bitmap);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layout.addView(imgView, params);
            }
        }else if(requestCode == 20 && resultCode == RESULT_OK){
            if(imgFile != null){
                Log.d("진입?", "ok?");

                VideoView videoView = new VideoView(Mission01.this);
                videoView.setMediaController(new MediaController(Mission01.this));

                Uri videoUri = Uri.parse(imgFile.getAbsolutePath());

                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                Bitmap bmp = null;

                retriever.setDataSource(imgFile.getAbsolutePath());
                bmp = retriever.getFrameAtTime();
                int videoHeight = bmp.getHeight();
                int videoWdith = bmp.getHeight();

                Log.d("높이", videoHeight + "");
                Log.d("너비", videoWdith + "");

                videoView.setVideoURI(videoUri);
                if(videoWdith >= videoHeight){
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(reqWidth, reqHeight);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    layout.addView(videoView, params);
                }else{
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(reqHeight, reqWidth);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    layout.addView(videoView, params);
                }

                videoView.start();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imgFile", imgFile.getAbsolutePath());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            String path = savedInstanceState.getString("imgFile");
            if(path != null){
                imgFile = new File(path);
            }
        }
    }
}
