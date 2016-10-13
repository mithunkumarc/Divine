package divine.calcify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.adapters.DivineServiceAdapter;
import divine.calcify.adapters.DivineTempleEventAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.model.DivineGroupsEvents;
import divine.calcify.model.DivineServices;


public class EventsFragment extends Fragment {

    private RecyclerView hs_events_recylerview;
    //DivineTempleEventAdapter adapter;
    DivineTempleEventAdapter adapter = new DivineTempleEventAdapter(getActivity());


    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        hs_events_recylerview = (RecyclerView)rootView.findViewById(R.id.hs_events_recylerview);
        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(getActivity());
        hs_events_recylerview.setLayoutManager(hs_linearLayout);
        hs_events_recylerview.setHasFixedSize(true);
        //initializeData();

        initializeAdapter();

        //return inflater.inflate(R.layout.fragment_services, container, false);
        return rootView;
    }

    /*private void initializeData(){
        divineServices = new ArrayList<DivineServices>();
        divineServices.add(new DivineServices("Group 1", "description"));
        divineServices.add(new DivineServices("Group 2", "description"));
        divineServices.add(new DivineServices("Group 3", "description"));
    }*/

    public void initializeAdapter(){
        DivineTempleEventAdapter adapter = new DivineTempleEventAdapter(getActivity(), HomeScreenActivity.divineGroupsEventsArrayList);
        adapter.notifyDataSetChanged();
        hs_events_recylerview.setAdapter(adapter);

    }

    public void updateAdapterWithSearchData(ArrayList<DivineGroupsEvents> divineGroupsEventses){
        adapter.notifyAdapter(divineGroupsEventses);
    }

}
