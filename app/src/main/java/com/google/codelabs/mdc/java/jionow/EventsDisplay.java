package com.google.codelabs.mdc.java.jionow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class EventsDisplay extends AppCompatActivity {

    private String testuser = ProductGridFragment.USER;;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query events_ref = db.collection("GlobalEvents")
            .whereArrayContains("Participants",testuser);
    public final String TAG = "Logcat";

    private EventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        setUpRecyclerView();

    }

    private void setUpRecyclerView(){

        Query query = events_ref;

        FirestoreRecyclerOptions<Events> options  = new FirestoreRecyclerOptions.Builder<Events>()
                .setQuery(query, Events.class)
                .build();

        adapter = new EventsAdapter(options, testuser);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_events);
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
