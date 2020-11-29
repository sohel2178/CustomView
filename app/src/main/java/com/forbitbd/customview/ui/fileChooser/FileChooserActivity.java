package com.forbitbd.customview.ui.fileChooser;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.forbitbd.customview.R;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.opencsv.CSVReader;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.GregorianCalendar;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class FileChooserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOpen;
    private static final int READ_WRITE =7500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);

        btnOpen = findViewById(R.id.open);
        btnOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        getReadWritePermission();
    }

    @AfterPermissionGranted(READ_WRITE)
    private void getReadWritePermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            openFileDialog();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Need READ and Write Permission",
                    READ_WRITE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void openFileDialog(){
        new ChooserDialog(this)
                .withFilter(false,false,"csv")
                .withStartFile(Environment.getExternalStorageDirectory().getAbsolutePath())
                .withDateFormat("HH:mm")
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String dir, File dirFile) {
                        Toast.makeText(FileChooserActivity.this, "FOLDER: " + dir, Toast.LENGTH_SHORT).show();

                        try {
                            CSVReader reader = new CSVReader(new FileReader(dir));
                            String [] nextLine;
                            while ((nextLine = reader.readNext()) != null) {
                                // nextLine[] is an array of values from the line
                                System.out.println(nextLine[0] + nextLine[1] + "etc...");
                               /* Log.d("YYYYYYY","OKKKKKKK "+nextLine[0]);
                                Log.d("YYYYYYY","OKKKKKKK "+nextLine[4]);
                                Log.d("YYYYYYY","OKKKKKKK "+nextLine[5]);*/

                                getDate(nextLine[5]);

                                if(getDate(nextLine[5])!=null){
                                    Log.d("YYYYYY","YOOOO");
                                }



                            }




                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .build()
                .show();

       /* Log.d("YYYYYYY",Environment.getExternalStorageDirectory().getAbsolutePath()+" Dir");
        //Log.d("YYYYYYY",Environment.getExternalStoragePublicDirectory(null).getAbsolutePath()+" Dir");*/
    }

    private Date getDate(String date){
        String[] hhh = date.split("/");

        if(hhh.length!=3){
            hhh = date.split("-");
        }

        try {
            int year = Integer.parseInt(hhh[2].trim());
            int month = Integer.parseInt(hhh[1].trim())-1;
            int day = Integer.parseInt(hhh[0].trim());
            return new GregorianCalendar(year, month, day).getTime();
        }catch (Exception e){
            Log.d("YYYYYY",e.getMessage()+"");
            return null;
        }


//        Log.d("YYYYYYY",year+","+month+","+day);
    }
}