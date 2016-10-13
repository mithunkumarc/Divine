package divine.calcify.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import divine.calcify.activities.CalendarScreenActivity;
import divine.calcify.activities.DetailTempleActivity;
import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.adapters.GroupDetailAdapter;
import divine.calcify.adapters.TempleDetailAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.interfaces.GroupDetailCallback;
import divine.calcify.model.Partner;
import divine.calcify.model.Services;

public class TempleDetailFragment extends Fragment {


    public TempleDetailAdapter templeDetailAdapter;
    //partners available for seleted service
    public static ArrayList<Partner> partnerArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_temple_detail, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(
                R.id.fragment_list_detail_temple);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //index is used to load services for corresponding fragment
        int index = getArguments().getInt("INDEX");
        templeDetailAdapter = new TempleDetailAdapter(getActivity(), DetailTempleActivity.templeArrayList.get(index).getEventsArrayList(),this);
        recyclerView.setAdapter(templeDetailAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

/*
    public void updateServiceState(int position){
        groupDetailAdapter.refreshGroupDetailAdapter(position);
        //this.recyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void refreshAdapter(int position) {
        groupDetailAdapter.refreshGroupDetailAdapter(position);
    }
*/

    @Override
    public void onResume() {
        super.onResume();
        //GroupDetailFragment.partnerArrayList.clear();
    }
    /*public void showCalendar(Services services){

        int serviceCount = Integer.parseInt(services.getService_count());
        if(serviceCount>0){
            //new CheckTypesTask(services.getService_id()).execute();
            //Toast.makeText(getActivity(),"show calendar for "+services.getService_name(),Toast.LENGTH_SHORT).show();
            //DivineServicesWebService listOfPartnersOfServiceWebservice = new DivineServicesWebService(getActivity(), DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY, services.getService_id(),HomeScreenActivity.locations.getLocation_id());
            //listOfPartnersOfServiceWebservice.execute();
            //Log.d("******",GroupDetailFragment.partnerArrayList.size()+"");
            *//*try {
                WebServiceHelper.getPartnerList(getActivity(),services);
            }catch (Exception e){
                Log.d("webservice helper Msg=",e.getMessage());
            }*//*

            Intent intent = new Intent(getActivity(),CalendarScreenActivity.class);
            //intent.putExtra("partners",GroupDetailFragment.partnerArrayList.size()+"")
            intent.putExtra("service_object",services);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(),"No partners are available",Toast.LENGTH_SHORT).show();
        }
    }*/
}