package divine.calcify.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.Utility.Utilities;
import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.com.divine.R;
import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.DivineServices;

/**
 * Created by Calcify3 on 11-05-2016.
 */

public class DivineServiceAdapter extends RecyclerView.Adapter<DivineServiceAdapter.DivineServiceViewHolder> {
    Context context;
    Activity activity;


    List<DivineServices> divineServices;
    ArrayList<DivineGroupServices> divineGroupServicesArrayList;


    public DivineServiceAdapter(Activity activity,ArrayList<DivineGroupServices> divineGroupServicesArrayList){
        this.activity = activity;
        this.divineGroupServicesArrayList = divineGroupServicesArrayList;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DivineServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hs_service_item_cardview, viewGroup, false);
        DivineServiceViewHolder pvh = new DivineServiceViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final DivineServiceViewHolder divineServiceViewHolder,final int i) {


        divineServiceViewHolder.divineservice_name.setText(divineGroupServicesArrayList.get(i).getGroupServiceName());
        //dont show view if no services has service providers(partners)
        boolean flag = Utilities.getServiceProvidersAvailableOrNot(divineGroupServicesArrayList.get(i).getGroupServicesList());
        if(!flag){
            divineServiceViewHolder.hs_service_list_button_view_all.setVisibility(View.INVISIBLE);
        }else{
            divineServiceViewHolder.hs_service_list_button_view_all.setVisibility(View.VISIBLE);
        }

        //on click of viewall , all services of current group must shown in fragment(detail group activity)
        divineServiceViewHolder.hs_service_list_button_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cardview=",divineGroupServicesArrayList.get(i).getGroupServiceName());
                HomeScreenActivity homeScreenActivity = (HomeScreenActivity)activity;
                homeScreenActivity.callGroupActivity(divineGroupServicesArrayList.get(i).getGroupServiceName());
            }
        });

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        divineServiceViewHolder.hs_services_list_recylerview.setLayoutManager(hs_linearLayout);
        divineServiceViewHolder.hs_services_list_recylerview.setHasFixedSize(true);

        DivineServiceListAdapter divineServiceListAdapter = new DivineServiceListAdapter(activity,divineGroupServicesArrayList.get(i).getGroupServicesList());
        divineServiceViewHolder.hs_services_list_recylerview.setAdapter(divineServiceListAdapter);


    }

    @Override
    public int getItemCount() {
        return divineGroupServicesArrayList.size();
    }

    public static class DivineServiceViewHolder extends RecyclerView.ViewHolder {

        CardView hm_cardview;
        TextView divineservice_name;
        //TextView divineservice_text;
        RecyclerView hs_services_list_recylerview;
        TextView hs_service_list_button_view_all;


        DivineServiceViewHolder(View itemView) {
            super(itemView);
            hs_service_list_button_view_all = (TextView)itemView.findViewById(R.id.hs_service_list_button_view_all);
            hs_services_list_recylerview = (RecyclerView)itemView.findViewById(R.id.hs_services_list_recylerview);
            hm_cardview = (CardView)itemView.findViewById(R.id.hs_cardview_groupList_with_location);
            divineservice_name = (TextView)itemView.findViewById(R.id.cv_divineservice_name);
            //divineservice_text = (TextView)itemView.findViewById(R.id.cv_divineservice_text);

        }
    }

    public void notifyAdapter(ArrayList<DivineGroupServices> divineGroupServices){
        divineGroupServicesArrayList = divineGroupServices;
        notifyDataSetChanged();
    }
}
