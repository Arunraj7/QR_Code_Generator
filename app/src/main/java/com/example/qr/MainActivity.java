package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

        downbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStoragePermissionGranted(MainActivity.this);

            }
        });



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








    }
    public void downloadsOpt(){
        bitmap = ((BitmapDrawable)qrImage.getDrawable()).getBitmap();
        String time = String.valueOf(qrvalue.getText());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/DCIM/QRCode");
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
    public void onRequestPermissionResult(int requestcode, String[] permission, int[] grantResults){
        super.onRequestPermissionsResult(requestcode, permission, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted ", Toast.LENGTH_SHORT).show();

        }else if(grantResults[0] == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Write External Storage Permission Required", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean isStoragePermissionGranted(Activity activity){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                downloadsOpt();

                return true;
            }
            else{
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                return false;
            }
        }else{

            return true;
        }

    }
}



