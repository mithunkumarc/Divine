package divine.calcify.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.com.divine.R;
import divine.calcify.model.ServiceCart;

/**
 * Created by Calcify3 on 06-10-2016.
 */

public class CartServiceAdapter extends RecyclerView.Adapter<CartServiceAdapter.CartServiceViewHolder> {

    Activity activity;
    ArrayList<ServiceCart> serviceCartArrayList = new ArrayList<>();


    public CartServiceAdapter(Activity activity, ArrayList<ServiceCart> serviceCartArrayList){
        this.activity = activity;
        this.serviceCartArrayList = serviceCartArrayList;
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


        cartServiceViewHolder.cart_service_name.setText(serviceCartArrayList.get(i).getServiceName());
        //dont show view if no services has service providers(partners)
        cartServiceViewHolder.cart_service_cost.setText(serviceCartArrayList.get(i).getCost());
        cartServiceViewHolder.cart_service_provider_name.setText(serviceCartArrayList.get(i).getPartnerName());
        cartServiceViewHolder.cart_service_date_time.setText(serviceCartArrayList.get(i).getDate()+", "+serviceCartArrayList.get(i).getTime());

        if (serviceCartArrayList.get(i).getStatus().equalsIgnoreCase("pending")){
            cartServiceViewHolder.cart_service_status.setTextColor(Color.parseColor("#FF0000"));
            cartServiceViewHolder.cart_service_status.setText(serviceCartArrayList.get(i).getStatus());
        }else  {
            cartServiceViewHolder.cart_service_status.setTextColor(Color.parseColor("#008000"));
            cartServiceViewHolder.cart_service_status.setText(serviceCartArrayList.get(i).getStatus());
        }
        cartServiceViewHolder.cart_service_remove_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceCartArrayList.size();
    }

    public static class CartServiceViewHolder extends RecyclerView.ViewHolder {

        TextView cart_service_cost;
        TextView cart_service_name;
        TextView cart_service_provider_name;
        TextView cart_service_date_time;
        TextView cart_service_remove_link;
        TextView cart_service_status;
        CartServiceViewHolder(View itemView) {
            super(itemView);
            cart_service_cost = (TextView)itemView.findViewById(R.id.sci_cart_service_cost);
            cart_service_name = (TextView) itemView.findViewById(R.id.sci_cart_service_name);
            cart_service_provider_name = (TextView) itemView.findViewById(R.id.sci_cart_service_provider_name);
            cart_service_date_time = (TextView)itemView.findViewById(R.id.sci_cart_date_time);
            cart_service_remove_link = (TextView)itemView.findViewById(R.id.sci_remove_link);
            cart_service_status = (TextView)itemView.findViewById(R.id.sci_cart_service_status);
        }
    }


}
