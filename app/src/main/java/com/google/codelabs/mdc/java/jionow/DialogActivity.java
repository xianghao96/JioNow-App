package com.google.codelabs.mdc.java.jionow;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class DialogActivity extends Activity{

    public final static String event = "Event";
    public static ArrayList<String> list = new ArrayList<>();
    public static String chosen_event;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.floating_activity);

        Intent intent = getIntent();
        ArrayList<String> Events =  (ArrayList<String>)intent.getSerializableExtra(ProductGridFragment.events);

        list.clear();
        for (int i= 0; i<Events.size();i++){
            list.add(String.valueOf(Events.get(i)));
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list);

        spinner.setAdapter(adapter);


        Button next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosen_event = spinner.getSelectedItem().toString();
                Context context = v.getContext();
                Intent intent = new Intent(context, CropGalleryImage.class);
                intent.putExtra(event, chosen_event);
                context.startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        list.clear();
    }
}