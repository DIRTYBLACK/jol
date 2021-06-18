package com.example.socattest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView recieveText;
    Button button, btnon, btnoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("IoT 가습기 학교용");

        recieveText = (TextView) findViewById(R.id.recieveText);
        button = (Button)findViewById(R.id.btnConnect);
        btnon = (Button)findViewById(R.id.btnOn);
        btnoff = (Button)findViewById(R.id.btnOff);

        //버튼을 눌렀을경우 발생하는 이벤트 정의
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClientTask myClientTask = new MyClientTask("192.168.100.242", // 라즈베리파이의 ip 주소로 8091포트에 sensor 라는 텍스트를 보냅니다.
                        Integer.parseInt("8091"), "sensor");
                myClientTask.execute(); // 처음 버튼 클릭했을시에 소켓통신에 연결
            }
        });

        btnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    OnTask onTask = new OnTask("192.168.100.242", // 라즈베리파이의 ip 주소로 8091포트에 on 이라는 텍스트를 보냅니다.
                            Integer.parseInt("8091"), "on");
                    onTask.execute(); // 처음 버튼 클릭했을시에 소켓통신에 연결

                    Toast.makeText(getApplicationContext(),"가습기 전원을 킵니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    OffTask offTask = new OffTask("192.168.100.242", // 라즈베리파이의 ip 주소로 8091포트에 off 라는 텍스트를 보냅니다.
                            Integer.parseInt("8091"), "off");
                    offTask.execute(); // 처음 버튼 클릭했을시에 소켓통신에 연결

                    Toast.makeText(getApplicationContext(),"가습기 전원을 끕니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        //소켓 통신에 쓰이는 변수들을 정의
        String dstAddress;
        int dstPort;
        String response = "";
        String myMessage = "";

        //constructor
        MyClientTask(String addr, int port, String message) {
            dstAddress = addr;
            dstPort = port;
            myMessage = message;
        }
        @Override
        protected Void doInBackground(Void... arg0) { //송수신 하는 데이터들을 받아오는 통신 속도와 어떤 형식으로 받아올지 정의

            Socket socket= null;
            myMessage = myMessage.toString();
            try {
                socket = new Socket(dstAddress, dstPort);
                //송신
                OutputStream out = socket.getOutputStream();
                out.write("sensor".getBytes());

                //수신
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                int bytesRead;
                InputStream inputStream = socket.getInputStream();
                /*
                 * notice:
                 * inputStream.read() will block if no data return
                 */
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }
                response = "지금 내방은  \n" + response;

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            recieveText.setText(response);
            super.onPostExecute(result);
            if (recieveText.getText().toString().contains("no water")) { //소캣서버에서 no water라는 값이 들어올경우에 이벤트 생성
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//오레오 이상 버전에서는 채널을 만들어줘야 알림이 생성가능 해서 채널을 생성
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    String Noti_Channel_ID = "Noti";
                    String Noti_Channel_Group_ID = "Noti_Group";
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel notificationChannel = new NotificationChannel(Noti_Channel_ID, Noti_Channel_Group_ID, importance);
                    if (notificationManager.getNotificationChannel(Noti_Channel_ID) != null) {
                    } else {                                                                 //채널이 없을시에 채널을 생성해줍니다.
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                    notificationManager.createNotificationChannel(notificationChannel);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Noti_Channel_ID) //알림 설정 하는부분
                            .setLargeIcon(null).setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setWhen(System.currentTimeMillis()).setShowWhen(true)
                            .setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_MAX)
                            .setContentTitle("물이 부족 합니다!")
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setContentText("가습기에 지금 물이 없습니다 물을 체워 주세요^^");
                    notificationManager.notify(0, builder.build()); // 알림 생성하기

                }
            }
        }
    }

    public class OnTask extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;


        //constructor
        OnTask(String addr, int port, String message) {
            dstAddress = addr;
            dstPort = port;

        }
        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                //송신
                OutputStream out = socket.getOutputStream();
                out.write("on".getBytes());

            }
            catch (UnknownHostException e) {

            } catch (IOException e) {

            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    public class OffTask extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;

        //constructor
        OffTask(String addr, int port, String message) {
            dstAddress = addr;
            dstPort = port;
        }
        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                //송신
                OutputStream out = socket.getOutputStream();
                out.write("off".getBytes());

            }
            catch (UnknownHostException e) {

            } catch (IOException e) {

            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}

