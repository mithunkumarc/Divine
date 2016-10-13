package divine.calcify.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.Utility.Utilities;
import divine.calcify.com.divine.R;
import divine.calcify.model.Locations;
import divine.calcify.ui.AutoCompleteLocationAdapter;
import divine.calcify.webservices.DivineServicesWebService;

public class RegUpdateMyProfileActivity extends AppCompatActivity {
    String ageGroup[] = { "Select Age Group","10-30", "30-50", "50-70","70-100"};
    ArrayAdapter<String> ageGroupAdapter;
    Spinner myprofile_ageGroup;
    TextView myprofile_emailId;
    TextInputLayout mp_email_wrapper;
    EditText myprofile_fullName;
    EditText myprofile_Mobile_number;
    Button submit_myprofile_button;
    ImageView myprofile_male_checkbox,myprofile_female_checkbox;
    private AutoCompleteLocationAdapter autoCompleteLocationAdapter;
    AutoCompleteTextView myprofile_location;
    String userName;

    //update profile webservice parameters
    String userNameProfile;
    String emailProfile;
    String genderProfile;
    String selectedLocationId;
    String selectedLocationName;
    String userMobileNumber;
    String fcmTockenID;
    String selectedAgeGroup="Select Age Group";
    //result, whether profile updated or not
    public static String updateProfileResult;

    boolean male_flag = false,female_flag= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_update_myprofile_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }


        Toolbar toolbar = (Toolbar)findViewById(R.id.myp_toolbar);
        toolbar.setTitle("My Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //this works only when the flow coming from installation, check intent when this activity
        //is opened from other source/screens
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userMobileNumber = intent.getStringExtra("mobileNumber");
        selectedLocationId = intent.getStringExtra("locationID");
        selectedLocationName = intent.getStringExtra("location");
        fcmTockenID= intent.getStringExtra("fcmTockenID");

        myprofile_location = (AutoCompleteTextView)findViewById(R.id.myprofile_location);
        myprofile_location.setThreshold(1);
        myprofile_ageGroup = (Spinner)findViewById(R.id.myprofile_ageGroup);
        myprofile_emailId = (TextView)findViewById(R.id.myprofile_emailId);
        mp_email_wrapper = (TextInputLayout)findViewById(R.id.mp_email_wrapper);
        myprofile_male_checkbox = (ImageView)findViewById(R.id.myprofile_male_checkbox);
        myprofile_female_checkbox = (ImageView)findViewById(R.id.myprofile_female_checkbox);

        mp_email_wrapper.setErrorEnabled(true);
        mp_email_wrapper.setError("enter email");
        myprofile_fullName = (EditText)findViewById(R.id.myprofile_fullName);
        myprofile_fullName.setText(userName);
        myprofile_Mobile_number = (EditText)findViewById(R.id.myprofile_Mobile_number);
        myprofile_Mobile_number.setText(userMobileNumber);
        submit_myprofile_button = (Button)findViewById(R.id.submit_myprofile_button);
        submit_myprofile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myprofile_emailId.getText()!=null){
                    //boolean resutl=android.util.Patterns.EMAIL_ADDRESS.matcher(myprofile_emailId.getText()).matches();
                    updateProfileValidation(RegUpdateMyProfileActivity.this);
                }
            }
        });

        ageGroupAdapter = new ArrayAdapter<String>(RegUpdateMyProfileActivity.this, android.R.layout.simple_dropdown_item_1line, ageGroup);

        myprofile_ageGroup.setAdapter(ageGroupAdapter);
        myprofile_ageGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAgeGroup = ageGroup[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        myprofile_male_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male_flag){
                    myprofile_male_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_disabled));
                    male_flag = false;
                }else {
                    if(female_flag){
                        myprofile_male_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_enabled));
                        male_flag=true;
                        myprofile_female_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_disabled));
                        female_flag=false;
                    }else{
                        myprofile_male_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_enabled));
                        male_flag=true;
                    }
                }
            }
        });
        //female select
        myprofile_female_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (female_flag){
                    myprofile_female_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_disabled));
                    female_flag=false;
                }else {
                    if(male_flag){
                        myprofile_female_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_enabled));
                        female_flag=true;
                        myprofile_male_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_disabled));
                        male_flag = false;
                    }else {
                        myprofile_female_checkbox.setImageDrawable(getResources().getDrawable(R.drawable.checkbox_enabled));
                        female_flag=true;
                    }
                }

            }
        });

        autoCompleteLocationAdapter=new AutoCompleteLocationAdapter(this,R.layout.reg_location_row_item,RegistrationHomeScreenActivity.locationsArrayList);

        myprofile_location.setText(selectedLocationName);
        myprofile_location.setAdapter(autoCompleteLocationAdapter);
        myprofile_location.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Locations location=(Locations)view.getTag();
                selectedLocationId = location.getLocation_id();
                selectedLocationName = location.getLocation_name();
            }
        });
    }

    //validation
    public void updateProfileValidation(Activity activity){
        //below is email validation
        //android.util.Patterns.EMAIL_ADDRESS.matcher(myprofile_emailId.getText()).matches();
        userNameProfile = myprofile_fullName.getText().toString().trim();
        emailProfile = myprofile_emailId.getText().toString().trim();
        if(userNameProfile.length()>0){
            if (emailProfile.length()>0){
                boolean emailFlag = android.util.Patterns.EMAIL_ADDRESS.matcher(emailProfile).matches();
                if (emailFlag){
                    if (male_flag||female_flag){
                        if (selectedLocationName!=null){
                            if(!selectedAgeGroup.equalsIgnoreCase("Select Age Group")){
                                if(myprofile_location.getText().length()>0){
                                    if (Utilities.validateHomeLocation(myprofile_location.getText().toString(),RegistrationHomeScreenActivity.locationsArrayList)){
                                        if (male_flag){
                                            genderProfile = "male";
                                        }if(female_flag){
                                            genderProfile = "female";
                                        }
                                        updateProfileWebservice();
                                    }else{
                                        myprofile_location.setText("");
                                        Toast.makeText(getApplicationContext(),"location not availbe",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(),"enter location",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"select age group",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"select location",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"select gender",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"incorrect emaid id",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"enter email id",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"enter full name",Toast.LENGTH_SHORT).show();
        }
    }
    //webservice call
    public void updateProfileWebservice(){
        HashMap<String,String> hashMapUpdateProfile = new HashMap<>();
        hashMapUpdateProfile.put("userName",userNameProfile);
        hashMapUpdateProfile.put("mobileNumber",userMobileNumber);
        hashMapUpdateProfile.put("location",selectedLocationName);
        hashMapUpdateProfile.put("locationID",selectedLocationId);
        hashMapUpdateProfile.put("email",emailProfile);
        hashMapUpdateProfile.put("gender",genderProfile);
        SharedPreferences fcmSharedPref = getSharedPreferences(DivineKeyWords.FcmSharedPreferences, Context.MODE_PRIVATE);
        String fcm_token = fcmSharedPref.getString(DivineKeyWords.FcmToken, "");
        hashMapUpdateProfile.put("fcmTockenID",fcm_token);
        hashMapUpdateProfile.put("ageGroup",selectedAgeGroup);
        hashMapUpdateProfile.put("gotra","gotraa");
        hashMapUpdateProfile.put("nakshyatra","nakshatra");
        hashMapUpdateProfile.put("rashi","rashii");
        DivineServicesWebService updateProfileWS = new DivineServicesWebService(this, DivineKeyWords.UPDATE_PROFILE_OF_USER_KEY,hashMapUpdateProfile);
        Double result=1.00;
        try {
            result = (Double)updateProfileWS.execute().get();

        }catch (Exception e){
            e.getStackTrace();
        }
        if (updateProfileResult.equalsIgnoreCase("false")){
            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }
}
