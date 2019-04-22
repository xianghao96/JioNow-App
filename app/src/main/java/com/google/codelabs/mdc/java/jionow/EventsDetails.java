package com.google.codelabs.mdc.java.jionow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class EventsDetails extends AppCompatActivity {

    public final String TAG = "Logcatevents";
    private String eventname = "Beer Fest";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView textViewEventDetailsName;
    private TextView textViewEventDetailsDescription;
    private TextView textViewEventDetailsStartdate;
    private TextView textViewEventDetailsEnddate;
    private TextView textViewEventDetailsHost;
    private TextView textViewEventDetailsParticipants;
    private Button buttonViewViewBill;
    String Host;
    String Description;
    String participantsList ="Participants: \n";
    Timestamp StartDate;
    Timestamp EndDate;
    List Participants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdetails);

        Intent intent = getIntent();
        eventname = intent.getStringExtra(EventsAdapter.EVENT_NAME);

        textViewEventDetailsName = findViewById(R.id.text_view_event_details_name);
        textViewEventDetailsDescription = findViewById(R.id.text_view_event_details_description);
        textViewEventDetailsStartdate = findViewById(R.id.text_view_event_details_startdate);
        textViewEventDetailsEnddate = findViewById(R.id.text_view_event_details_enddate);
        textViewEventDetailsParticipants = findViewById(R.id.text_view_event_details_participants);
        textViewEventDetailsHost = findViewById(R.id.text_view_event_details_Host);
        buttonViewViewBill = findViewById(R.id.text_view_event_details_viewbill);
        textViewEventDetailsName.setText(eventname);

        Query eventdetail_ref = db.collection("GlobalEvents")
                .whereEqualTo("Name", eventname);
        Query docRef = eventdetail_ref;
        Log.d(TAG,String.valueOf(docRef));

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot query = task.getResult();
                    if (query!= null) {
                        Log.d(TAG, "DocumentSnapshot data: ");
                        List<DocumentSnapshot> doclist = query.getDocuments();
                        for (int i=0; i<doclist.size();i++){
                            DocumentSnapshot doc = doclist.get(i);
                            if(doc.exists()){
                                Host = (String) doc.getData().get("Host");
                                Description = (String) doc.getData().get("Description");
                                StartDate = (Timestamp) doc.getData().get("StartDate");
                                EndDate = (Timestamp) doc.getData().get("EndDate");
                                Participants = (List) doc.getData().get("Participants");

                                textViewEventDetailsDescription.setText("Description:\n" + Description);
                                textViewEventDetailsHost.setText("Host: " + Host);

                                for (int j = 0; j<Participants.size(); j++){
                                    if (j == Participants.size()-1) {
                                        participantsList += String.valueOf(Participants.get(j));
                                    }
                                    else{
                                        participantsList += String.valueOf(Participants.get(j));
                                        participantsList += ", ";
                                    }
                                }
                                textViewEventDetailsParticipants.setText(participantsList);

                                String StartDateStr = "Start Date: \n" + String.valueOf(StartDate.toDate());
                                textViewEventDetailsStartdate.setText(StartDateStr);

                                String EndDateStr = "End Date: \n" + String.valueOf(EndDate.toDate());
                                textViewEventDetailsEnddate.setText(EndDateStr);


                                Log.d(TAG, String.valueOf("success"));

                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        buttonViewViewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, Paylah.class);
                context.startActivity(intent);
            }
        });


    }

}
