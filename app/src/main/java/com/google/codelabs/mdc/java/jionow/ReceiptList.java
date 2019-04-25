package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptList extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String chosen_event;
    private static String receiptnumber;
    private static ArrayList<String> items;
    private static ArrayList<String> quantity;
    private static ArrayList<String> values;
    private static ArrayList<Double> eachDish;
    private static Map<String, Object> receiptmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_display);

        Intent intent = getIntent();
        chosen_event =  intent.getStringExtra(CropGalleryImage.chosenevent);
        receiptmap = (Map) intent.getSerializableExtra(CropGalleryImage.mappedreceipt);
        items = (ArrayList<String>) intent.getSerializableExtra(CropGalleryImage.ITEMS);
        quantity = (ArrayList<String>) intent.getSerializableExtra(CropGalleryImage.QUANTITY);
        values = (ArrayList<String>) intent.getSerializableExtra(CropGalleryImage.VALUES);
        Log.d("received",String.valueOf(items));
        Log.d("received",String.valueOf(quantity));
        Log.d("received",String.valueOf(values));

        TextView textView_bill = findViewById(R.id.total_bill);
        textView_bill.setText(receiptmap.get("Total").toString());

        Button splitButton = findViewById(R.id.split_button);
        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        ListView mListView = (ListView) findViewById(R.id.receipt_list);

        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.receipt_view_adapter, null);

            TextView textView_foodName = (TextView) convertView.findViewById(R.id.food1);
            TextView textView_quantity = (TextView) convertView.findViewById(R.id.dish_quantity);
            TextView textView_price = (TextView) convertView.findViewById(R.id.dish_price);
            TextView textView_total = (TextView) convertView.findViewById(R.id.total_price_for_dish);

            textView_foodName.setText(items.get(position));
            textView_quantity.setText(quantity.get(position));
            textView_price.setText(values.get(position));
            textView_total.setText(values.get(position));

            return convertView;
        }
    }

}
