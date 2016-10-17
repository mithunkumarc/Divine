package divine.calcify.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.EventCartFragment;
import divine.calcify.fragments.ServiceCartFragment;
import divine.calcify.model.Locations;
import divine.calcify.model.Partner;
import divine.calcify.model.ServiceCart;
import divine.calcify.model.ServiceCartListByDates;
import divine.calcify.model.Services;
import divine.calcify.webservices.DivineServicesWebService;

public class CartActivity extends AppCompatActivity {
    //public static ArrayList<ServiceCart> serviceCartArrayList = new ArrayList<>();
    public static ArrayList<ServiceCartListByDates> serviceCartListByDatesArrayList = new ArrayList<>();
    Services services;
    TabLayout cartTabLayout;
    ViewPager cartViewPager;
    Partner partner;
    String cart_key;
    String calendar_selected_date;
    String calendar_selected_time;
    String calendar_selected_day;
    ServiceCartFragment serviceCartFragment = new ServiceCartFragment();
    EventCartFragment eventCartFragment = new EventCartFragment();
    //badge
    TextView badgeCount;
    int badge_count=0;
    String userMobileNumberAsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });


        SharedPreferences userSharedPref = getSharedPreferences(RegisterOtpScreenActivity.UserAccountInfo, Context.MODE_PRIVATE);
        userMobileNumberAsId = userSharedPref.getString(RegisterOtpScreenActivity.UserMobileNumber,"");

        cartViewPager = (ViewPager) findViewById(R.id.cart_viewPager);
        cartTabLayout = (TabLayout)findViewById(R.id.cart_tabLayout);
        cartTabLayout.setBackgroundColor(getResources().getColor(R.color.black));
        cartTabLayout.setTabTextColors(getResources().getColor(R.color.white),getResources().getColor(R.color.white));
        cartTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        cartViewPager.setAdapter(new CustomCartAdapter(getSupportFragmentManager(),getApplicationContext()));

        cartTabLayout.setupWithViewPager(cartViewPager);

        cartTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cartViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                cartViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                cartViewPager.setCurrentItem(tab.getPosition());
            }
        });

        //cart_key = getIntent().getStringExtra("cart_data");// DivineKeyWords.SERVICE_CART_DATA
        //if(serviceCartArrayList.size()==0){
            serviceCartListByDatesArrayList.clear();
            DivineServicesWebService getServiceCartListWS = new DivineServicesWebService(this,DivineKeyWords.GET_SERVICES_LIST_FROM_CART_KEY,userMobileNumberAsId);
            try{
                Double result = getServiceCartListWS.execute().get();
            }catch (Exception e){
                Log.d("getServiceCartListEr:",e.getMessage());
            }
            //updateBadgeCount(serviceCartListByDatesArrayList.size());
            invalidateOptionsMenu();

        //}


    }

    private class CustomCartAdapter extends FragmentPagerAdapter {

        private String fragments [] = {"Service","Event"};

        public CustomCartAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return serviceCartFragment;
                case 1:
                    return eventCartFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);

        final View notificaitons = menu.findItem(R.id.menu_cart).getActionView();
        badgeCount = (TextView) notificaitons.findViewById(R.id.badgeCount);
        badge_count = badge_count+1;
        updateBadgeCount(serviceCartListByDatesArrayList.size());

        /*notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });*/
        return true;
    }

    //update badge count
    public void updateBadgeCount(final int badge_count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                badgeCount.setText(badge_count+"");
            }
        });
    }


}
