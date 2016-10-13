package divine.calcify.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.adapters.DivineServiceAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.DivineServices;


public class ServicesFragment extends Fragment {

    private RecyclerView homescreen_recylerview;
    private List<DivineServices> divineServices;//old one
    private ArrayList<DivineGroupServices> divineGroupServicesArrayList;
    public Activity activity;
    DivineServiceAdapter adapter;
    public ServicesFragment(){
      //default constructors required
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_services, container, false);
        homescreen_recylerview = (RecyclerView)rootView.findViewById(R.id.homescreen_recylerview);
        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(getActivity());
        homescreen_recylerview.setLayoutManager(hs_linearLayout);
        homescreen_recylerview.setHasFixedSize(true);
        //initializeData();

        initializeAdapter();

        //return inflater.inflate(R.layout.fragment_services, container, false);
        return rootView;
    }

    public void initializeAdapter(){
        adapter = new DivineServiceAdapter(this.getActivity(), HomeScreenActivity.divineGroupServicesArrayList);
        adapter.notifyDataSetChanged();
        homescreen_recylerview.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    public void updateAdapterWithSearchData(){
        adapter.notifyAdapter(HomeScreenActivity.selectedGroupFromSearch);
    }

}
