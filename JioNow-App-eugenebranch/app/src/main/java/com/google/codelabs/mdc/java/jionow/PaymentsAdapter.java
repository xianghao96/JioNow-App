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

public class PaymentsAdapter extends FirestoreRecyclerAdapter<Payments, PaymentsAdapter.NoteHolder> {

    private String user;

    public PaymentsAdapter(@NonNull FirestoreRecyclerOptions<Payments> options, String User) {
        super(options);
        this.user = User;
    }



    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Payments model) {
        holder.textViewEvent.setText(model.getName());
        holder.textViewOwed.setText(model.getPayment(this.user));
        holder.textViewRecipient.setText(model.getHost());
        holder.textViewOutstandingHostAmount.setText(model.getHostPayment());

        if (model.getHost().equals(this.user)){
            holder.buttonPayView.setVisibility(View.INVISIBLE);
            holder.textViewRecipient.setVisibility(View.INVISIBLE);
            holder.textViewRecipientDescription.setVisibility(View.INVISIBLE);
            holder.texttViewOwedDescription.setVisibility(View.INVISIBLE);
            holder.textViewOwed.setVisibility(View.INVISIBLE);
        }
        else{
            holder.buttonRemindView.setVisibility(View.INVISIBLE);
            holder.texttViewHost.setVisibility(View.INVISIBLE);
            holder.textViewOutstandingHostAmountDescription.setVisibility(View.INVISIBLE);
            holder.textViewOutstandingHostAmount.setVisibility(View.INVISIBLE);
        }

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.outstanding_item, parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewRecipientDescription;
        TextView textViewRecipient;
        TextView textViewEvent;
        TextView texttViewHost;
        TextView textViewOwed;
        TextView texttViewOwedDescription;
        TextView textViewOutstandingHostAmountDescription;
        TextView textViewOutstandingHostAmount;
        Button buttonPayView;
        Button buttonRemindView;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewRecipientDescription = itemView.findViewById(R.id.text_view_recipient_description);
            textViewRecipient = itemView.findViewById(R.id.text_view_recipient);
            textViewEvent = itemView.findViewById(R.id.text_view_event);
            texttViewHost = itemView.findViewById(R.id.text_view_host);
            textViewOwed = itemView.findViewById(R.id.text_view_owed);
            texttViewOwedDescription = itemView.findViewById(R.id.text_view_owed_description);
            textViewOutstandingHostAmountDescription = itemView.findViewById(R.id.text_view_outstanding_host_amount_description);
            textViewOutstandingHostAmount = itemView.findViewById(R.id.text_view_outstanding_host_amount);
            buttonPayView = itemView.findViewById(R.id.button_pay);
            buttonRemindView = itemView.findViewById(R.id.button_remindall);

            buttonPayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Paylah.class);
                    context.startActivity(intent);
                }
            });

            buttonRemindView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Paylah.class);
                    context.startActivity(intent);
                }
            });


        }



    }
}

