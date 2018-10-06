package com.example.oguerisck.wearnotifications.ViewHolder;

/**
 * Created by Oguerisck on 21/09/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oguerisck.wearnotifications.Model.EventModel;
import com.example.oguerisck.wearnotifications.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends BaseAdapter {
    Activity context;
    private static LayoutInflater inflater = null;
    private List<EventModel> eventsList;

    public EventAdapter (Activity context, ArrayList<EventModel> listeEvenements) {
        this.context = context;
        this.eventsList = listeEvenements;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public EventModel getItem(int i) {
        return eventsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        itemView = (itemView == null) ? inflater.inflate(R.layout.item_event, null): itemView;
        TextView textViewTitle = (TextView) itemView.findViewById(R.id.card_event_title);
        TextView textViewDesc = (TextView) itemView.findViewById(R.id.card_event_description);
        TextView textViewAddress = (TextView) itemView.findViewById(R.id.card_event_address);
        TextView textViewDateB = (TextView) itemView.findViewById(R.id.card_event_begin);
        TextView textViewDateE = (TextView) itemView.findViewById(R.id.card_event_end);
        EventModel selectedEvent = eventsList.get(i);
        textViewTitle.setText(selectedEvent.title);
        textViewDesc.setText(selectedEvent.body);
        textViewAddress.setText(selectedEvent.address);
        textViewDateB.setText(selectedEvent.begin.toString());
        textViewDateE.setText(selectedEvent.end.toString());
        return itemView;
    }
}
