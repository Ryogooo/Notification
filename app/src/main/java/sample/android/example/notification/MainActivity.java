package sample.android.example.notification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;



    // 通知用ID
    private static final int NOTIFICATION_ID = 100;

    //チャンネルID
    private static final String CHANNEL_ID = "SAMPLE_CHANNEL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //これが無いとNotificationが動作しない
        //Android8.0以上で通知を配信するためには必須
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Pokeri Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        //ボタンを押したときの処理
        //シンプルな通知
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);
                //アイコンの設定(これは必須)
                builder.setSmallIcon(R.drawable.ic_launcher);
                //通知のタイトル
                builder.setContentTitle("タイトル");
                //通知の内容
                builder.setContentText("テキスト");
                //通知の優先度
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                //通知を表示する
                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID,builder.build());
            }
        });

        //長いテキストを表示
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder2 = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);
                builder2.setSmallIcon(R.drawable.ic_launcher);
                builder2.setContentText("テキスト");
                builder2.setContentTitle("タイトル");
                builder2.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("ああああああああああああああああああああああああああああああああああ" +
                        "ああああああああああああああああああああああああああああああああ"));
                builder2.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManager NotificationManager = (android.app.NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                NotificationManager.notify(NOTIFICATION_ID,builder2.build());
            }
        });

        //アクションボタンを追加した表示
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BroadcastReceiver.class);
                intent.putExtra("message","ブロードキャストレシーバーのテスト");
                PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setContentTitle("タイトル");
                builder.setContentText("テキスト");
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setContentIntent(snoozePendingIntent);
                builder.addAction(R.drawable.ic_launcher,getString(R.string.snooze),snoozePendingIntent);

                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID,builder.build());
            }
        });


    }

}

