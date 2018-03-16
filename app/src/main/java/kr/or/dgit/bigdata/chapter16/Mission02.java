package kr.or.dgit.bigdata.chapter16;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Mission02 extends AppCompatActivity implements View.OnClickListener{
    ImageView callImg;
    ImageView locationImg;
    ImageView internetImg;
    ImageView shareImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission02);

        callImg = findViewById(R.id.mission2_call);
        callImg.setOnClickListener(this);

        locationImg = findViewById(R.id.mission2_location);
        locationImg.setOnClickListener(this);

        internetImg = findViewById(R.id.mission2_internet);
        internetImg.setOnClickListener(this);

        shareImg = findViewById(R.id.mission2_share);
        shareImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.mission2_call:
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 10);
                break;
            case R.id.mission2_location:
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:37.5662952, 126.9779451"));
                startActivity(intent);
                break;
            case R.id.mission2_internet:
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://skykim10908.cafe24.com/My_portfolio"));
                startActivity(intent);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 10 && resultCode == RESULT_OK){
            String[] target = data.getDataString().split("/");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_URI + "/" + target[target.length - 1]));
            startActivity(intent);
        }
    }
}
