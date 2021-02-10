package com.example.qrcode;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class MainActivity extends AppCompatActivity {

    public TextView tvRes;
    private TextView tvRes0;
    private SurfaceView surfaceView;
    private QREader qrEader;
    private ToggleButton button;

    //public String androidIDFirst = android.provider.Settings.System.getString(this.getContentResolver(), Secure.ANDROID_ID);
    public static String androidID = "androidIDFirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvRes0 = findViewById(R.id.textView3);
        tvRes0.setText(androidID);

        initView();
        requestCameraPermission();
//        new Server().execute();
        //runServer();
    }

    private void runServer()
    {
        Thread t = new Thread(){
            @Override
            public void run()
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Server().execute();
                    }
                });
            }
        };
        t.start();
    }

    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        initCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Intent intent = new Intent(MainActivity.this, PermissionInfoActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void initView() {
        tvRes = findViewById(R.id.textView);
        button = findViewById(R.id.button2);
    }

    private void initQREader() {
        qrEader = new QREader.Builder(this, surfaceView, new QRDataListener() {
            @Override
            public void onDetected(String data) {
                tvRes.post(new Runnable() {
                    @Override
                    public void run() {
                        tvRes.setText(data);
                        if(!data.isEmpty())
                        {
                            new Server().execute();
                            runServer();
                        }
                    }
                });
            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(surfaceView.getHeight())
                .width(surfaceView.getWidth())
                .build();
    }

    private void initCamera() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrEader.isCameraRunning()) {
                    button.setChecked(false);
                    qrEader.stop();
                } else {
                    button.setChecked(true);
                    qrEader.start();
                }
            }
        });

        surfaceView = findViewById(R.id.surfaceView);
        initQREader();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (qrEader != null)
            qrEader.initAndStart(surfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (qrEader != null)
            qrEader.releaseAndCleanup();
    }
}