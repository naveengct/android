package com.example.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import org.tensorflow.lite.Interpreter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.app.Activity;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.io.FileInputStream;
import android.content.res.AssetFileDescriptor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.nio.MappedByteBuffer;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    Sensor accelerometer,gyroscope;
    SensorManager sm,gy;
    TextView acceleration ,gyro,hi;
    Interpreter tflite;
    String modelFile="model.tflite";

    private static List<Float> x;
    private static List<Float> y;
    private static List<Float> z;

    float input[][][]=new float[1000][128][6];
    float output[][]= new float[1000][6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            tflite=new Interpreter(loadModelFile(MainActivity.this,modelFile));

        } catch (IOException e) {
            e.printStackTrace();
        }


        sm=(SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        acceleration=(TextView) findViewById(R.id.accerelation);

        gy=(SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        gy.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        gyro=(TextView) findViewById(R.id.gyro);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
        acceleration.setText(tflite+"\n\nAccelerometer:\n\nX:"+event.values[0]+
                              "\nY:"+event.values[1]+
                              "\nZ:"+event.values[2]);

        }

        else {
            gyro.setText(input[0][1]+"\n\nGyroscope!!:\n\nX:"+event.values[0]+
                    "\nY:"+event.values[1]+
                    "\nZ:"+event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private MappedByteBuffer loadModelFile(Activity activity,String MODEL_FILE) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
