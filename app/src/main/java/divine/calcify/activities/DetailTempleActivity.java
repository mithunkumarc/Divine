package divine.calcify.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.fragments.TempleDetailFragment;
import divine.calcify.interfaces.ShowEventListCallback;
import divine.calcify.model.Events;
import divine.calcify.model.Temple;
import divine.calcify.webservices.DivineServicesWebService;

public class DetailTempleActivity extends AppCompatActivity implements ShowEventListCallback {
    public ViewPager detail_temple_viewpager;
    public TabLayout detail_temple_tabs;
    public Toolbar detail_temple_toolbar;
    public CollapsingToolbarLayout detail_temple_collapse_toolbar;
    public ViewPagerAdapter detail_temple_viewpager_adapter;
    public static ArrayList<Temple> templeArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_temple);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        detail_temple_collapse_toolbar = (CollapsingToolbarLayout)findViewById(R.id.detail_temple_collapse_toolbar);
        detail_temple_collapse_toolbar.setTitleEnabled(false);
        //toolbar
        detail_temple_toolbar = (Toolbar)findViewById(R.id.detail_temple_toolbar);
        setSupportActionBar(detail_temple_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detail_temple_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //tabs
        detail_temple_tabs=(TabLayout)findViewById(R.id.detail_temple_tabs);

        detail_temple_viewpager=(ViewPager)findViewById(R.id.detail_temple_viewpager);
        templeArrayList = (ArrayList<Temple>) getIntent().getSerializableExtra("temples_list");
        detail_temple_viewpager_adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //create dynamic fragments
        for(int index = 0;index<templeArrayList.size();index++){
            Bundle bundle = new Bundle();
            //index is used to match services with group in fragment while initialization
            //i.e, corresponding fragments will load its list of services. each fragment represents group(god).
            bundle.putInt("INDEX", index);
            // set Fragmentclass Arguments
            TempleDetailFragment templeDetailFragment = new TempleDetailFragment();
            templeDetailFragment.setArguments(bundle);
            detail_temple_viewpager_adapter.addFrag(templeDetailFragment, templeArrayList.get(index).getTempleName());
        }

        detail_temple_viewpager.setAdapter(detail_temple_viewpager_adapter);
        detail_temple_tabs.setTabTextColors(Color.WHITE,Color.WHITE);

        detail_temple_tabs.setupWithViewPager(detail_temple_viewpager);
        detail_temple_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(detail_temple_tabs));
        detail_temple_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                /*GroupDetailCallback groupDetailCallback = (GroupDetailCallback) adapter.instantiateItem(viewPager, position);
                if (groupDetailCallback != null) {
                    groupDetailCallback.refreshAdapter(position);
                }*/
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //
        //to select particular tab
        /*String tab_name = getIntent().getStringExtra("tab_name");
        if(tab_name!=null){
            int tab_count = tabLayout.getTabCount();
            for (int index = 0; index < tab_count ; index++){
                TabLayout.Tab tab = tabLayout.getTabAt(index);
                if(tab_name.equalsIgnoreCase(tab.getText().toString())){
                    tab.select();
                }
            }
        }*/

    }
    //viewpager adapter
    static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public final List<Integer> index = new ArrayList<Integer>();
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
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void displayEventInformaion(Events events) {
        DivineServicesWebService eventInfoWS = new DivineServicesWebService(DetailTempleActivity.this, DivineKeyWords.GET_EVENT_INFORMATION_KEY,events.getEventId());
        try {
            Double result = (Double)eventInfoWS.execute().get();
        }catch (Exception e){
            Log.d("eventWS error-",e.getMessage());
        }
        Intent intent = new Intent(DetailTempleActivity.this,DisplayEventInformation.class);
        intent.putExtra("event_object",ShowTempleListActivity.event_info);//reusing eventinfo variable
        startActivity(intent);
    }
}
