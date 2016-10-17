package divine.calcify.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.com.divine.R;
import divine.calcify.model.Partner;
import divine.calcify.model.ServiceCart;
import divine.calcify.model.ServiceCartListByDates;
import divine.calcify.model.Services;

/**
 * Created by Calcify3 on 06-10-2016.
 */

public class CartServiceAdapter extends RecyclerView.Adapter<CartServiceAdapter.CartServiceViewHolder> {

    Activity activity;
    //ArrayList<ServiceCart> serviceCartArrayList = new ArrayList<>();
    ArrayList<Services> servicesArrayList = new ArrayList<>();


    public CartServiceAdapter(Activity activity, ArrayList<Services> servicesArrayList){
        this.activity = activity;
        this.servicesArrayList = servicesArrayList;
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CartServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_cart_item, viewGroup, false);
        CartServiceViewHolder pvh = new CartServiceViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final CartServiceViewHolder cartServiceViewHolder,final int i) {


        //cartServiceViewHolder.cart_service_name.setText(serviceCartArrayList.get(i).getServiceName());
        cartServiceViewHolder.cart_service_name.setText(servicesArrayList.get(i).getService_name());
        cartServiceViewHolder.cart_service_selected_date.setText(servicesArrayList.get(i).getDateSelectedByCustomerForService());

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        cartServiceViewHolder.cart_service_partner_list_recycler_view.setLayoutManager(hs_linearLayout);
        cartServiceViewHolder.cart_service_partner_list_recycler_view.setHasFixedSize(true);

        CartServicePartnerListAdapter cartServicePartnerListAdapter = new CartServicePartnerListAdapter(activity,servicesArrayList.get(i).getPartnerArrayList(),this);
        cartServiceViewHolder.cart_service_partner_list_recycler_view.setAdapter(cartServicePartnerListAdapter);
        boolean partnerSelected = false;
        for (int index=0;index<servicesArrayList.get(i).getPartnerArrayList().size();index++){
            if (servicesArrayList.get(i).getPartnerArrayList().get(index).isSelected()){
                cartServiceViewHolder.sci_cart_service_confirm_button.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                partnerSelected = true;
                cartServiceViewHolder.sci_button_layout.setVisibility(View.VISIBLE);
            }
        }
        if (!partnerSelected){
            //cartServiceViewHolder.sci_cart_service_confirm_button.setBackgroundColor(activity.getResources().getColor(R.color.chrome_grey));
            cartServiceViewHolder.sci_button_layout.setVisibility(View.INVISIBLE);
        }
        cartServiceViewHolder.sci_cart_service_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Partner partner : servicesArrayList.get(i).getPartnerArrayList()){
                    partner.setSelected(false);
                }
                cartServiceViewHolder.sci_button_layout.setVisibility(View.INVISIBLE);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return servicesArrayList.size();
    }

    public static class CartServiceViewHolder extends RecyclerView.ViewHolder {

        //TextView cart_service_cost;
        TextView cart_service_name;
        TextView cart_service_selected_date;
        RecyclerView cart_service_partner_list_recycler_view;
        Button sci_cart_service_confirm_button;
        Button sci_cart_service_cancel_button;
        LinearLayout sci_button_layout;


        CartServiceViewHolder(View itemView) {
            super(itemView);
            //cart_service_cost = (TextView)itemView.findViewById(R.id.sci_cart_service_cost);
            cart_service_name = (TextView) itemView.findViewById(R.id.sci_cart_service_name);
            //cart_service_provider_name = (TextView) itemView.findViewById(R.id.sci_cart_service_provider_name);
            cart_service_selected_date = (TextView)itemView.findViewById(R.id.sci_cart_service_selected_date);
            cart_service_partner_list_recycler_view =(RecyclerView)itemView.findViewById(R.id.sci_cart_service_partner_list_recycler_view);
            sci_cart_service_confirm_button = (Button)itemView.findViewById(R.id.sci_cart_service_confirm_button);
            sci_cart_service_cancel_button = (Button)itemView.findViewById(R.id.sci_cart_service_cancel_button);
            sci_button_layout = (LinearLayout)itemView.findViewById(R.id.sci_button_layout);
        }
    }


}
