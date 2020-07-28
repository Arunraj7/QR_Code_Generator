package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import android.graphics.drawable.BitmapDrawable;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {
    EditText qrvalue;
    Button generateBtn, scanBtn, downbtn;
    ImageView qrImage;
    Bitmap qrBits;
    Bitmap bitmap;
    private static  final int WRITE_EXTERNAL_STORAGE_CODE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrvalue = findViewById(R.id.EnterValue);
        generateBtn = findViewById(R.id.button);
        scanBtn = findViewById(R.id.button2);
        qrImage = findViewById(R.id.imageView);
        downbtn = findViewById(R.id.button3);
        downbtn.setVisibility(View.GONE);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                String[] permission = (Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
//            }
//        }



        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrvalue.getText().toString();
                if (data.isEmpty()) {
                    qrvalue.setError("Value Required");
                } else {
                    downbtn.setVisibility(View.VISIBLE);

                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 700);
                    qrBits = qrgEncoder.getBitmap();
                    qrImage.setImageBitmap((qrBits));
                }
            }
        });
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), scanner.class));
            }
        });

        downbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ((BitmapDrawable)qrImage.getDrawable()).getBitmap();
                String time = String.valueOf(qrvalue.getText());
                File path = Environment.getExternalStorageDirectory();
                File dir = new File(path+"/DCIM");
                dir.mkdirs();
                String imagename = time+".png";
                File file = new File(dir, imagename);
                OutputStream out;

                try {
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
                    out.flush();
                    out.close();
                    Toast.makeText(MainActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}