package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
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

public class CreateEventFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_create_event_fragment, container, false);

        final TextInputLayout eventTextInput = view.findViewById(R.id.event_text_input);
        final TextInputEditText eventEditText = view.findViewById(R.id.event_edit_text);

        final TextInputLayout descriptionTextInput = view.findViewById(R.id.description_text_input);
        final TextInputEditText descriptionEditText = view.findViewById(R.id.description_edit_text);

        final TextInputLayout dateTextInput = view.findViewById(R.id.date_text_input);
        final TextInputEditText dateEditText = view.findViewById(R.id.date_edit_text);

        final TextInputLayout timeTextInput = view.findViewById(R.id.time_text_input);
        final TextInputEditText timeEditText = view.findViewById(R.id.time_edit_text);

        final TextInputLayout inviteesTextInput = view.findViewById(R.id.invitees_text_input);
        final TextInputEditText inviteesEditText = view.findViewById(R.id.invitees_edit_text);

        MaterialButton saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");

                intent.putExtra(CalendarContract.Events.TITLE, eventEditText.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descriptionEditText.getText().toString());
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateEditText.getText().toString());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, timeEditText.getText().toString());
                intent.putExtra(Intent.EXTRA_EMAIL, inviteesEditText.getText().toString());

                startActivity(intent);
            }
        });
    return view;
    }
}

