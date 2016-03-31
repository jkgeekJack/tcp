package com.latiao.tap.teststring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.btn);
        tv= (TextView) findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Socket socket = null;
                        String message = tv.getText().toString() + "\r\n";
                        try
                        {
                            //创建Socket
                            socket = new Socket("119.29.136.149",9999); //查看本机IP,每次开机都不同
                            //向服务器发送消息
                            PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
                            out.println(message);

                            //接收来自服务器的消息
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String msg = br.readLine();

                            if ( msg != null )
                            {
                                tv.setText(msg);
                                Log.e("content",msg);
                            }
                            else
                            {
                                tv.setText("数据错误!");
                            }
                            //关闭流
                            out.close();
                            br.close();
                            //关闭Socket
                            socket.close();
                        }
                        catch (Exception e)
                        {
                            // TODO: handle exception
                            Log.e("error", e.toString());
                        }
                    }
                }).start();

            }
        });
    }
}
