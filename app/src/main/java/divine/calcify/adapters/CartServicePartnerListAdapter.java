package divine.calcify.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.com.divine.R;
import divine.calcify.model.Partner;
import divine.calcify.model.ServiceCart;
import divine.calcify.model.Services;

/**
 * Created by Calcify3 on 14-10-2016.
 */


public class CartServicePartnerListAdapter extends RecyclerView.Adapter<CartServicePartnerListAdapter.CartServicePartnerListViewHolder> {

    Activity activity;
    ArrayList<Partner> partnerArrayList = new ArrayList<>();
    CartServiceAdapter cartServiceAdapter;


    public CartServicePartnerListAdapter(Activity activity, ArrayList<Partner> partnerArrayList,CartServiceAdapter cartServiceAdapter){
        this.activity = activity;
        this.partnerArrayList = partnerArrayList;
        this.cartServiceAdapter = cartServiceAdapter;
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CartServicePartnerListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_cart_item_partner_list, viewGroup, false);
        CartServicePartnerListViewHolder pvh = new CartServicePartnerListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final CartServicePartnerListViewHolder cartServicePartnerListViewHolder,final int i) {
        cartServicePartnerListViewHolder.sci_cart_service_partner_name.setText(partnerArrayList.get(i).getPartnerName());
        cartServicePartnerListViewHolder.sci_cart_service_cost.setText(partnerArrayList.get(i).getServiceCost());
        if(partnerArrayList.get(i).isSelected()){
            cartServicePartnerListViewHolder.sci_cart_service_confirm.setImageDrawable(activity.getResources().getDrawable(R.drawable.checkbox_enabled));
        }else {
            cartServicePartnerListViewHolder.sci_cart_service_confirm.setImageDrawable(activity.getResources().getDrawable(R.drawable.checkbox_disabled));
        }
        cartServicePartnerListViewHolder.sci_cart_service_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (partnerArrayList.get(i).isSelected()){
                    cartServicePartnerListViewHolder.sci_cart_service_confirm.setImageDrawable(activity.getResources().getDrawable(R.drawable.checkbox_disabled));
                    partnerArrayList.get(i).setSelected(false);
                    for (int index=0;index<partnerArrayList.size();index++){
                            partnerArrayList.get(index).setSelected(false);
                    }
                    //notifyDataSetChanged();
                    //cartServiceAdapter.notifyDataSetChanged();
                }else {
                    cartServicePartnerListViewHolder.sci_cart_service_confirm.setImageDrawable(activity.getResources().getDrawable(R.drawable.checkbox_enabled));
                    partnerArrayList.get(i).setSelected(true);
                    for (int index=0;index<partnerArrayList.size();index++){
                        if (index!=i){
                            partnerArrayList.get(index).setSelected(false);
                        }
                    }
                    //uncheck all other checkbox
                    /*notifyDataSetChanged();
                    //enable confirm button
                    cartServiceAdapter.notifyDataSetChanged();*/
                }
                notifyDataSetChanged();
                //enable confirm button
                cartServiceAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return partnerArrayList.size();
    }

    public static class CartServicePartnerListViewHolder extends RecyclerView.ViewHolder {
        TextView sci_cart_service_partner_name;
        TextView sci_cart_service_cost;
        ImageView sci_cart_service_confirm;

        CartServicePartnerListViewHolder(View itemView) {
            super(itemView);
            sci_cart_service_partner_name = (TextView)itemView.findViewById(R.id.sci_cart_service_partner_name);
            sci_cart_service_cost=(TextView)itemView.findViewById(R.id.sci_cart_service_cost);
            sci_cart_service_confirm = (ImageView)itemView.findViewById(R.id.sci_cart_service_confirm);

        }
    }
}

