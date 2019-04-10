package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.Calendar;


public class CreateEventFragment extends Fragment {
    public static final Uri CONTENT_URI = null;

//    final HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
//    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();


    GoogleAccountCredential credentials;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_create_event_fragment, container, false);
        final TextInputLayout eventTextInput = view.findViewById(R.id.event_text_input);
        final TextInputEditText eventEditText = view.findViewById(R.id.event_edit_text);
//        final TextInputLayout datetimeTextInput = view.findViewById(R.id.datetime_text_input);
//        final TextInputEditText datetimeEditText = view.findViewById(R.id.datetime_edit_text);
        MaterialButton saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, eventEditText.getText().toString());

//                        .setType("vnd.android.cursor.item/event")
//                        .setData(CalendarContract.Events.CONTENT_URI)
//                        .putExtra(CalendarContract.Events.TITLE, eventEditText.getText().toString());
                intent.putExtra(Intent.EXTRA_EMAIL, "eugenechia95@gmail.com");
                startActivity(intent);
            }
        });
    return view;
    }
}

