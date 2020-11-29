package com.forbitbd.customview.ui.camerax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.forbitbd.customview.R;
import com.forbitbd.customview.ui.main.MainActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CameraActivity extends AppCompatActivity {
    private static final int RC_CAMERA =5000;
    private Button btnStart,btnRotateCamera;

    private PreviewView mPreviewView;
    private ExecutorService cameraExecuter;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;

    private int ffff = CameraSelector.LENS_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraExecuter = Executors.newSingleThreadExecutor();

        mPreviewView = findViewById(R.id.viewFinder);
        btnStart = findViewById(R.id.camera_capture_button);
        btnRotateCamera = findViewById(R.id.rotate_camera);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HHHHHH",getBatchDirectoryName());
                SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date())+ ".jpg");

                ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();

                imageCapture.takePicture(outputFileOptions, cameraExecuter, new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Log.d("HHHHHH","onImageSaved Called"+file.getPath());
                        Log.d("HHHHHH","onImageSaved Called"+file.getAbsolutePath());
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.d("HHHHHH","onImageSaved Called");
                    }
                });
            }
        });

        btnRotateCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ffff==CameraSelector.LENS_FACING_BACK){
                    ffff = CameraSelector.LENS_FACING_FRONT;
                }else{
                    ffff = CameraSelector.LENS_FACING_BACK;
                }

                startPreview();
            }
        });



        getCameraPermission();
    }

    @AfterPermissionGranted(RC_CAMERA)
    private void getCameraPermission() {
        String[] perms = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            startPreview();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Need Camera Permission",
                    RC_CAMERA, perms);
        }
    }

    private void startPreview(){

        ListenableFuture<ProcessCameraProvider> processCameraProvider = ProcessCameraProvider.getInstance(this);
        processCameraProvider.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    cameraProviderFuture = ProcessCameraProvider.getInstance(CameraActivity.this);

                    // Camera provider is now guaranteed to be available
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                    cameraProvider.unbindAll();

                    // Set up the view finder use case to display camera preview
                    Preview preview = new Preview.Builder().build();

                    // Set up the capture use case to allow users to take photos
                    imageCapture = new ImageCapture.Builder()
                            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                            .build();



                    // Choose the camera by requiring a lens facing
                    CameraSelector cameraSelector = new CameraSelector.Builder()
                            .requireLensFacing(ffff)
                            .build();

                    // Attach use cases to the camera with the same lifecycle owner
                    Camera camera = cameraProvider.bindToLifecycle(
                            ((LifecycleOwner) CameraActivity.this),
                            cameraSelector,
                            preview,
                            imageCapture);


/*
                    OrientationEventListener listener = new OrientationEventListener(MainActivity.this) {
                        @Override
                        public void onOrientationChanged(int i) {
                            Log.d("HHHHH",i+" Orientation");
                        }
                    };

                    listener.enable();*/



                    // Connect the preview use case to the previewView
                    preview.setSurfaceProvider(
                            mPreviewView.getSurfaceProvider());
                } catch (InterruptedException | ExecutionException e) {
                    // Currently no exceptions thrown. cameraProviderFuture.get() should
                    // not block since the listener is being called, so no need to
                    // handle InterruptedException.
                }


            }
        }, ContextCompat.getMainExecutor(this));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public String getBatchDirectoryName() {

        String app_folder_path = "";
        app_folder_path = Environment.getExternalStorageDirectory().toString() + "/images";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {

        }

        return app_folder_path;
    }
}