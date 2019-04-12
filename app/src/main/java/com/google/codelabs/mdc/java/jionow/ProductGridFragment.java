package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProductGridFragment extends Fragment {

    private Button outstandingPaymentsButton, paymentHistoryButton, calendarButton, scanReceiptButton, signOutButton, myEventsButton;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false);
        MaterialButton outstandingPaymentsButton = view.findViewById(R.id.outstandingPaymentsButton);
        MaterialButton paymentHistoryButton = view.findViewById(R.id.paymentHistoryButton);
        MaterialButton calendarButton = view.findViewById(R.id.calendarButton);
        MaterialButton scanReceiptButton = view.findViewById(R.id.scanReceiptButton);
        MaterialButton myEventsButton = view.findViewById(R.id.my_events_button);
        final Button signOutButton =  view.findViewById(R.id.sign_out_button);


        scanReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new SplitMoneyFragment(), true); // Navigate to Outstanding Payments Fragment
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new CreateEventFragment(), true); // Navigate to the next Fragment
//                 changeToCalendar();
            }

//            public void changeToCalendar() {
//                Intent launchIntent = getContext().getPackageManager().getLaunchIntentForPackage("com.google.android.calendar");
//                if (launchIntent != null) {
//                    startActivity(launchIntent);//null pointer check in case package name was not found
//                }
//            }
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

        return view;
    }
}
