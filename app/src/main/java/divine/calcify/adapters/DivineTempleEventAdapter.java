package divine.calcify.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.com.divine.R;
import divine.calcify.model.DivineGroupsEvents;

/**
 * Created by Calcify3 on 29-09-2016.
 */

public class DivineTempleEventAdapter extends RecyclerView.Adapter<DivineTempleEventAdapter.DivineTempleEventViewHolder> {
    Context context;
    Activity activity;

    ArrayList<DivineGroupsEvents> divineTempleEventsArrayList=HomeScreenActivity.divineGroupsEventsArrayList;
    DivineTempleEventListAdapter divineTempleEventListAdapter;
    ArrayList<DivineTempleEventListAdapter> arrayListAdaper= new ArrayList<>();

    public DivineTempleEventAdapter(Activity activity,ArrayList<DivineGroupsEvents> divineTempleEventsArrayList){
        this.activity = activity;
        this.divineTempleEventsArrayList = divineTempleEventsArrayList;
    }
    public DivineTempleEventAdapter(Activity activity){
        this.activity = activity;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DivineTempleEventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hs_event_item_cardview, viewGroup, false);
        DivineTempleEventViewHolder pvh = new DivineTempleEventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final DivineTempleEventViewHolder divineServiceViewHolder,final int i) {


        divineServiceViewHolder.divine_group_temple_event_name.setText(divineTempleEventsArrayList.get(i).getGroupName());
        //dont show view if no services has service providers(partners)
        int templeArrayListSize = divineTempleEventsArrayList.get(i).getTempleArrayList().size();
        if(templeArrayListSize>2){
            divineServiceViewHolder.divine_temple_event_button_viewall.setVisibility(View.VISIBLE);
        }else{
            divineServiceViewHolder.divine_temple_event_button_viewall.setVisibility(View.INVISIBLE);
        }

        //on click of viewall , all services of current group must shown in fragment(detail group activity)
        divineServiceViewHolder.divine_temple_event_button_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("show all=","events of "+divineTempleEventsArrayList.get(i).getTempleArrayList());
                HomeScreenActivity homeScreenActivity = (HomeScreenActivity) activity;
                homeScreenActivity.showAllTempleEventListWithDate(divineTempleEventsArrayList.get(i).getTempleArrayList());
                /*HomeScreenActivity homeScreenActivity = (HomeScreenActivity)activity;
                homeScreenActivity.callGroupActivity(divineGroupServicesArrayList.get(i).getGroupServiceName());*/
            }
        });

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        divineServiceViewHolder.divine_temple_event_recyclerview.setLayoutManager(hs_linearLayout);
        divineServiceViewHolder.divine_temple_event_recyclerview.setHasFixedSize(true);

        divineTempleEventListAdapter = new DivineTempleEventListAdapter(activity,divineTempleEventsArrayList.get(i).getTempleArrayList(),i);
        divineServiceViewHolder.divine_temple_event_recyclerview.setAdapter(divineTempleEventListAdapter);


    }

    @Override
    public int getItemCount() {
        return divineTempleEventsArrayList.size();
    }

    public static class DivineTempleEventViewHolder extends RecyclerView.ViewHolder {

        CardView hm_cardview;
        TextView divine_group_temple_event_name;
        //TextView cv_divine_temple_god_event_text;
        RecyclerView divine_temple_event_recyclerview;
        TextView divine_temple_event_button_viewall;


        DivineTempleEventViewHolder(View itemView) {
            super(itemView);
            divine_temple_event_button_viewall = (TextView)itemView.findViewById(R.id.hs_temple_event_list_button_view_all);
            divine_temple_event_recyclerview = (RecyclerView)itemView.findViewById(R.id.hs_temple_god_list_recylerview);
            hm_cardview = (CardView)itemView.findViewById(R.id.hs_cardview_temple_events_with_location);
            divine_group_temple_event_name = (TextView)itemView.findViewById(R.id.cv_divine_temple_god_event_name);
            //cv_divine_temple_god_event_text = (TextView)itemView.findViewById(R.id.cv_divine_temple_god_event_text);

        }
    }

    //display results after toolbar search
    public void notifyAdapter(ArrayList<DivineGroupsEvents> divineGroupsEventses){
        divineTempleEventsArrayList = divineGroupsEventses;
        notifyDataSetChanged();
    }
}

