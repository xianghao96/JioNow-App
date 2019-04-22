package com.google.codelabs.mdc.java.jionow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;

public class ProductGridFragment extends Fragment {

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shr_product_grid_fragment, parent, false);

        final Button signOutButton =  v.findViewById(R.id.sign_out_button);
        TextView viewEmail = v.findViewById(R.id.sign_in_email);
        MaterialCardView outstandingPaymentsCardView = v.findViewById(R.id.outstandingPaymentsCardView);
        MaterialCardView createEventsCardView = v.findViewById(R.id.createEventsCardView);
        MaterialCardView scanReceiptCardView = v.findViewById(R.id.scanReceiptCardView);
        MaterialCardView myEventsCardView = v.findViewById(R.id.myEventsCardView);

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
