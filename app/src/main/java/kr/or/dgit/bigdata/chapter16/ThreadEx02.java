package kr.or.dgit.bigdata.chapter16;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreadEx02 extends AppCompatActivity implements View.OnClickListener{
    ImageView startBtn;
    ImageView pauseBtn;
    TextView textView;
    EditText editView;

    boolean isFirst = true;
    MyAsncTask myAsncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_ex02);

        startBtn = (ImageView) findViewById(R.id.main_startBtn);
        pauseBtn = (ImageView) findViewById(R.id.main_pauseBtn);
        textView = (TextView) findViewById(R.id.main_textView);
        editView = (EditText) findViewById(R.id.main_edit_view);
        editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        startBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);

        myAsncTask = new MyAsncTask();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_startBtn:
                if(isFirst){
                    int value = Integer.parseInt(editView.getText().toString());
                    myAsncTask.isRun = true;
                    isFirst = false;
                    myAsncTask.execute(value);
                }else{
                    myAsncTask.isRun = true;
                }
                break;
            case R.id.main_pauseBtn:
                myAsncTask.isRun = false;
                break;
        }
    }

    class MyAsncTask extends AsyncTask<Integer, Integer, String>{
        boolean loopFlag = true;
        boolean isRun;

        @Override
        protected String doInBackground(Integer... integers) {
            int count = integers[0];
            while(loopFlag){
                SystemClock.sleep(1000);
                if(isRun){
                    count--;
                    publishProgress(count);
                    if(count == 0){
                        loopFlag = false;
                    }
                }
            }

            return "재진 부럽지?!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }
}
