package com.google.codelabs.mdc.java.shrine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProductGridFragment extends Fragment {

    private Button outstandingPaymentsButton, paymentHistoryButton, calendarButton, scanReceiptButton;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false);
        MaterialButton outstandingPaymentsButton = view.findViewById(R.id.outstandingPaymentsButton);
        MaterialButton paymentHistoryButton = view.findViewById(R.id.paymentHistoryButton);
        MaterialButton calendarButton = view.findViewById(R.id.calendarButton);
        MaterialButton scanReceiptButton = view.findViewById(R.id.scanReceiptButton);

        outstandingPaymentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), true); // Navigate to Outstanding Payments Fragment
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToCalendar();
            }

            public void changeToCalendar() {
                Intent launchIntent = getContext().getPackageManager().getLaunchIntentForPackage("com.google.android.calendar");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

        return view;
    }
}
