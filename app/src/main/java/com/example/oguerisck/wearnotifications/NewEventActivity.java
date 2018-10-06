package com.example.oguerisck.wearnotifications;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oguerisck.wearnotifications.DB.DbHelper;
import com.example.oguerisck.wearnotifications.Model.EventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView event_title;
    private TextView event_desc;
    private TextView event_address;
    private TextView event_begin;
    private TextView event_end;

    private Date eBegin;
    private Date eEnd;

    private DatePickerDialog.OnDateSetListener beginDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private DbHelper DBTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);


        DBTask = new DbHelper(this);

        //TextViews
        event_title = (TextView) findViewById(R.id.event_title);
        event_desc = (TextView) findViewById(R.id.event_desc);
        event_address = (TextView) findViewById(R.id.event_address);
        event_begin = (TextView) findViewById(R.id.event_begin);
        event_end = (TextView) findViewById(R.id.event_end);

        //DatePicker
        event_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(NewEventActivity.this, R.style.AppTheme, beginDateSetListener, year, month, day);
                dialog.show();
            }
        });

        beginDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mMonth = month + 1;
                String bd = dayOfMonth + "/" + mMonth + "/" + year;
                event_begin.setText(bd);
            }
        };

        event_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog =   new DatePickerDialog(NewEventActivity.this, R.style.AppTheme, endDateSetListener, year, month, day);
                dialog.show();

            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mMonth = month + 1;
                String bd = dayOfMonth + "/" + mMonth + "/" + year;
                event_end.setText(bd);
            }
        };

        //Button
        findViewById(R.id.addEventBtn).setOnClickListener(this);
    }

    private boolean validateInput(){
        boolean valid = true;

        String title = event_title.getText().toString();
        if (TextUtils.isEmpty(title)) {
            event_title.setError("Requis.");
            valid = false;
        } else {
            event_title.setError(null);
        }

        String address = event_address.getText().toString();
        if (TextUtils.isEmpty(address)) {
            event_address.setError("Requis.");
            valid = false;
        } else {
            event_address.setError(null);
        }

        if (event_begin.toString() == null) {
            event_begin.setError("Requis.");
            valid = false;
        } else {
            event_begin.setError(null);
        }

        if (event_end.toString() == null) {
            event_end.setError("Requis.");
            valid = false;
        } else {
            event_end.setError(null);
        }

        return valid;

    }

    private void postEvent(){
        if (!validateInput()) {
            return;
        }
        String title = event_title.getText().toString();
        String desc = event_desc.getText().toString();
        String address = event_address.getText().toString();
        String dateB = event_begin.getText().toString();
        String dateD = event_end.getText().toString();

        DBTask.insertNewTask(title, desc, address, dateB, dateD);
        ArrayList<EventModel> etat;
        etat = new ArrayList<EventModel>();

        try {
            etat = DBTask.getTaskList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, etat.toString(),
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.addEventBtn){
            postEvent();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
