package com.qianfeng.nettools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DuankouActivity extends AppCompatActivity {

    TextView tv_result;
    EditText et_hostname;
    ScrollView scv;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    String data= (String) msg.obj;
                    tv_result.setText(tv_result.getText()+"\n"+data);

                    break;
                case 2:
                    tv_result.setText("网络异常");
                    break;

                default:
                    break;
            }

        };
    };

    private void doPing(String paramString) {
        new Thread() {
            public void run() {
                try {
                    String host=et_hostname.getText().toString();
                    String[] str=Stringcast.stringcast(host, ',');
                    for (int i = 0; i < str.length; i++) {

                        Process process = Runtime.getRuntime().exec("ping -c 3 " +str[i]);
                        BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));

                        String data = "";

                        while ((data = buf.readLine()) != null) {
                            Log.i("mtag", "====" + data);

                            Message msg=new Message();
                            msg.what=1;
                            msg.obj=(data+"\n");
                            handler.sendMessage(msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ping);
        tv_result = (TextView) findViewById(R.id.tv_result);
        et_hostname = (EditText) findViewById(R.id.et_hostname);
        scv=(ScrollView)findViewById(R.id.scv);
        scv.scrollTo(0, BIND_AUTO_CREATE);

    }


    public void onclicked(View v) {
        switch (v.getId()) {
            case R.id.kaishi:
               if(et_hostname.getText().toString().equals("")){
                   Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
               }else {
                   doPing(et_hostname.getText().toString());
                   Toast.makeText(this, "正在测试，请稍后。。。。", Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.qingchu:
                tv_result.setText("");
                et_hostname.setText("");
                break;


            default:
                break;
        }
    }

}
