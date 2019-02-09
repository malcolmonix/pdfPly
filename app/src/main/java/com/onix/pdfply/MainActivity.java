package com.onix.pdfply;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.radaee.util.RadaeePDFManager;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button m_btn_file;


    private RadaeePDFManager mPDFManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPDFManager = new RadaeePDFManager();
        //plz set this line to Activity in AndroidManifes.xml:
        //    android:configChanges="orientation|keyboardHidden|screenSize"
        //otherwise, APP shall destroy this Activity and re-create a new Activity when rotate.
        RelativeLayout layout = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        m_btn_file = (Button)layout.findViewById(R.id.open_btn);

        m_btn_file.setOnClickListener(this);

        setContentView(layout);
        verifyPermissions(this);
    }

    private void verifyPermissions(Activity act) {
        // Check if we have necessary permissions
        int storagePermissionWrite = ActivityCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storagePermissionRead = ActivityCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE);

        ArrayList<String> permissions = new ArrayList<>();

        if (storagePermissionWrite != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        if (storagePermissionRead != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permissions.size() > 0) {
            String[] permissionArray = new String[permissions.size()];
            permissions.toArray(permissionArray);

            ActivityCompat.requestPermissions(act, permissionArray, 1);
        }
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onDestroy() {
        com.radaee.pdf.Global.RemoveTmp();
        super.onDestroy();
    }

    @Override
    public void onClick(View arg0) {
        if( arg0 == m_btn_file ) {
            Intent intent = new Intent(this, com.radaee.reader.PDFNavAct.class);
            startActivity(intent);
        }
    }
}