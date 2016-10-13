package divine.calcify.adapters;

/**
 * Created by Calcify3 on 27-08-2016.
 */
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import divine.calcify.activities.ServicePartnerListActivity;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.model.Partner;


public class ServicePartnerListAdapter extends RecyclerView.Adapter<ServicePartnerListAdapter.ViewHolder> {
    private ArrayList<Partner> partnerArrayList;
    private Context context;
    public Activity activity;

    public ServicePartnerListAdapter(Context context,Activity activity) {
        this.partnerArrayList = GroupDetailFragment.partnerArrayList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ServicePartnerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spl_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ServicePartnerListAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.spl_group_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("group=",partnerArrayList.get(i).getPartnerName());
            }
        });
        viewHolder.spl_location_name.setText(partnerArrayList.get(i).getLocation());
        viewHolder.spl_partner_name.setText(partnerArrayList.get(i).getPartnerName());
        viewHolder.spl_service_cost.setText(context.getApplicationContext().getResources().getString(R.string.rupee_symbol)+partnerArrayList.get(i).getServiceCost());
        Picasso.with(context).load(R.mipmap.ic_launcher).resize(240, 120).into(viewHolder.spl_profile_image_view);
        viewHolder.spl_profile_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicePartnerListActivity servicePartnerListActivity= (ServicePartnerListActivity)activity;
                servicePartnerListActivity.showPartnerProfileInfo(partnerArrayList.get(i));
            }
        });
        viewHolder.spl_check_box_image.setImageDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.checkbox_disabled));
        viewHolder.spl_check_box_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partnerArrayList.get(i).isSelected()==true){
                    viewHolder.spl_check_box_image.setImageDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.checkbox_disabled));
                    viewHolder.borderVerticalLine.setBackgroundColor(context.getResources().getColor(R.color.border_line_off));
                    partnerArrayList.get(i).setSelected(false);
                    ((ServicePartnerListActivity) activity).changeButtonColor();
                    Log.d("is selected =",partnerArrayList.get(i).isSelected()+"");
                }
                else{
                    if(partnerArrayList.get(i).isSelected()==false){
                        viewHolder.borderVerticalLine.setBackgroundColor(context.getResources().getColor(R.color.border_line_on));
                        viewHolder.spl_check_box_image.setImageDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.checkbox_enabled));
                        partnerArrayList.get(i).setSelected(true);
                        ((ServicePartnerListActivity) activity).changeButtonColor();
                        Log.d("is selected =",partnerArrayList.get(i).isSelected()+"");}
                }


            }
        });
        //groupsList.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img_android

    }

    @Override
    public int getItemCount() {
        return partnerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView spl_partner_name;
        private TextView spl_location_name;
        private TextView spl_service_cost;
        private ImageView spl_profile_image_view;
        private ImageView spl_check_box_image;
        private LinearLayout spl_group_item_layout;
        private LinearLayout borderVerticalLine;
        public ViewHolder(View view) {
            super(view);
            borderVerticalLine = (LinearLayout) view.findViewById(R.id.spl_border_line);
            spl_check_box_image = (ImageView)view.findViewById(R.id.spl_check_box_image);
            spl_service_cost = (TextView)view.findViewById(R.id.spl_service_cost);
            spl_partner_name = (TextView)view.findViewById(R.id.spl_partner_name);
            spl_location_name = (TextView)view.findViewById(R.id.spl_location_name);
            spl_profile_image_view = (ImageView) view.findViewById(R.id.spl_profile_image_view);
            spl_group_item_layout=(LinearLayout)view.findViewById(R.id.spl_group_item_layout);
        }
    }



}

