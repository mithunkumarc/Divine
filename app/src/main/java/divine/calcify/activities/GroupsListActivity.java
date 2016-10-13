package divine.calcify.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import divine.calcify.adapters.GroupsAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.interfaces.GroupListCallBack;
import divine.calcify.model.DivineGroupServices;

public class GroupsListActivity extends AppCompatActivity implements GroupListCallBack {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    public RecyclerView groups_list_recyclerview;
    public TextView group_list_hs_location;//selected location
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_list_activity);
        Toolbar toolbar = (Toolbar)findViewById(R.id.group_list_grid_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_back);
        group_list_hs_location = (TextView)findViewById(R.id.group_list_hs_location);
        group_list_hs_location.setText(HomeScreenActivity.hs_selected_location);
        //mDrawerList = (ListView)findViewById(R.id.navList);
        //mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //mActivityTitle = getTitle().toString();
        //addDrawerItems();
        //setupDrawer();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        groups_list_recyclerview = (RecyclerView)findViewById(R.id.groups_list_recycler_view);


        groups_list_recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        groups_list_recyclerview.setLayoutManager(layoutManager);


        GroupsAdapter adapter = new GroupsAdapter(this,HomeScreenActivity.divineGroupServicesArrayList);
        groups_list_recyclerview.setAdapter(adapter);

        Toast.makeText(getApplicationContext(),HomeScreenActivity.divineAllGroupList.size()+"",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

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
                getSupportActionBar().setTitle("Navigation!");
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

    //to open list of all groups or events
    public void openGroupsOrEvents(String groupOrEvents){
        if(groupOrEvents.equalsIgnoreCase("Groups")){
            if(HomeScreenActivity.divineAllGroupList.size()>0){
                Toast.makeText(getApplicationContext(),"groups",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(HomeScreenActivity.this,GroupsListActivity.class);
                //startActivity(intent);
            }
        }
        if (groupOrEvents.equalsIgnoreCase("Events")){
            Toast.makeText(getApplicationContext(), "events", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        if(id == R.id.menu_search){
            Toast.makeText(getApplicationContext(),"search selected",Toast.LENGTH_SHORT).show();
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showGroupDetailActivity(DivineGroupServices divineGroupServices) {
        Intent intent = new Intent(this,DetailGroupActivity.class);
        intent.putExtra("tab_name",divineGroupServices.getGroupServiceName().trim());
        startActivity(intent);
    }
}
