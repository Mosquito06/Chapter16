package kr.or.dgit.bigdata.chapter16;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreadEx01 extends AppCompatActivity implements View.OnClickListener{
    ImageView startBtn;
    ImageView pauseBtn;
    TextView textView;

    boolean loopFlag = true;
    boolean isFirst = true;
    boolean isRun;
    MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_ex01);

        startBtn = (ImageView) findViewById(R.id.main_startBtn);
        pauseBtn = (ImageView) findViewById(R.id.main_pauseBtn);
        textView = (TextView) findViewById(R.id.main_textView);

        startBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);

        thread = new MyThread();
    }


    @Override
    public void onClick(View view) {
        if (view == startBtn) {
            if (isFirst) {
                isFirst = false;
                isRun = true;
                thread.start();
            } else {
                isRun = true;
            }
        } else if (view == pauseBtn) {
            isRun = false;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                textView.setText((String.valueOf(msg.arg1)));
            }else if(msg.what == 2){
                textView.setText((String) msg.obj);
            }
        }


    };


    class MyThread extends Thread{
        @Override
        public void run() {
            try{
                int count = 10;
                while(loopFlag){
                    Thread.sleep(1000);
                    if(isRun){
                        count--;
                        /*Message message = Message.obtain();
                        message.what = 1;
                        message.arg1 = count;*/
                        Message message = handler.obtainMessage(1, count, 0);

                        handler.sendMessage(message);
                        if(count == 0){
                           /* message = Message.obtain();
                            message.what = 2;
                            message.obj = "Finish!!";*/
                           message = handler.obtainMessage(2, "Finish!!");
                            handler.sendMessage(message);

                            loopFlag = false;
                        }
                    }
                }
            }catch(Exception e){

            }
        }
    }
}
