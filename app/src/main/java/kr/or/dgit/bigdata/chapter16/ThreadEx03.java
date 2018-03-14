package kr.or.dgit.bigdata.chapter16;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThreadEx03 extends AppCompatActivity {
    int value;
    TextView textView;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_ex03);
        textView = findViewById(R.id.tvValue);
    }


    public void mOnClick(View view) {
        new AccumulateTask().execute(100);
    }

    class AccumulateTask extends AsyncTask<Integer, Integer, Integer>{
        @Override
        protected void onPreExecute() {
            textView.setText(0 + "");
            progress = new ProgressDialog(ThreadEx03.this);
            progress.setTitle("Updating");
            progress.setMessage("Wait...");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setCancelable(true);
            progress.setProgress(value);
            progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    cancel(true);
                }
            });
            progress.show();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            while (!isCancelled()){
                value++;
                if(value < integers[0]){
                    publishProgress(value);
                }else{
                    break;
                }
                try{
                    Thread.sleep(50);
                }catch (InterruptedException e){

                }
            }

            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(value);
        }

        @Override
        protected void onCancelled() {
            progress.dismiss();
        }

        @Override
        protected void onPostExecute(Integer integer) {
           progress.dismiss();
           value = 0;
           textView.setText(integer + "");
        }


    }
}
