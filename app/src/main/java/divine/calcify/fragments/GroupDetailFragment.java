package divine.calcify.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.Utility.WebServiceHelper;
import divine.calcify.activities.CalendarScreenActivity;
import divine.calcify.activities.DetailGroupActivity;
import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.adapters.GroupDetailAdapter;
import divine.calcify.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import divine.calcify.com.divine.R;
import divine.calcify.interfaces.GroupDetailCallback;
import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.Partner;
import divine.calcify.model.Services;
import divine.calcify.webservices.DivineServicesWebService;


public class GroupDetailFragment extends Fragment implements GroupDetailCallback {


        public GroupDetailAdapter groupDetailAdapter;
        //partners available for seleted service
        public static ArrayList<Partner> partnerArrayList = new ArrayList<>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.group_detail_fragment, container, false);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(
                    R.id.fragment_list_rv);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            //index is used to load services for corresponding fragment
            int index = getArguments().getInt("INDEX");
            groupDetailAdapter = new GroupDetailAdapter(getActivity(),HomeScreenActivity.divineGroupServicesArrayList.get(index),this);
            recyclerView.setAdapter(groupDetailAdapter);
            return view;
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void updateServiceState(int position){
        groupDetailAdapter.refreshGroupDetailAdapter(position);
        //this.recyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void refreshAdapter(int position) {
        groupDetailAdapter.refreshGroupDetailAdapter(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        //GroupDetailFragment.partnerArrayList.clear();
    }
    public void showCalendar(Services services){

        int serviceCount = Integer.parseInt(services.getService_count());
        if(serviceCount>0){
            //new CheckTypesTask(services.getService_id()).execute();
            //Toast.makeText(getActivity(),"show calendar for "+services.getService_name(),Toast.LENGTH_SHORT).show();
            //DivineServicesWebService listOfPartnersOfServiceWebservice = new DivineServicesWebService(getActivity(), DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY, services.getService_id(),HomeScreenActivity.locations.getLocation_id());
            //listOfPartnersOfServiceWebservice.execute();
            //Log.d("******",GroupDetailFragment.partnerArrayList.size()+"");
            /*try {
                WebServiceHelper.getPartnerList(getActivity(),services);
            }catch (Exception e){
                Log.d("webservice helper Msg=",e.getMessage());
            }*/

            Intent intent = new Intent(getActivity(),CalendarScreenActivity.class);
            //intent.putExtra("partners",GroupDetailFragment.partnerArrayList.size()+"")
            intent.putExtra("service_object",services);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(),"No partners are available",Toast.LENGTH_SHORT).show();
        }
    }
}
