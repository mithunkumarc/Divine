package divine.calcify.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.Utility.Utilities;
import divine.calcify.activities.CartActivity;
import divine.calcify.adapters.CartServiceAdapter;
import divine.calcify.adapters.DivineTempleEventListAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.model.ServiceCart;
import divine.calcify.model.Services;
import divine.calcify.webservices.DivineServicesWebService;


public class ServiceCartFragment extends Fragment {
        CartServiceAdapter cartServiceAdapter;
        RecyclerView cart_recycler_view;
        TextView cart_service_total;
        Double totalServiceCartAmout;
        //LinearLayout cart_service_purchase_order;
        String selected_cart_id;
        public static String purchaseOrderServiceCartResult;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_service_cart, container, false);
            cart_recycler_view = (RecyclerView)rootView.findViewById(R.id.cart_service_recycler_view);
            //cart_service_total = (TextView)rootView.findViewById(R.id.cart_service_total);
            //cart_service_purchase_order = (LinearLayout) rootView.findViewById(R.id.cart_service_purchase_order);
            LinearLayoutManager hs_linearLayout = new LinearLayoutManager(getActivity());
            cart_recycler_view.setLayoutManager(hs_linearLayout);
            cart_recycler_view.setHasFixedSize(true);
            ArrayList<Services> servicesArrayList = addAllServicesList();
            cartServiceAdapter = new CartServiceAdapter(getActivity(), servicesArrayList);
            cartServiceAdapter.notifyDataSetChanged();
            cart_recycler_view.setAdapter(cartServiceAdapter);
            /*if (CartActivity.serviceCartArrayList.size()>0){
                totalServiceCartAmout = Utilities.getTotalAmoutOfServiceCartList();
            }*/
            String rupee_symbol = getResources().getString(R.string.rupee_symbol);
            //cart_service_total.setText("Total("+rupee_symbol+totalServiceCartAmout+"/-)");
            //return inflater.inflate(R.layout.fragment_services, container, false);
            /*cart_service_purchase_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CartActivity.serviceCartArrayList.size()==1){
                        selected_cart_id = CartActivity.serviceCartArrayList.get(0).getCartId();
                        sendPurchaseOrderServiceCart(getActivity());
                    }
                }
            });*/
            return rootView;



        }



        public ServiceCartFragment(){

        }

        /*private class CheckTypesTask extends AsyncTask<Void, Void, Void> {
            ProgressDialog asyncDialog = new ProgressDialog(getActivity());
            String typeStatus;


            @Override
            protected void onPreExecute() {
                //set message of the dialog
                asyncDialog.setMessage("waiting");
                //show dialog
                asyncDialog.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                //don't touch dialog here it'll break the application
                //do some lengthy stuff like calling login webservice
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Fragment> fragmentList = getActivity().getSupportFragmentManager().getFragments();
                        for(Fragment fragment : fragmentList){
                            if(fragment instanceof ServiceCartFragment){
                                ((ServiceCartFragment) fragment).cartServiceAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                //hide the dialog

                super.onPostExecute(result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        asyncDialog.dismiss();
                        //toolbarSearchDialog.dismiss();
                    }
                });
                //load temple_events based on location start
                //loadEventFragmentData();
                //load temple_events based on location end
            }

        }*/
        public void sendPurchaseOrderServiceCart(Activity activity){
            DivineServicesWebService purchaseOrderServiceCartWS = new DivineServicesWebService(getActivity(), DivineKeyWords.PURCHASE_ORDER_SERVICE_CART_KEY,selected_cart_id);
            try{
                Double result = purchaseOrderServiceCartWS.execute().get();
            }
            catch (Exception e){
                Log.d("purchase order err:",e.getMessage());
            }
            if (purchaseOrderServiceCartResult.equalsIgnoreCase("false")){
                Toast.makeText(getActivity().getApplicationContext(),"successfully placed order",Toast.LENGTH_SHORT);
            }
        }
    //adding all services list to one
    public ArrayList<Services> addAllServicesList(){
        ArrayList<Services> servicesArrayList = new ArrayList<>();
        for (int index = 0; index<CartActivity.serviceCartListByDatesArrayList.size();index++){
            servicesArrayList.addAll(CartActivity.serviceCartListByDatesArrayList.get(index).getServicesArrayList());
        }
        return servicesArrayList;
    }

    }