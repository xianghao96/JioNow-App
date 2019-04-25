package com.google.codelabs.mdc.java.jionow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OutstandingPayments extends AppCompatActivity {

    private String testuser = MainMenu.USER;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query notebook_ref = db.collection("GlobalEvents")
            .whereArrayContains("OwedParticipants",testuser);
    public final String TAG = "Logcat";

    private PaymentsAdapter adapter;

    Button Pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outstanding_payments);
        Log.d("testuser",testuser);


        setUpRecyclerView();



    }

    private void setUpRecyclerView(){

        Query query = notebook_ref;

        FirestoreRecyclerOptions<Payments> options  = new FirestoreRecyclerOptions.Builder<Payments>()
                .setQuery(query, Payments.class)
                .build();

        adapter = new PaymentsAdapter(options, testuser);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
