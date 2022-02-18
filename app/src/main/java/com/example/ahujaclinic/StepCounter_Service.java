package com.example.ahujaclinic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class StepCounter_Service extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean running = false;
    private int total_steps = 0;
    private int previous_total_steps = 0, current_steps = 0;
    private String phone;
    private Sensor step_sensor;
    private DatabaseReference reference_fitness;
    private Intent inten;
    private PendingIntent pendingIntent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reference_fitness = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Fitness");
        Patient_Session_Management session = new Patient_Session_Management(StepCounter_Service.this);
        phone = session.getSession();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        step_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(step_sensor != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel notificationChannel = new NotificationChannel("Channelid1", "Your Steps", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(notificationChannel);
            }
            inten = new Intent(this, PhyziHealth_StepCounter.class);
            pendingIntent = PendingIntent.getActivity(this, 0, inten, 0);
            sensorManager.registerListener(StepCounter_Service.this, step_sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int countSteps = (int)event.values[0];
            if(previous_total_steps == 0){
                previous_total_steps = countSteps;
            }
            current_steps = countSteps-previous_total_steps;
            previous_total_steps = countSteps;
            String[] monthName = {"Jan", "Feb",
                    "Mar", "Apr", "May", "Jun", "Jul",
                    "Aug", "Sep", "Oct", "Nov",
                    "Dec"};
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DATE);
            String month = monthName[cal.get(Calendar.MONTH)];
            int year = cal.get(Calendar.YEAR);
            String value = day + " " + month + " " + year;
            reference_fitness.child("Steps").child(phone).child(value).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        int steps = snapshot.child("steps").getValue(Integer.class);
                        reference_fitness.child("Steps").child(phone).child(value).child("steps").setValue(steps+current_steps);
                    }
                    else{
                        Integer steps = current_steps;
                        reference_fitness.child("Steps").child(phone).child(value).child("steps").setValue(steps);
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
