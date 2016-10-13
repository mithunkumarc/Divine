package divine.calcify.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.model.DivineGroupServices;

/**
 * Created by Calcify3 on 17-08-2016.
 */

public class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailAdapter.ViewHolder> {
    private DivineGroupServices divinegroup;
    private Context context;
    private GroupDetailFragment groupDetailFragment;

    public GroupDetailAdapter(Context context,DivineGroupServices divinegroup,GroupDetailFragment groupDetailFragment) {
        this.divinegroup = divinegroup;
        this.context = context;
        this.groupDetailFragment = groupDetailFragment;
    }

    @Override
    public GroupDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_detail_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupDetailAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.groupservice_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("calling","- show calendar");

                groupDetailFragment.showCalendar(divinegroup.getGroupServicesList().get(i));
            }
        });
        viewHolder.group_detail_service_name.setText(divinegroup.getGroupServicesList().get(i).getService_name());
        viewHolder.group_service_partner_count.setText(divinegroup.getGroupServicesList().get(i).getService_count());
    }

    @Override
    public int getItemCount() {
        return divinegroup.getGroupServicesList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView group_detail_service_name;
        private TextView group_service_partner_count;
        private CardView groupservice_cardview;
        public ViewHolder(View view) {
            super(view);
            groupservice_cardview = (CardView)view.findViewById(R.id.groupservice_cardview);
            group_detail_service_name = (TextView)view.findViewById(R.id.group_detail_service_name);
            group_service_partner_count = (TextView)view.findViewById(R.id.group_service_partner_count);
        }
    }

    public void refreshGroupDetailAdapter(int position){
        this.divinegroup = HomeScreenActivity.divineGroupServicesArrayList.get(position);
        this.notifyDataSetChanged();
    }



}