package divine.calcify.activities;
//this activity shows the list of partners available for selected services
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.adapters.ServicePartnerListAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.fragments.ServicesFragment;
import divine.calcify.interfaces.ServicePartnerListCallBack;
import divine.calcify.model.Partner;
import divine.calcify.model.Services;
import divine.calcify.webservices.DivineServicesWebService;

public class ServicePartnerListActivity extends AppCompatActivity implements ServicePartnerListCallBack {
    public static ArrayList<Partner> selectedPartnerList = new ArrayList<>();//final list of selected partners to send quote amount
    public Services services;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Button spl_quote_button;
    public TextView spl_date;
    public ImageView spl_filter_partners;
    public String selected_date;
    public String selected_time;
    public String calendar_selected_day;
    public Button spl_cart_button;
    public String userName;
    public String userMobileNumber;
    public static String insertServiceCartResult;//result of insert service cart details


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_partner_list_activity);
        //change status bar to primary dark

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        SharedPreferences userSharedPref = getSharedPreferences(RegisterOtpScreenActivity.UserAccountInfo, Context.MODE_PRIVATE);
        userName = userSharedPref.getString(RegisterOtpScreenActivity.UserName, "");
        userMobileNumber = userSharedPref.getString(RegisterOtpScreenActivity.UserMobileNumber,"");
        spl_cart_button = (Button)findViewById(R.id.spl_cart_button);
        spl_date = (TextView)findViewById(R.id.spl_date);
        spl_filter_partners= (ImageView)findViewById(R.id.spl_filter_partners);
        services = (Services) getIntent().getSerializableExtra("service_object");
        selected_date = getIntent().getStringExtra("calendar_selected_date");
        selected_time = getIntent().getStringExtra("calendar_selected_time");
        calendar_selected_day = getIntent().getStringExtra("calendar_selected_day");

        try{
            //to get the list of partners available for the selected services
            DivineServicesWebService listOfPartnersOfServiceWebservice = new DivineServicesWebService(ServicePartnerListActivity.this, DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY, services.getService_id(), HomeScreenActivity.locations.getLocation_id());
            Double result = (Double) listOfPartnersOfServiceWebservice.execute().get();
        }catch (Exception ex){
            ex.getStackTrace();
        }



        Toolbar toolbar = (Toolbar)findViewById(R.id.spl_toolbar);
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

        /*spl_date = (TextView)findViewById(R.id.spl_date);
        spl_date.setText(CalendarScreenActivity.selectedServicePartnerListDate);
        */
        spl_quote_button = (Button)findViewById(R.id.spl_quote_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.spl_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ServicePartnerListAdapter(getApplicationContext(),this);
        mRecyclerView.setAdapter(mAdapter);





        spl_quote_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int index=0;index< GroupDetailFragment.partnerArrayList.size();index++){
                    if(GroupDetailFragment.partnerArrayList.get(index).isSelected()){
                        selectedPartnerList.add(GroupDetailFragment.partnerArrayList.get(index));
                    }
                }
                if(selectedPartnerList.size()==0){
                    Toast.makeText(getApplicationContext(),"please select partners to quote",Toast.LENGTH_SHORT).show();
                }if(selectedPartnerList.size()>0) {
                    Intent intent = new Intent(ServicePartnerListActivity.this,SendQuoteToPartnerActivity.class);
                    intent.putExtra("service_object",services);//access date from calender activity
                    intent.putExtra("calendar_selected_date",selected_date);
                    intent.putExtra("calendar_selected_time",selected_time);
                    intent.putExtra("calendar_selected_day",calendar_selected_day);
                    startActivity(intent);
                }

            }
        });
        spl_date.setText(selected_date+", "+selected_time+", "+calendar_selected_day);

        //be ready with sorted data, below is for sort in ascending order of service cost
        Collections.sort(GroupDetailFragment.partnerArrayList,new Partner.PartnerComparator());

        spl_filter_partners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(GroupDetailFragment.partnerArrayList);
                mAdapter.notifyDataSetChanged();
               /* System.out.println("After sorting");
                for (Partner partner : GroupDetailFragment.partnerArrayList) {
                    Log.d("name: ",partner.getPartnerName());
                }*/
            }
        });
        //cart button
        spl_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int index=0;index< GroupDetailFragment.partnerArrayList.size();index++){
                    if(GroupDetailFragment.partnerArrayList.get(index).isSelected()){
                        selectedPartnerList.add(GroupDetailFragment.partnerArrayList.get(index));
                    }
                }
                if(selectedPartnerList.size()==0){
                    Toast.makeText(getApplicationContext(),"select item for Add to cart",Toast.LENGTH_SHORT).show();
                }
                if (selectedPartnerList.size()==1){
                    insertServiceCartDetails(ServicePartnerListActivity.this);
                }
                if(selectedPartnerList.size()>1){
                    Toast.makeText(getApplicationContext(),"select single item for Add to cart",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void showPartnerProfileInfo(Partner partner) {
        Toast.makeText(getApplicationContext(),"show profile",Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this,PartnerProfileInfoActivity.class);
        Intent intent = new Intent(this,PartnerProfileInformationActvity.class);
        intent.putExtra("partner_object",partner);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_notifications:
                    Intent intent = new Intent(ServicePartnerListActivity.this,CartActivity.class);
                    startActivity(intent);
                return true;
            case R.id.menu_cart:
                    Intent intent1 = new Intent(ServicePartnerListActivity.this,CartActivity.class);
                    startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //to enable button after partner is selected(atleast one
    @Override
    public void changeButtonColor() {
        if(countSelectedPartners()){
            spl_quote_button.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.border_line_on));
            spl_quote_button.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
        }else {
            spl_quote_button.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.border_line_off));
            spl_quote_button.setTextColor(getApplicationContext().getResources().getColor(R.color.text_color_off));
        }
    }

    //helper method to count the selected partners, so that to enable button sendquote
    public boolean countSelectedPartners(){
        for(int index=0;index< GroupDetailFragment.partnerArrayList.size();index++){
            if(GroupDetailFragment.partnerArrayList.get(index).isSelected()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GroupDetailFragment.partnerArrayList.clear();
        mAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    public void insertServiceCartDetails(Activity activity){
        //add to cart for services
        Partner partner = selectedPartnerList.get(0);
        //this selected cart must be saved into db
        HashMap<String,String> serviceCartInsertMap = new HashMap<>();
        serviceCartInsertMap.put("serviceName",services.getService_name());
        serviceCartInsertMap.put("serviceID",services.getService_id());
        serviceCartInsertMap.put("serviceDate",selected_date);
        serviceCartInsertMap.put("serviceTime",selected_time);
        serviceCartInsertMap.put("locationName",partner.getLocation());
        serviceCartInsertMap.put("locationId",partner.getLocationId());
        serviceCartInsertMap.put("serviceAmount",partner.getServiceCost());
        serviceCartInsertMap.put("partnerName",partner.getPartnerName());
        serviceCartInsertMap.put("partnerID",partner.getUserID());//partnerId
        serviceCartInsertMap.put("customerName",userName);//customer name from shared pref
        serviceCartInsertMap.put("customerMobileNumber",userMobileNumber);//from shared pref

        DivineServicesWebService inseriCartDetailsWS = new DivineServicesWebService(this,DivineKeyWords.INSERT_SERVICE_CART_DETAILS_KEY,serviceCartInsertMap);
        try {
            Double result = (Double) inseriCartDetailsWS.execute().get();
        }catch (Exception e){
            Log.d("insert service cart er:",e.getMessage());
        }
        //db
        /*Intent intent = new Intent(ServicePartnerListActivity.this,CartActivity.class);
        intent.putExtra("service_object",services);//access date from calender activity
        intent.putExtra("calendar_selected_date",selected_date);
        intent.putExtra("calendar_selected_time",selected_time);
        intent.putExtra("calendar_selected_day",calendar_selected_day);
        intent.putExtra("partner_object",partner);
        intent.putExtra("cart_data",DivineKeyWords.SERVICE_CART_DATA);*/
        if (insertServiceCartResult.equalsIgnoreCase("false")){//insert success
            //startActivity(intent);
            Toast.makeText(getApplicationContext(),"item saved in cart successfully",Toast.LENGTH_SHORT).show();
        }


    }
}