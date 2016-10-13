package divine.calcify.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.com.divine.R;
import divine.calcify.model.DivineServices;
import divine.calcify.model.Services;

/**
 * Created by Calcify3 on 11-05-2016.
 */

public class DivineServiceListAdapter extends RecyclerView.Adapter<DivineServiceListAdapter.DivineServiceListViewHolder> {
    Context context;

    Activity activity;
    List<DivineServices> divineServices;
    ArrayList<Services> servicesArrayList;

    public DivineServiceListAdapter(Activity activity,List<DivineServices> divineServices){
        this.activity = activity;
        this.divineServices = divineServices;
    }
    public DivineServiceListAdapter(Activity activity,ArrayList<Services> servicesArrayList){
        this.activity = activity;
        this.servicesArrayList = servicesArrayList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DivineServiceListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hs_serviceslist_cardview_horizontal, viewGroup, false);
        DivineServiceListViewHolder pvh = new DivineServiceListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final DivineServiceListViewHolder divineServiceListViewHolder,final int i) {

        divineServiceListViewHolder.hs_service_name_in_service_list.setText(servicesArrayList.get(i).getService_name()+"("+servicesArrayList.get(i).getService_count()+")");
        //divineServiceListViewHolder.cv_list_of_service.setText("("+servicesArrayList.get(i).getService_count()+")");
        divineServiceListViewHolder.hs_service_name_in_service_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    HomeScreenActivity homeScreenActivity = (HomeScreenActivity) activity;
                    homeScreenActivity.showCalenderOnServiceClickCallBack(servicesArrayList.get(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        //return servicesArrayList.size();
        //need to show only 2 services in each group (in homescreen actitivy)

        return 2;
    }

    public static class DivineServiceListViewHolder extends RecyclerView.ViewHolder {
        TextView hs_service_name_in_service_list;
        //TextView service_count;

        DivineServiceListViewHolder(View itemView) {
            super(itemView);
            hs_service_name_in_service_list = (TextView)itemView.findViewById(R.id.hs_service_name_in_service_list);
            //service_count = (TextView)itemView.findViewById(R.id.service_count);
        }
    }
}
