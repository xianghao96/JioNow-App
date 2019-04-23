package com.google.codelabs.mdc.java.jionow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductGridFragment extends Fragment {

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private String testuser = "xianghao96";
    public final static String events = "Events";
    public final String TAG = "Logcatevents";
    private ArrayList<String> eventslist = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shr_product_grid_fragment, parent, false);

        final Button signOutButton =  v.findViewById(R.id.sign_out_button);
        TextView viewEmail = v.findViewById(R.id.sign_in_email);
        MaterialCardView outstandingPaymentsCardView = v.findViewById(R.id.outstandingPaymentsCardView);
        MaterialCardView createEventsCardView = v.findViewById(R.id.createEventsCardView);
        MaterialCardView scanReceiptCardView = v.findViewById(R.id.scanReceiptCardView);
        MaterialCardView myEventsCardView = v.findViewById(R.id.myEventsCardView);

        //Flush eventslist and read from firestore each time activity is generated

        db.collection("GlobalEvents")
                .whereEqualTo("Host", testuser)
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
                context.startActivity(intent);            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personEmail = acct.getEmail();
            viewEmail.setText(personEmail);
        }

        // This has been changed to display the receipt view for now!
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ReceiptList.class);
                context.startActivity(intent);
            }
        });

//        signOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.sign_out_button:
//                        mGoogleSignInClient.signOut();
//                        break;
//                }
//            }
//        });

        return v;
    }
}
