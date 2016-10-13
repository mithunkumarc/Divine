package divine.calcify.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.adapters.EventListParentAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.interfaces.ShowEventListCallback;
import divine.calcify.model.Events;
import divine.calcify.model.Temple;
import divine.calcify.webservices.DivineServicesWebService;

public class ShowTempleListActivity extends AppCompatActivity implements ShowEventListCallback {
    RecyclerView event_recycler_view_parent;
    EventListParentAdapter event_list_parent_adapter;
    public static Events event_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_temple_list);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.stl_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayList<Events> tempListEvents = (ArrayList<Events>) getIntent().getSerializableExtra("events_list");
        HashMap<String,ArrayList<Events>> dateEventsMap = new HashMap<String,ArrayList<Events>>();

        for(int index=0;index<tempListEvents.size();index++){
            String key = tempListEvents.get(index).getEventDate();
            ArrayList<Events> arrayEventsList = new ArrayList<Events>();
            for(int innerIndex=0;innerIndex<tempListEvents.size();innerIndex++){
                if(key.equals(tempListEvents.get(innerIndex).getEventDate())){
                    arrayEventsList.add(tempListEvents.get(innerIndex));
                }
                dateEventsMap.put(key, arrayEventsList);
            }
        }
        /*Iterator it = dateEventsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ArrayList<Events> arrayLIst = (ArrayList<Events>)pair.getValue();
            for(Events events : arrayLIst){
                System.out.println(pair.getKey()+"=="+events.getEventId());
                System.out.println(events.getEventName());
            }
        }*/

        event_recycler_view_parent = (RecyclerView) findViewById(R.id.temple_event_recycler_view_parent);
        event_list_parent_adapter = new EventListParentAdapter(dateEventsMap,ShowTempleListActivity.this);
        event_recycler_view_parent.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        event_recycler_view_parent.setLayoutManager(mLayoutManager);
        event_recycler_view_parent.setItemAnimator(new DefaultItemAnimator());
        event_recycler_view_parent.setAdapter(event_list_parent_adapter);

    }

    //show event information
    public void displayEventInformaion(Events events){
        DivineServicesWebService eventInfoWS = new DivineServicesWebService(ShowTempleListActivity.this, DivineKeyWords.GET_EVENT_INFORMATION_KEY,events.getEventId());
        try {
            Double result = (Double)eventInfoWS.execute().get();
        }catch (Exception e){
            Log.d("eventWS error-",e.getMessage());
        }
        Intent intent = new Intent(ShowTempleListActivity.this,DisplayEventInformation.class);
        intent.putExtra("event_object",event_info);
        startActivity(intent);
    }
}
