package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ReceiptList extends AppCompatActivity {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String chosen_event;
    private static int receiptnumber;
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

        getReceiptNumber();

        TextView textView_bill = findViewById(R.id.total_bill);
        textView_bill.setText(receiptmap.get("Total").toString());

        Button splitButton = findViewById(R.id.split_button);
        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
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
                                        docRef.update("Receipt"+String.valueOf(receiptnumber), receiptmap);
                                        ArrayList<String> owedusers = new ArrayList<String>();
                                        ArrayList<String> amount = new ArrayList<String>();
                                        if (document.get("Owed") != null){
                                            Map<String, Object>  Owed = (HashMap) document.get("Owed");
                                            for ( String key : Owed.keySet() ) {
                                                owedusers.add(key);
                                                amount.add(String.valueOf(Owed.get(key)));
                                            }
                                        }

                                        Map<String, Object>  NewOwed = new HashMap<String, Object>();
                                        List participants = (List) document.get("Participants");
                                        double billperpax = CropGalleryImage.roundAvoid(
                                                ((double) receiptmap.get("Total")/participants.size()),2);
                                        docRef.update("OwedParticipants",participants);
                                        docRef.update("Owed",FieldValue.delete());
                                        //Put receipt amounts into temporary owed map
                                        for (int i = 0; i< participants.size(); i++){
                                                NewOwed.put( String.valueOf(participants.get(i)),
                                                        String.valueOf(billperpax));
                                        }
                                        //Update temporary owed map with existing firestore owed details
                                        for (int i = 0; i< participants.size(); i++){
                                            if (owedusers.size() == 0){
                                                NewOwed.put( String.valueOf(participants.get(i)),
                                                        String.valueOf(billperpax));
                                            }
                                            for (int j = 0 ; j<owedusers.size(); j++) {
                                                if (participants.get(i).equals(owedusers.get(j))){
                                                    NewOwed.put( String.valueOf(participants.get(i)),
                                                            String.valueOf(billperpax + Double.parseDouble(amount.get(j))));
                                                }
                                            }
                                        }
                                        Log.d("abcdef",String.valueOf(NewOwed));

                                        //Remove host from owed list
                                        NewOwed.remove(MainMenu.USER);
                                        //Push to firestore
                                        docRef.update("Owed",NewOwed);
                                        finish();
                                        Toast toast = Toast.makeText(v.getContext(),
                                                "Bill Split! Upload another receipt", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
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

    private static void getReceiptNumber(){
        db.collection("GlobalEvents")
                .whereEqualTo("Name", chosen_event)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                receiptnumber = 1;
                                for (int i = 1; i<=5; i++){
                                    String receiptname = "Receipt"+i;
                                    if (document.get(receiptname)!= null){
                                        receiptnumber += 1;
                                    }
                                }
                                Log.d("ReceiptNumber",String.valueOf(receiptnumber));
                            }
                        } else {
                            Log.d("ReceiptNumber", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
