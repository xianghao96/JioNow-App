package com.google.codelabs.mdc.java.jionow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainMenu extends Fragment {

    public static String USER;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    public final static String events = "Events";
    public final String TAG = "Logcatevents";
    private ArrayList<String> eventslist = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_menu, parent, false);

        Button signOutButton =  v.findViewById(R.id.sign_out_button);
        TextView viewEmail = v.findViewById(R.id.sign_in_email);
        MaterialCardView outstandingPaymentsCardView = v.findViewById(R.id.outstandingPaymentsCardView);
        MaterialCardView createEventsCardView = v.findViewById(R.id.createEventsCardView);
        MaterialCardView scanReceiptCardView = v.findViewById(R.id.scanReceiptCardView);
        MaterialCardView myEventsCardView = v.findViewById(R.id.myEventsCardView);

        //Flush eventslist and read from firestore each time activity is generated

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personEmail = acct.getEmail();
            //String personEmail = "eugenechia95@gmail.com";
            String[] user =personEmail.split("@");
            USER = user[0];
            viewEmail.setText("Logged in as: " + USER);
            getEvents();
        }

        outstandingPaymentsCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Intent intent = new Intent(context, OutstandingPayments.class);
                context.startActivity(intent);
            }
        });

        createEventsCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    ((NavigationHost) getActivity()).navigateTo(new CreateEventFragment(), true);
                }
        });

        scanReceiptCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Intent intent = new Intent(context, DialogActivity.class);
                intent.putExtra(events, eventslist);
                context.startActivity(intent);
            }
        });

        myEventsCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Intent intent = new Intent(context, EventsDisplay.class);
                context.startActivity(intent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_out_button:
                        signOut();
                        break;
                }
            }
        });
        return v;
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ((NavigationHost) getActivity()).navigateTo(new LoginFragment(), true);
            }
        });
    }

    public void getEvents(){
        db.collection("GlobalEvents")
                .whereEqualTo("Host", USER)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            eventslist.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Timestamp startdate = (Timestamp) document.get("StartDate");
                                long now=System.currentTimeMillis()/1000;
                                //long diff = now.getTime() - startdate.getTime();
                                long starttime = startdate.getSeconds();
                                //only past events less than 3 months ago will be displayed
                                if (now-starttime < 7.884e+6 && now-starttime>0){
                                    String eventname = (String) document.get("Name");
                                    Log.d(TAG,eventname);
                                    eventslist.add(String.valueOf(eventname));
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
