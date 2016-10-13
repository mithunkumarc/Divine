package divine.calcify.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import divine.calcify.com.divine.R;
import divine.calcify.model.Events;
import divine.calcify.model.Temple;

public class EventListParentAdapter extends RecyclerView.Adapter<EventListParentAdapter.MyViewHolder> {

    //private List<Movie> moviesList;

    private Activity activity;

    private Temple temple;
    private HashMap<String,ArrayList<Events>> dateEventsMap;

    public EventListParentAdapter(HashMap<String,ArrayList<Events>> dateEventsMap,Activity activity) {
        this.dateEventsMap = dateEventsMap;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_parent_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Set<String> dateKeys = dateEventsMap.keySet();

        ArrayList<Events> eventsArrayList = dateEventsMap.get(dateKeys.toArray()[position]);

        holder.event_list_parent_date.setText(dateKeys.toArray()[position].toString());

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false);
        holder.event_recycler_view_child.setLayoutManager(hs_linearLayout);
        holder.event_recycler_view_child.setHasFixedSize(true);
        EventListChildAdapter eventListChildAdapter = new EventListChildAdapter(activity,eventsArrayList);
        //DivineServiceListAdapter divineServiceListAdapter = new DivineServiceListAdapter(activity,divineGroupServicesArrayList.get(i).getGroupServicesList());
        holder.event_recycler_view_child.setAdapter(eventListChildAdapter);

    }

    @Override
    public int getItemCount() {
        return dateEventsMap.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_list_parent_date;
        public RecyclerView event_recycler_view_child;

        public MyViewHolder(View view) {
            super(view);
            event_list_parent_date = (TextView) view.findViewById(R.id.event_list_parent_date);
            event_recycler_view_child = (RecyclerView)view.findViewById(R.id.event_recycler_view_child);
        }
    }
}
