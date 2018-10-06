package com.example.oguerisck.wearnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oguerisck.wearnotifications.DB.DbHelper;
import com.example.oguerisck.wearnotifications.Model.EventModel;
import com.example.oguerisck.wearnotifications.ViewHolder.EventAdapter;
import com.example.oguerisck.wearnotifications.ViewHolder.EventDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FloatingActionButton btn;
    private ListView mListView;
    private EventAdapter mAdapter;
    private ArrayList<EventModel> eventsData;
    private DbHelper DBTask;
    private int numEvent;

    private NotificationManagerCompat mNotificationManagerCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBTask = new DbHelper(this);

        //findViewById(R.id.fab).setOnClickListener(this);
        btn = (FloatingActionButton) findViewById(R.id.fab);
        mListView = (ListView) findViewById(R.id.eventsListView);
        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        eventsData = new ArrayList<>();

        try {
            eventsData = DBTask.getTaskList();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mAdapter = new EventAdapter(MainActivity.this , eventsData);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "click sur item : " + position, Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, NewEventActivity.class);
                startActivity(intent);
            }

        });

    }

    private void generateNotification(int numEvent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel principal";
            String description = "Channel de notifications de rappels";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1234", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1234")
                    .setSmallIcon(R.drawable.plus)
                    .setContentTitle("Click sur Event n° : " + numEvent)
                    .setContentText("some text")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Intent intent = new Intent(this, EventDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


            NotificationManagerCompat  mNotificationManager =  NotificationManagerCompat.from(this);


            mNotificationManager.notify(001, mBuilder.build());

        }
    }



    /*@Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i) {
            case R.id.fab : {Intent intent = new Intent(this, NewEventActivity.class);
                            startActivity(intent);
                            break;}
            case R.id.card_add_calendar_btn :

                generateNotification();
                break;
        }
    }*/

}



