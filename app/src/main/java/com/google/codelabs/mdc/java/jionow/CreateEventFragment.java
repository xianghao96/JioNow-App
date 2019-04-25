package com.google.codelabs.mdc.java.jionow;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CreateEventFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public final String TAG = "Sent:";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_create_event_fragment, container, false);

        final TextInputLayout eventTextInput = view.findViewById(R.id.event_text_input);
        final TextInputEditText eventEditText = view.findViewById(R.id.event_edit_text);

        final TextInputLayout descriptionTextInput = view.findViewById(R.id.description_text_input);
        final TextInputEditText descriptionEditText = view.findViewById(R.id.description_edit_text);

        final TextInputLayout startDateTimeTextInput = view.findViewById(R.id.start_DateTime_text_input);
        final TextInputEditText startDateTimeEditText = view.findViewById(R.id.start_DateTime_edit_text);

        final TextInputLayout endDateTimeTextInput = view.findViewById(R.id.end_DateTime_text_input);
        final TextInputEditText endDateTimeEditText = view.findViewById(R.id.end_DateTime_edit_text);

        final TextInputLayout inviteesTextInput = view.findViewById(R.id.invitees_text_input);
        final TextInputEditText inviteesEditText = view.findViewById(R.id.invitees_edit_text);


        MaterialButton saveButton = view.findViewById(R.id.save_button);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> newdoc = new HashMap<>();

                Date startDateTime = null;
                Date endDateTime = null;
                String personName = null;

                try {
                    startDateTime = new SimpleDateFormat("dd-MM-yyyy-HH:mm").parse(startDateTimeEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    endDateTime = new SimpleDateFormat("dd-MM-yyyy-HH:mm").parse(endDateTimeEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                personName = MainMenu.USER;


                String Name = eventEditText.getText().toString();
                String Description = descriptionEditText.getText().toString();
                //List<String> Participants = Arrays.asList(inviteesEditText.getText().toString());
                List<String> Participants = Arrays.asList("xianghao96","eugenechia95");

                Date startDate = startDateTime;
                Date endDate = endDateTime;
                String Host = personName;
                String DocumentId = "test";

                newdoc.put("Name", Name);
                newdoc.put("Description", Description);
                newdoc.put("Participants", Participants);
                newdoc.put("Host", "xianghao96");
                newdoc.put("StartDate", startDate);
                newdoc.put("EndDate", endDate);

                db.collection("GlobalEvents").document(DocumentId)
                        .set(newdoc)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });


                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, eventEditText.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descriptionEditText.getText().toString());
                intent.putExtra("beginTime", startDateTime.getTime());
                intent.putExtra("endTime", endDateTime.getTime());
                intent.putExtra(Intent.EXTRA_EMAIL, inviteesEditText.getText().toString());

                startActivity(intent);
            }
        });
        return view;
    }
}

