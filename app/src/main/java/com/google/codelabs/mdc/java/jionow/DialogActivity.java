package com.google.codelabs.mdc.java.jionow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

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
        ArrayList<String> Events =  (ArrayList<String>)intent.getSerializableExtra(MainMenu.events);

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