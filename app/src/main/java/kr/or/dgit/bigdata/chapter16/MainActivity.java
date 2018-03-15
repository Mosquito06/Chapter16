package kr.or.dgit.bigdata.chapter16;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datas = new ArrayList<>();

        listView = findViewById(R.id.main_list_view);
        datas.add("ThreadEx01");
        datas.add("ThreadEx02");
        datas.add("ThreadEx03");
        datas.add("ThreadEx04");
        datas.add("Mission01");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), getPackageName() + "." + datas.get(i).toString());
        startActivity(intent);
    }


}
