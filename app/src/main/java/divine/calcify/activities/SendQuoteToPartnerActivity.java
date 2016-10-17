package divine.calcify.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.adapters.SendQuotePartnerAdapter;
import divine.calcify.com.divine.R;
import divine.calcify.model.Locations;
import divine.calcify.model.Partner;
import divine.calcify.model.Services;
import divine.calcify.webservices.DivineServicesWebService;

public class SendQuoteToPartnerActivity extends AppCompatActivity {
    private ArrayList<Partner> sendQuoteForPartnersList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TextView sqp_service_name;
    public TextView sqp_selected_date;
    public TextView sqp_selected_day;
    public TextView sqp_selected_location;
    public EditText sqp_quote_amout;
    public Button sqp_quote_button;

    public Services services;

    public String calendar_selected_date;
    public String calendar_selected_time;
    public String calendar_selected_day;

    public String userName;
    public String userMobileNumber;
    public Locations locations = HomeScreenActivity.locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_quote_to_partner_activity);
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


        services = (Services) getIntent().getSerializableExtra("service_object");
        calendar_selected_date = getIntent().getStringExtra("calendar_selected_date");
        calendar_selected_time = getIntent().getStringExtra("calendar_selected_time");
        calendar_selected_day = getIntent().getStringExtra("calendar_selected_day");
        sqp_service_name = (TextView)findViewById(R.id.sqp_service_name);
        sqp_service_name.setText(services.getService_name());
        sqp_selected_date = (TextView)findViewById(R.id.sqp_selected_date);
        sqp_selected_date.setText(calendar_selected_date);
        sqp_selected_day = (TextView)findViewById(R.id.sqp_selected_day);
        sqp_selected_day.setText(calendar_selected_day+", "+calendar_selected_time);
        sqp_selected_location = (TextView)findViewById(R.id.sqp_selected_location);
        sqp_selected_location.setText(HomeScreenActivity.hs_selected_location);
        sqp_quote_amout = (EditText)findViewById(R.id.sqp_quote_amout);
        sqp_quote_button = (Button)findViewById(R.id.sqp_quote_button);


        Toolbar toolbar = (Toolbar)findViewById(R.id.sqp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.sqp_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SendQuotePartnerAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        sqp_quote_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendQuoteForPartnersList= ServicePartnerListActivity.selectedPartnerList;
                sendQuoteToServiceProviders(SendQuoteToPartnerActivity.this);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ServicePartnerListActivity.selectedPartnerList.clear();
    }

    //sendQuoteToServiceProviders
    public void sendQuoteToServiceProviders(Activity activity){
        //check whether partners selected or not
        if (sendQuoteForPartnersList.size()>0){
            //arraylists are used for comma seperated value to send for webservice
            ArrayList<String> serviceProviderName= new ArrayList<>();
            ArrayList<String> serviceProviderId= new ArrayList<>();
            ArrayList<String> serviceProviderCostForSelectedService = new ArrayList<>();
            String serviceID = services.getService_id();
            String serviceName = services.getService_name();
            String userQuoteAmout = sqp_quote_amout.getText().toString().trim();
            if(!sqp_quote_amout.getText().toString().equals("")){
                Log.d("service id = ",services.getService_id());

                Log.d("service name = ",services.getService_name());
                for(int index=0;index<sendQuoteForPartnersList.size();index++){
                    serviceProviderName.add(sendQuoteForPartnersList.get(index).getPartnerName());
                    serviceProviderId.add(sendQuoteForPartnersList.get(index).getUserID());
                    serviceProviderCostForSelectedService.add(sendQuoteForPartnersList.get(index).getServiceCost());
                }
                String serviceProviderNameList=serviceProviderName.toString();
                String serviceProviderIdList=serviceProviderId.toString();
                String serviceProviderCostForSelectedServiceList=serviceProviderCostForSelectedService.toString();
                Log.d("serviceProviderNameLst=",serviceProviderNameList);
                Log.d("serviceProviderIdList=",serviceProviderIdList);
                Log.d("CostSelectedServiceList",serviceProviderCostForSelectedServiceList);
                Log.d("calendar_selected_date=",calendar_selected_date);
                Log.d("calendar_selected_time=",calendar_selected_time);
                Log.d("location=",HomeScreenActivity.locations.getLocation_id()+":"+HomeScreenActivity.locations.getLocation_name());
                Log.d("quoted amount",sqp_quote_amout.getText().toString());


                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("serviceID",serviceID);
                hashMap.put("serviceName",serviceName);
                hashMap.put("serviceDate",calendar_selected_date);
                hashMap.put("serviceTime",calendar_selected_time);
                //hashMap.put("locationName",HomeScreenActivity.locations.getLocation_name());
                //hashMap.put("locationId",HomeScreenActivity.locations.getLocation_id());
                hashMap.put("locationName",this.locations.getLocation_name());
                hashMap.put("locationId",this.locations.getLocation_id());
                hashMap.put("partnerName",serviceProviderNameList);
                hashMap.put("partnerID",serviceProviderIdList);
                hashMap.put("serviceAmount",serviceProviderCostForSelectedServiceList);
                hashMap.put("userQuoteAmout",userQuoteAmout);
                hashMap.put("customerName",userName);
                hashMap.put("mobileNumber",userMobileNumber);
                DivineServicesWebService divineServicesWebService = new DivineServicesWebService(this, DivineKeyWords.QUOTED_INFO_OF_SERVICE_KEY, hashMap);
                divineServicesWebService.execute();
                Toast.makeText(getApplicationContext(),"quote request sent",Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(getApplicationContext(),"enter quote amout",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"no service providers selected",Toast.LENGTH_SHORT).show();
        }

    }



}
