package com.example.sensor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.github.nisrulz.sensey.LightDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.TouchTypeDetector;

public class MainActivity extends AppCompatActivity {

    Switch s1,s2,s3;
    TextView tv;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.shake);
        s2 = findViewById(R.id.light);
        s3 = findViewById(R.id.touch);
        tv = findViewById(R.id.mysensor);

        /*intilization of Sensy API*/
        Sensey.getInstance().init(MainActivity.this);

        /*This is Shake Sensor*/
        final ShakeDetector.ShakeListener shakeListener = new ShakeDetector.ShakeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShakeDetected() {
                tv.setText("Shake Sensor Detected");

                CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String camID = null;

                try {
                    camID = manager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.setTorchMode(camID,true); // Flash Light Turn ON
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onShakeStopped() {
                tv.setText("Shake Sensor Stopped");
                CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String camID = null;
                try {
                    camID = manager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.setTorchMode(camID,false); // Flash Light Turn off
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }
        };

        /*handling CLick Event*/
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Sensey.getInstance().startShakeDetection(shakeListener);
                }else {
                    Sensey.getInstance().stopShakeDetection(shakeListener);
                }
            }
        });

        /*Light Sensor*/

        final LightDetector.LightListener lightListener = new LightDetector.LightListener() {
            @Override
            public void onDark() {
                tv.setText("Dark Room");
            }

            @Override
            public void onLight() {
                tv.setText("Light Room");
            }
        };

       s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                   Sensey.getInstance().startLightDetection(lightListener);
               }else {
                   Sensey.getInstance().stopLightDetection(lightListener);
               }
           }
       });

       /*Touch Sensor*/

        final TouchTypeDetector.TouchTypListener touchTypListener = new TouchTypeDetector.TouchTypListener() {
            @Override
            public void onDoubleTap() {
                tv.setText("Double Tap");
            }

            @Override
            public void onLongPress() {
                tv.setText("Long Press");
            }

            @Override
            public void onScroll(int i) {
                tv.setText("Scroll");
            }

            @Override
            public void onSingleTap() {
                tv.setText("Single Tap");
            }

            @Override
            public void onSwipe(int i) {
                tv.setText("Swipe");
            }

            @Override
            public void onThreeFingerSingleTap() {
                tv.setText("3 Fingers Single Tap");
            }

            @Override
            public void onTwoFingerSingleTap() {
                tv.setText("2 Fingers Single Tap");
            }
        };

        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Sensey.getInstance().startTouchTypeDetection(MainActivity.this,touchTypListener);
                }else {
                    Sensey.getInstance().stopTouchTypeDetection();
                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*To Stop your Sensy API*/
        Sensey.getInstance().stop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Sensey.getInstance().setupDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}