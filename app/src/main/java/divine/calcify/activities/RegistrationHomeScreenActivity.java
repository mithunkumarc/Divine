package divine.calcify.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.Utility.Utilities;
import divine.calcify.adapters.SharedPreference;
import divine.calcify.com.divine.R;
import divine.calcify.model.Locations;
import divine.calcify.ui.AutoCompleteLocationAdapter;
import divine.calcify.webservices.DivineServicesWebService;
//NOTE: GET THE TOKEN AFTER REGS IN FCM. TOKEN NEEDED
public class RegistrationHomeScreenActivity extends AppCompatActivity {
    private EditText reg_full_name;
    private AutoCompleteTextView reg_home_location;
    private EditText reg_mobile_number;
    private Button reg_button;
    public static ArrayList<Locations> locationsArrayList = new ArrayList<>();
    private AutoCompleteLocationAdapter autoCompleteLocationAdapter;
    private String selectedLocationId;
    private String selectedLocationName;

    public static String fcm_token;

    public static String userUniqueNumberResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_home_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(RegisterOtpScreenActivity.UserAccountInfo, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(RegisterOtpScreenActivity.UserName))
        {
            //if user already signed in, control will go to homescreen activity
            Intent intent = new Intent(RegistrationHomeScreenActivity.this,HomeScreenActivity.class);
            startActivity(intent);
            //delete shared pref for testing
            //use below code to delete shared preference and also avoid going to next activity while deleting
            /*sharedPreferences.edit().remove(RegisterOtpScreenActivity.UserName).commit();
            sharedPreferences.edit().remove(RegisterOtpScreenActivity.UserMobileNumber).commit();
            sharedPreferences.edit().remove(RegisterOtpScreenActivity.UserLocationId).commit();
            sharedPreferences.edit().remove(RegisterOtpScreenActivity.UserLocation).commit();
            sharedPreferences.edit().remove(RegisterOtpScreenActivity.FcmToken).commit();
            sharedPreferences.edit().clear().commit();*/
            // /delete shared pref for testing
        }
        else
        {
            //first time installing
            loadLocations();
            reg_full_name = (EditText)findViewById(R.id.rhs_full_name);
            reg_home_location = (AutoCompleteTextView) findViewById(R.id.rhs_home_location);
            //suggestion start after entering first letter
            reg_home_location.setThreshold(1);
            reg_mobile_number = (EditText)findViewById(R.id.rhs_mobile_number);
            reg_button = (Button)findViewById(R.id.rhs_submit_button);
            reg_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //validation needed
                    if(reg_full_name.getText().length()!=0){
                        if(reg_home_location.getText().toString().trim().length()!=0){
                            if(Utilities.validateHomeLocation(reg_home_location.getText().toString(),locationsArrayList)){
                                if(reg_mobile_number.getText().length()==10){
                                    //check whether the mobile number is unique(not present in db)
                                    checkUniqueMobileNumberWebservice(RegistrationHomeScreenActivity.this);
                                }else {
                                    Toast.makeText(getApplicationContext(),"enter correct mobile number",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                reg_home_location.setText("");
                                Toast.makeText(getApplicationContext(),"location not available",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(),"enter location",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"enter your name",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            autoCompleteLocationAdapter=new AutoCompleteLocationAdapter(this,R.layout.reg_location_row_item,locationsArrayList);

            reg_home_location.setAdapter(autoCompleteLocationAdapter);
            reg_home_location.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Locations location=(Locations)view.getTag();
                    selectedLocationId = location.getLocation_id();
                    selectedLocationName = location.getLocation_name();
                    Toast.makeText(RegistrationHomeScreenActivity.this,"Clicked " + location.getLocation_name(),Toast.LENGTH_LONG).show();
                }
            });

        }





    }

    //call webservice to get all location to allow user to select
    public void loadLocations(){
        DivineServicesWebService locationWebservice = new DivineServicesWebService(this, DivineKeyWords.LOCATIONS_KEY);
        try{
            Double dou = (Double) locationWebservice.execute().get();
            //Log.d("location=",RegistrationHomeScreenActivity.locationsArrayList.size()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //check whether the mobile number is unique or not
    public void checkUniqueMobileNumberWebservice(Activity activity){
        String userNumber = reg_mobile_number.getText().toString();
        DivineServicesWebService checkMobileNumberUniqueWS = new DivineServicesWebService(this, DivineKeyWords.USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY,userNumber);
        try{
            Double dou = (Double) checkMobileNumberUniqueWS.execute().get();
            if (userUniqueNumberResult.equalsIgnoreCase("false")){
                Toast.makeText(getApplicationContext(),"mobile number already exists",Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(RegistrationHomeScreenActivity.this,RegisterOtpScreenActivity.class);
                intent.putExtra("user_name",reg_full_name.getText().toString().trim());
                intent.putExtra("user_location_id",selectedLocationId);
                intent.putExtra("user_location_name",selectedLocationName);
                intent.putExtra("usesr_mobile_number",reg_mobile_number.getText().toString().trim());
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
