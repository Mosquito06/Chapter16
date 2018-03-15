package kr.or.dgit.bigdata.chapter16;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Mission01 extends AppCompatActivity implements DialogInterface.OnClickListener{
    ImageView imageView;
    EditText editText;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission01);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout);


        imageView = findViewById(R.id.main_photo_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Log.d("진입?", "OK");

                if(ContextCompat.checkSelfPermission(Mission01.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Mission01.this);
                    builder.setTitle("촬영");
                    builder.setCancelable(false);
                    builder.setItems(new CharSequence[] {"사진 촬용", "동영상 촬용"}, Mission01.this);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    ActivityCompat.requestPermissions(Mission01.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        });

    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == 0){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 10);

        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 10){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            ImageView imageView = new ImageView(Mission01.this);
            imageView.setImageBitmap(bitmap);

            layout.addView(imageView, params);
        }
    }
}
