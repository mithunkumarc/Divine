package divine.calcify.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import divine.calcify.interfaces.HomeScreenCallBack;
import divine.calcify.Utility.DatabaseHandler;
import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.Utility.JsonParserUtility;
import divine.calcify.adapters.SearchAdapter;
import divine.calcify.adapters.SearchGroupAdapter;
import divine.calcify.adapters.SharedPreference;
import divine.calcify.adapters.Utils;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.ServicesFragment;
import divine.calcify.fragments.EventsFragment;
import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.DivineGroupsEvents;
import divine.calcify.model.Events;
import divine.calcify.model.Locations;
import divine.calcify.model.Services;
import divine.calcify.model.Temple;
import divine.calcify.webservices.DivineServicesWebService;


public class HomeScreenActivity extends AppCompatActivity  implements HomeScreenCallBack {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    Dialog toolbarSearchDialog;
    TextView badgeCount;
    //selected location in locatin popup

    //position of selected location
    public TextView hs_location;
    public static String hs_selected_location;
    //list of groups available irrespective of location
    public static ArrayList<DivineGroupServices> divineAllGroupList = new ArrayList<DivineGroupServices>();
    //services with respect to location
    //group referes to gods
    public static ArrayList<DivineGroupServices> divineGroupServicesArrayList = new ArrayList<DivineGroupServices>();
    public static ArrayList<Locations> locationsArrayList = new ArrayList<Locations>();
    //this list contains only one group object(god) which is selected from search bar
    public static ArrayList<DivineGroupServices> selectedGroupFromSearch = new ArrayList<DivineGroupServices>();
    //This is our tablayout // services and events
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;
    //selected location objec
    public static Locations locations;
    //selected group from search bar
    public static DivineGroupServices search_selected_group;
    //events information
    public static ArrayList<DivineGroupsEvents> divineGroupsEventsArrayList= new ArrayList<>();

    //fragments
    ServicesFragment servicesFragment=new ServicesFragment();
    EventsFragment eventsFragment = new EventsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);

        //fcm token
        SharedPreferences fcmPref = getSharedPreferences(DivineKeyWords.FcmSharedPreferences, Context.MODE_PRIVATE);
        String fcmTOken = fcmPref.getString(DivineKeyWords.FcmToken, "");
        Log.d("fcmToken=",fcmTOken);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        hs_location = (TextView)findViewById(R.id.hs_location);
        if(hs_selected_location!=null){
            hs_location.setText(hs_selected_location);
        }
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.homescreen_tabs);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.homescreen_viewpager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        //if there is no location selected , we need to display services for current location, this not yet implemeted
        if(hs_selected_location==null){
            //no need to call webservice if services list is not empty
            if(HomeScreenActivity.locationsArrayList.size()==0){
                //location webservice////control coming from location
                DivineServicesWebService locationWebservice = new DivineServicesWebService(this, DivineKeyWords.LOCATIONS_KEY);
                locationWebservice.execute();
            }
        }

        //review below code
        if(hs_selected_location==null || hs_selected_location==""){
            loadToolBarSearch();
        }


    }//end oncreate

    private void addDrawerItems() {
        final String[] osArray = { "Groups", "Events" };
        mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openGroupsOrEvents(osArray[position]);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Home");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final View notificaitons = menu.findItem(R.id.menu_cart).getActionView();
        badgeCount = (TextView) notificaitons.findViewById(R.id.badgeCount);
        badgeCount.setText("0");
        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_notifications) {
            Intent intent = new Intent(HomeScreenActivity.this,CartActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.menu_cart){
            Intent intent = new Intent(HomeScreenActivity.this,CartActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.menu_search){
            //group search - start

            //group search - end
            loadGroupSearch();
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(servicesFragment, "ONE");
        adapter.addFrag(eventsFragment, "TWO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setTextColor(Color.WHITE);
        tabOne.setText("Services");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.services_tab_ic, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setTextColor(Color.WHITE);
        tabTwo.setText("Events");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.events_tab_unselected, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }

    //open location window
    public void openLocationWindow(View view){
        //open location search window
        loadToolBarSearch();
    }

    //location search will be opened
    public void loadToolBarSearch() {
        //clear old selected group from searched query
        HomeScreenActivity.selectedGroupFromSearch.clear();
        //below key hold for some time until bug is fixed
        ArrayList<Locations> locationStored = SharedPreference.loadList(HomeScreenActivity.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES);


        final View view = HomeScreenActivity.this.getLayoutInflater().inflate(R.layout.hs_view_toolbar_search, null);

        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        //i changed to search
        final ImageView imgToolcancel = (ImageView) view.findViewById(R.id.img_tool_cancel);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);


        //when edit field has no data , no need to show cancel icon

        Utils.setListViewHeightBasedOnChildren(listSearch);

        edtToolSearch.setHint("Search your location");

        toolbarSearchDialog = new Dialog(HomeScreenActivity.this, R.style.MaterialSearch);
        toolbarSearchDialog.setCanceledOnTouchOutside(true);
        toolbarSearchDialog.setContentView(view);
        //toolbarSearchDialog.setCancelable(true);

        toolbarSearchDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        toolbarSearchDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        //toolbarSearchDialog.setCanceledOnTouchOutside(true);

        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        locationStored = (locationStored != null && locationStored.size() > 0) ? locationStored : new ArrayList<Locations>();
        //bug , recent loc no available
        final SearchAdapter searchAdapter = new SearchAdapter(HomeScreenActivity.this, false,locationStored);
        //final SearchAdapter searchAdapter = new SearchAdapter(HomeScreenActivity.this, false,HomeScreenActivity.locationsArrayList);

        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchAdapter);


        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //String country = String.valueOf(adapterView.getItemAtPosition(position));
                locations = (Locations) adapterView.getItemAtPosition(position);
                //update location name in location name box
                HomeScreenActivity.divineGroupServicesArrayList.clear();
                updateLocationWithServices(locations);

                Toast.makeText(getApplication(),locations.getLocation_name()+""+locations.getLocation_id(),Toast.LENGTH_SHORT).show();
                SharedPreference.addList(HomeScreenActivity.this, Utils.PREFS_NAME, Utils.KEY_COUNTRIES, locations);
                edtToolSearch.setText(locations.getLocation_name());

                listSearch.setVisibility(View.GONE);
                //need to refresh adapter here
                //toolbarSearchDialog.dismiss();
                new CheckTypesTask().execute();

            }
        });

        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listSearch.setVisibility(View.VISIBLE);
                imgToolcancel.setVisibility(View.VISIBLE);
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(true,HomeScreenActivity.locationsArrayList);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Locations> filterList = new ArrayList<Locations>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < HomeScreenActivity.locationsArrayList.size(); i++) {


                        if (HomeScreenActivity.locationsArrayList.get(i).getLocation_name().toLowerCase().startsWith(s.toString().trim().toLowerCase())) {

                            filterList.add(HomeScreenActivity.locationsArrayList.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList( true,filterList);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtToolSearch.getText().length()==0){
                    imgToolcancel.setVisibility(View.INVISIBLE);
                }else{
                    imgToolcancel.setVisibility(View.VISIBLE);
                }
            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

        imgToolcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtToolSearch.setText("");
                imgToolcancel.setVisibility(View.INVISIBLE);

            }
        });


    }

    //after location changed, put this method in utilities
    public void updateLocationWithServices(Locations locations){
        //onRestart();
        hs_selected_location = locations.getLocation_name();
        hs_location.setText(hs_selected_location);
        Log.d("selected location = ",locations.getLocation_name());
        DivineServicesWebService divineServicesWebService = new DivineServicesWebService(this, DivineKeyWords.SERVICES_KEY, locations.getLocation_id());
        divineServicesWebService.execute();
    }



    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(HomeScreenActivity.this, HomeScreenActivity.class);  //your class
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //to open list of all groups or events
    public void openGroupsOrEvents(String groupOrEvents){
        if(groupOrEvents.equalsIgnoreCase("Groups")){
            if(HomeScreenActivity.divineGroupServicesArrayList.size()>0){
                Toast.makeText(getApplicationContext(),HomeScreenActivity.divineAllGroupList.size()+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeScreenActivity.this,GroupsListActivity.class);
                startActivity(intent);
            }
        }
        if (groupOrEvents.equalsIgnoreCase("Events")){
            Toast.makeText(HomeScreenActivity.this, groupOrEvents, Toast.LENGTH_SHORT).show();
        }
    }


    //----------
    @Override
    protected void onStart() {
        super.onStart();

    }
    //----------------------------------------------------
    @Override
    protected void onStop() {
        super.onStop();

    }

    //control goes to groupdetail activity and groupName is being used to show corresponding tab
    public void callGroupActivity(String groupName){
        Intent intent = new Intent(HomeScreenActivity.this,DetailGroupActivity.class);
        intent.putExtra("tab_name",groupName);//groupname helps to select tab name
        startActivity(intent);
    }



    //update home location
    public void updateHomeLocation(View view){
        Toast.makeText(getApplicationContext(),"update home location",Toast.LENGTH_SHORT).show();
    }

    //////////asnc taskstart- allow activity to load new data basis of selected location
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(HomeScreenActivity.this);
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                    for(Fragment fragment : fragmentList){
                        if(fragment instanceof ServicesFragment){
                            ((ServicesFragment) fragment).initializeAdapter();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    asyncDialog.dismiss();
                    toolbarSearchDialog.dismiss();
                }
            });
            //load temple_events based on location start
            loadEventFragmentData();
            //load temple_events based on location end
        }

    }
    ////////////asnc task end

    //group search
    public void loadGroupSearch(){
        ArrayList<DivineGroupServices> searchGroupServiceslist = new ArrayList<>();
        final View view = HomeScreenActivity.this.getLayoutInflater().inflate(R.layout.hs_view_toolbar_search, null);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        final ImageView imgToolcancel = (ImageView) view.findViewById(R.id.img_tool_cancel);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        Utils.setListViewHeightBasedOnChildren(listSearch);
        edtToolSearch.setHint("Search Groups");

        toolbarSearchDialog = new Dialog(HomeScreenActivity.this, R.style.MaterialSearch);
        toolbarSearchDialog.setCanceledOnTouchOutside(true);
        toolbarSearchDialog.setContentView(view);


        toolbarSearchDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        toolbarSearchDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        //toolbarSearchDialog.setCanceledOnTouchOutside(true);

        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final SearchGroupAdapter searchGroupAdapter = new SearchGroupAdapter(HomeScreenActivity.this, false,searchGroupServiceslist);

        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchGroupAdapter);


        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //get selected group from the suggestion list
                search_selected_group = (DivineGroupServices) adapterView.getItemAtPosition(position);

                selectedGroupFromSearch.add(search_selected_group);
                listSearch.setVisibility(View.GONE);

                toolbarSearchDialog.dismiss();
                //need to refresh adapter here( service fragment adapter)
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                for(Fragment fragment : fragmentList){
                    if(fragment instanceof ServicesFragment){
                        ((ServicesFragment) fragment).updateAdapterWithSearchData();
                    }
                }
            }
        });

        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listSearch.setVisibility(View.VISIBLE);
                imgToolcancel.setVisibility(View.VISIBLE);
                listSearch.setVisibility(View.VISIBLE);
                searchGroupAdapter.updateList( true,HomeScreenActivity.divineGroupServicesArrayList);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<DivineGroupServices> filterList = new ArrayList<DivineGroupServices>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < HomeScreenActivity.divineGroupServicesArrayList.size(); i++) {
                        if (HomeScreenActivity.divineGroupServicesArrayList.get(i).getGroupServiceName().toLowerCase().startsWith(s.toString().trim().toLowerCase())) {
                            filterList.add(HomeScreenActivity.divineGroupServicesArrayList.get(i));
                            listSearch.setVisibility(View.VISIBLE);
                            searchGroupAdapter.updateList(true,filterList);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtToolSearch.getText().length()==0){
                    imgToolcancel.setVisibility(View.INVISIBLE);
                }else{
                    imgToolcancel.setVisibility(View.VISIBLE);
                }
            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

        imgToolcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtToolSearch.setText("");
                imgToolcancel.setVisibility(View.INVISIBLE);
            }
        });
    }

    //show calendar when service is clicked in homescreen
    public void showCalenderOnServiceClickCallBack(Services services){
        if(!services.getService_count().equals("0")){
            Intent intent = new Intent(HomeScreenActivity.this,CalendarScreenActivity.class);
            intent.putExtra("service_object",services);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"service providers not available",Toast.LENGTH_SHORT).show();
        }
    }

    //load temple events based on location
    public void loadEventFragmentData(){
        DivineServicesWebService divineServicesWebService = new DivineServicesWebService(this, DivineKeyWords.EVENTS_KEY, locations.getLocation_id());
        try{
            Double d = divineServicesWebService.execute().get();

            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {

                    eventsFragment.initializeAdapter();
                } // This is your code
            };
            mainHandler.post(myRunnable);
        }catch (Exception e){
            Log.d("LoadEventData=",e.getMessage());
        }
    }

    //show EventList of single temple WithDateScreen
    public void showSingleTempleEventListWithDate(Temple temple){
        Intent intent = new Intent(HomeScreenActivity.this,ShowTempleListActivity.class);
        ArrayList<Events> eventsArrayList = temple.getEventsArrayList();
        intent.putExtra("events_list",eventsArrayList);
        startActivity(intent);
    }

    //changed detail temple activity
    public void showAllTempleEventListWithDate(ArrayList<Temple> templeArrayList){
        /*ArrayList<Events> eventsArrayList = new ArrayList<>();
        for (int index=0;index<templeArrayList.size();index++){
            eventsArrayList.addAll(templeArrayList.get(index).getEventsArrayList());
        }*/
        Intent intent = new Intent(HomeScreenActivity.this,DetailTempleActivity.class);
        intent.putExtra("temples_list",templeArrayList);
        startActivity(intent);
    }

}
