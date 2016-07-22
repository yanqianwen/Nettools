package com.qianfeng.nettools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
public class PingActivity extends AppCompatActivity {

    private EditText editText_ipaddress;
    private EditText editText_port;
    private TextView connect_result;
    private Button button_connect;
    private ScrollView scv;
    private final static int OK = 1;
    private final static int ERROR = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int msg_num = msg.what;
            switch (msg_num) {
                case OK:
                    String port1= (String) msg.obj;
                    connect_result.setText(connect_result.getText()+"\n"+port1+getResources().getString(R.string.socket_connect_ok));
                    button_connect.setEnabled(true);
                    break;
                case ERROR:
                    String port2=(String)msg.obj;
                    connect_result.setText(connect_result.getText()+"\n"+port2+getResources().getString(R.string.socket_connect_error));
                    button_connect.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duankou);

        editText_ipaddress = (EditText) findViewById(R.id.editText_ipaddress);
        editText_port = (EditText) findViewById(R.id.editText_port);
        connect_result = (TextView) findViewById(R.id.connect_result);

        button_connect = (Button) findViewById(R.id.button_connect);
        button_connect.setOnClickListener(button_connect_Listener);
        scv=(ScrollView) findViewById(R.id.scv1);
        scv.scrollTo(0, BIND_AUTO_CREATE);

    }

    OnClickListener button_connect_Listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            connect_result.setText(getResources().getString(R.string.socket_connecting));
            button_connect.setEnabled(false);
            Thread thread = new Thread() {

                @Override
                public void run() {
                    String editport= doStringCheck(editText_port.getText().toString());
                    String[] port=Stringcast.stringcast(editport, ',');
                    int port1=Integer.parseInt(port[port.length-1]);
                    int port2=Integer.parseInt(port[0]);
                    int length=port1-port2;
                    Log.i("mtag", port1+"  "+port2+"  "+length);
                    for (int i = 0; i <=length; i++) {


                        try {


                            Socket socket = new Socket();
                            socket.connect(new InetSocketAddress(doStringCheck(editText_ipaddress.getText().toString()),
                                    i+port2), 10000);
                            Message msg=new Message();
                            msg.what=OK;
                            msg.obj=i+port2+" ";
                            handler.sendMessage(msg);

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                            Message msg=new Message();
                            msg.what=ERROR;
                            msg.obj=i+port2+" ";
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Message msg=new Message();
                            msg.what=ERROR;
                            msg.obj=i+port2+" ";
                            handler.sendMessage(msg);
                        }
                    }
                }
            };
            thread.start();
        }
    };

    public void click(View view){
        switch (view.getId()) {
            case R.id.button_qingkong:
                editText_ipaddress.setText("");
                editText_port.setText("");
                connect_result.setText("");
                break;

            default:
                break;
        }
    }

    private String doStringCheck(String string) {
        if (string.equals("") || string == null) {
            return "0";
        } else {
            return string;
        }
    }
}
