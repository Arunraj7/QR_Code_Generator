package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {
    EditText qrvalue;
    Button generateBtn, scanBtn;
    ImageView qrImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrvalue = findViewById(R.id.EnterValue);
        generateBtn = findViewById(R.id.button);
        scanBtn = findViewById(R.id.button2);
        qrImage = findViewById(R.id.imageView);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qrvalue.getText().toString();
                if(data.isEmpty()){
                    qrvalue.setError("Value Required");
                }else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 700);
                    Bitmap qrBits = qrgEncoder.getBitmap();
                    qrImage.setImageBitmap((qrBits));
                }
            }
        });
    }
}