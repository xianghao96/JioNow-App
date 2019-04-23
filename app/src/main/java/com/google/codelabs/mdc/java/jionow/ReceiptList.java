package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class ReceiptList extends AppCompatActivity {

    private static String chosen_event;
    private static String receiptnumber;
    static Map<String, Object> receiptmap;

    // Info scanned from receipt should replace this
    String[] foodNames = {"Chicken Rice", "Fried Rice", "Nuggets"};
    String[] quantity = {"2", "1", "5"};
    String[] price = {"$3", "$5", "$2.50"};
    String[] total = {"$6", "$5", "$12.50"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_display);

        Intent intent = getIntent();
        chosen_event =  intent.getStringExtra(CropGalleryImage.chosenevent);
        receiptmap = (Map) intent.getSerializableExtra(CropGalleryImage.mappedreceipt);
        Log.d("received",String.valueOf(receiptmap));

        ListView mListView = (ListView) findViewById(R.id.receipt_list);

        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return foodNames.length;
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

            textView_foodName.setText(foodNames[position]);
            textView_quantity.setText(quantity[position]);
            textView_price.setText(price[position]);
            textView_total.setText(total[position]);

            return convertView;
        }
    }

}
