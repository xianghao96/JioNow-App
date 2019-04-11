package com.google.codelabs.mdc.java.jionow;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EventsAdapter extends FirestoreRecyclerAdapter<Events, EventsAdapter.EventsHolder> {

    public static String EVENT_NAME = "test";

    private String user;

    public EventsAdapter(@NonNull FirestoreRecyclerOptions<Events> options, String User) {
        super(options);
        this.user = User;
    }

    @Override
    protected void onBindViewHolder(@NonNull EventsHolder holder, int position, @NonNull Events model) {
        //Check if host is user ny passing as param, if is, give indication
        holder.textViewEventTitle.setText(model.getName());
        if (model.isEventsHost(user) == false){
            holder.textViewEventIsHost.setVisibility(View.INVISIBLE);
        }
        holder.textViewEventDescription.setText(model.getDescription());
        holder.textViewEventStatus.setText(model.status());
    }

    @NonNull
    @Override
    public EventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventsHolder(v);
    }

    class EventsHolder extends RecyclerView.ViewHolder {
        TextView textViewEventTitle;
        TextView textViewEventIsHost;
        TextView textViewEventDescription;
        TextView textViewEventStatus;
        Button buttonViewEvent;

        public EventsHolder(View itemView) {
            super(itemView);
            textViewEventTitle = itemView.findViewById(R.id.text_view_event_title);
            textViewEventIsHost = itemView.findViewById(R.id.text_view_event_ishost);
            textViewEventDescription = itemView.findViewById(R.id.text_view_event_description);
            textViewEventStatus = itemView.findViewById(R.id.text_view_event_status);
            Button buttonViewEvent = itemView.findViewById(R.id.button_view_event);

            buttonViewEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String texttitle = textViewEventTitle.getText().toString();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EventsDetails.class);
                    intent.putExtra(EVENT_NAME, texttitle);
                    context.startActivity(intent);
                }
            });
        }
    }
}

