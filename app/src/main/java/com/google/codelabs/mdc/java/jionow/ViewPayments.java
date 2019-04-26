package com.google.codelabs.mdc.java.jionow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPayments extends AppCompatActivity {

    private static String chosen_event;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    static Map<String, Object> receiptmap;
    private static ArrayList<String> owedusers = new ArrayList<String>();
    private static ArrayList<String> amount = new ArrayList<String>();
    ListView mListView;
    public static CustomAdapter customAdapter;
    private static Map<String, Object> Owed = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpayments);
        owedusers.clear();
        amount.clear();

        Intent intent = getIntent();
        String owedmap =  intent.getStringExtra(PaymentsAdapter.OWEDPAYMENTS);
        Log.d("abcdef", owedmap);
        owedmap = owedmap.substring(1, owedmap.length()-1);           //remove curly brackets
        String[] keyValuePairs = owedmap.split(",");              //split the string to creat key-value pairs

        Owed.clear();
        for(String pair : keyValuePairs)                        //iterate over the pairs
        {
            String[] entry = pair.split("=");                   //split the pairs to get key and value
            Owed.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
        }

        chosen_event =  intent.getStringExtra(PaymentsAdapter.EVENT);

        for ( String key : Owed.keySet() ) { ;
            owedusers.add(key);
            amount.add(String.valueOf(Owed.get(key)));
        }

        mListView = (ListView) findViewById(R.id.paymentlistView);

        customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);
        Button settle = findViewById(R.id.settlepayment);

    }

    public class CustomAdapter extends BaseAdapter implements ListAdapter {

        @Override
        public int getCount() {
            return owedusers.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.viewpayments_adapter, null);

            TextView textView_owedusers = (TextView) convertView.findViewById(R.id.owed_users);
            TextView textView_amount = (TextView) convertView.findViewById(R.id.owed_amount);
            Button settle = (Button) convertView.findViewById(R.id.settlepayment);

            textView_owedusers.setText(owedusers.get(position));
            textView_amount.setText(amount.get(position));

            settle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Owed.remove(owedusers.get(position));
                    //Write to firestore
                    Query eventref = db.collection("GlobalEvents").
                            whereEqualTo("Name",chosen_event);
                    eventref.get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            DocumentReference docRef =
                                                    db.collection("GlobalEvents").document(String.valueOf(document.getId()));
                                            docRef.update("Owed",FieldValue.delete());
                                            docRef.update("Owed",Owed);
                                            Log.d("updated",document.getId());
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    owedusers.remove(position);
                    amount.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

    }

}