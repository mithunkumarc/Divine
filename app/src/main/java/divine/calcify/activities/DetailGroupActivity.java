package divine.calcify.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import divine.calcify.com.divine.R;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.interfaces.GroupDetailCallback;
import divine.calcify.model.DivineGroupServices;

public class DetailGroupActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_group_activity_layout);
        //change status bar to primary dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        setupToolbar();
        setupCollapsingToolbar();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(int index = 0;index<HomeScreenActivity.divineGroupServicesArrayList.size();index++){
            Bundle bundle = new Bundle();
            //index is used to match services with group in fragment while initialization
            //i.e, corresponding fragments will load its list of services. each fragment represents group(god).
            bundle.putInt("INDEX", index);
            // set Fragmentclass Arguments
            GroupDetailFragment groupDetailFragment = new GroupDetailFragment();
            groupDetailFragment.setArguments(bundle);
            adapter.addFrag(groupDetailFragment, HomeScreenActivity.divineGroupServicesArrayList.get(index).getGroupServiceName());
        }
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.WHITE,Color.WHITE);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        //intent.getExtras().getString("epuzzle");
        String tab_name = getIntent().getStringExtra("tab_name");
        if(tab_name!=null){
            int tab_count = tabLayout.getTabCount();
            for (int index = 0; index < tab_count ; index++){
                TabLayout.Tab tab = tabLayout.getTabAt(index);
                if(tab_name.equalsIgnoreCase(tab.getText().toString())){
                    tab.select();
                }
            }
        }
    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        //collapsingToolbar.setTitle("location");
        collapsingToolbar.setTitleEnabled(false);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_group_service_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


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
    public void onBackPressed() {
        super.onBackPressed();
        HomeScreenActivity.selectedGroupFromSearch.clear();
    }
}
