package divine.calcify.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.activities.DetailTempleActivity;
import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.fragments.TempleDetailFragment;
import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.Events;

/**
 * Created by Calcify3 on 13-10-2016.
 */

public class TempleDetailAdapter extends RecyclerView.Adapter<TempleDetailAdapter.TempleDetailViewHolder> {
    private DivineGroupServices divinegroup;
    private Activity activity;
    private ArrayList<Events> eventsArrayList;
    private TempleDetailFragment templeDetailFragment;

    public TempleDetailAdapter(Activity activity, ArrayList<Events> eventsArrayList, TempleDetailFragment templeDetailFragment) {
        this.eventsArrayList = eventsArrayList;
        this.activity = activity;
        this.templeDetailFragment = templeDetailFragment;
    }

    @Override
    public TempleDetailAdapter.TempleDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.temple_detail_list, viewGroup, false);
        return new TempleDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TempleDetailAdapter.TempleDetailViewHolder viewHolder, final int i) {
        viewHolder.temple_detail_events_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailTempleActivity detailTempleActivity = (DetailTempleActivity)activity;
                detailTempleActivity.displayEventInformaion(eventsArrayList.get(i));
            }
        });
        viewHolder.temple_detail_event_name.setText(eventsArrayList.get(i).getEventName());
        viewHolder.temple_detail_event_date.setText(eventsArrayList.get(i).getEventDate());
        viewHolder.temple_detail_event_timings.setText(eventsArrayList.get(i).getEventTimings());
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class TempleDetailViewHolder extends RecyclerView.ViewHolder{
        private CardView temple_detail_events_cardview;
        private TextView temple_detail_event_name;
        private TextView temple_detail_event_timings;
        private TextView temple_detail_event_date;
        public TempleDetailViewHolder(View view) {
            super(view);
            temple_detail_events_cardview = (CardView)view.findViewById(R.id.temple_detail_events_cardview);
            temple_detail_event_name = (TextView)view.findViewById(R.id.temple_detail_event_name);
            temple_detail_event_timings = (TextView)view.findViewById(R.id.temple_detail_event_timings);
            temple_detail_event_date = (TextView)view.findViewById(R.id.temple_detail_event_date);
        }
    }

    /*public void refreshGroupDetailAdapter(int position){
        this.divinegroup = HomeScreenActivity.divineGroupServicesArrayList.get(position);
        this.notifyDataSetChanged();
    }*/



}