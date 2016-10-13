package divine.calcify.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.activities.ShowTempleListActivity;
import divine.calcify.com.divine.R;
import divine.calcify.model.Events;

/**
 * Created by Calcify3 on 28-09-2016.
 */

public class EventListChildAdapter extends RecyclerView.Adapter<EventListChildAdapter.MyViewHolder> {

    //private List<Movie> moviesList;


    private ArrayList<Events> eventsArrayList;
    private Activity activity;

    public EventListChildAdapter(Activity activity, ArrayList<Events> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_child_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        final Events events = eventsArrayList.get(position);
        holder.event_list_event_name.setText(events.getEventName());
        holder.event_list_event_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowTempleListActivity showTempleListActivity = (ShowTempleListActivity)activity;
                showTempleListActivity.displayEventInformaion(events);
            }
        });



    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_list_event_name;


        public MyViewHolder(View view) {
            super(view);
            event_list_event_name = (TextView) view.findViewById(R.id.event_list_event_name);

        }
    }
}
