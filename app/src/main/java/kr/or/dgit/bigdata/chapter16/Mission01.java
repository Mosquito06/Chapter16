package kr.or.dgit.bigdata.chapter16;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Mission01 extends AppCompatActivity implements DialogInterface.OnClickListener{
    ImageView imageView;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission01);



        imageView = findViewById(R.id.main_photo_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Log.d("진입?", "OK");

                if(ContextCompat.checkSelfPermission(Mission01.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Mission01.this);
                    builder.setTitle("촬용");
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

    }
}
