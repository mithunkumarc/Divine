package divine.calcify.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.com.divine.R;
import divine.calcify.webservices.DivineServicesWebService;

public class RegisterOtpScreenActivity extends AppCompatActivity {
    private EditText ros_mobile_number;
    private EditText ros_otp_number;
    private Button ros_register_button;
    private String userName;
    private String userMobileNumber;
    private String userLocationId;
    private String userLocationName;

    public SharedPreferences userDataSharedPref;
    public static final String UserAccountInfo = "User_Accoutn_Info";
    public static final String UserName = "UserNameKey";
    public static final String UserMobileNumber = "ContactNumberKey";
    public static final String UserLocation = "LocationKey";
    public static final String UserLocationId = "LocationKeyId";
    public static final String FcmToken = "FcmToken";

    String fcm_token;
    //if success , save to shared pref as well
    public static String saveUserDetailsResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_otp_screen);
        SharedPreferences fcmSharedPref = getSharedPreferences(DivineKeyWords.FcmSharedPreferences, Context.MODE_PRIVATE);
        fcm_token = fcmSharedPref.getString(DivineKeyWords.FcmToken, "");
        Intent intent = getIntent();
        userName = intent.getStringExtra("user_name");
        userMobileNumber = intent.getStringExtra("usesr_mobile_number");
        userLocationId = intent.getStringExtra("user_location_id");
        userLocationName = intent.getStringExtra("user_location_name");

        ros_mobile_number = (EditText)findViewById(R.id.ros_mobile_number);
        ros_mobile_number.setText(userMobileNumber);
        ros_otp_number = (EditText)findViewById(R.id.ros_otp_number);
        ros_register_button = (Button)findViewById(R.id.ros_register_button);
        ros_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ros_mobile_number.getText().toString().length()==10){
                    SharedPreferences sharedPreferences = getSharedPreferences(RegisterOtpScreenActivity.UserAccountInfo, Context.MODE_PRIVATE);
                    if (sharedPreferences.contains(RegisterOtpScreenActivity.UserName))
                    {
                        Toast.makeText(getApplicationContext(),"data saved already",Toast.LENGTH_SHORT).show();
                    }else {
                        //save/insert data to db
                        saveUserData();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"please enter mobile number",Toast.LENGTH_SHORT);
                }

            }
        });
    }
    //save userdata in sharedpref and in backend also
    public void saveUserData(){
        //saving in database using werbserivce
        Log.d("CustomerName=",userName);
        Log.d("mob no=",userMobileNumber);

        HashMap<String,String> hashMapUserInfo = new HashMap();
        hashMapUserInfo.put("CustomerName",userName);
        hashMapUserInfo.put("MobileNumber",userMobileNumber);
        hashMapUserInfo.put("FcmTockenID",fcm_token);//fcm tobe integrated
        hashMapUserInfo.put("LocationID",userLocationId);
        hashMapUserInfo.put("Location",userLocationName);


        DivineServicesWebService saveUserDataInfoWS = new DivineServicesWebService(this, DivineKeyWords.SAVE_USER_LOGIN_INFO_KEY,hashMapUserInfo);
        try{
            Double dou = (Double) saveUserDataInfoWS.execute().get();
            //Log.d("location=",RegistrationHomeScreenActivity.locationsArrayList.size()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (saveUserDetailsResult.equalsIgnoreCase("false")){
            userDataSharedPref = getSharedPreferences(UserAccountInfo, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userDataSharedPref.edit();
            editor.putString(UserName, userName);
            editor.putString(UserMobileNumber, userMobileNumber);
            editor.putString(UserLocationId,userLocationId);
            editor.putString(UserLocation,userLocationName);
            //save fcm token
            editor.putString(FcmToken,fcm_token);//to be implemented
            editor.commit();
            //show complete profile screen
            Intent intent = new Intent(RegisterOtpScreenActivity.this,RegisterCompleteScreenActivity.class);
            intent.putExtra("userName",userName);
            intent.putExtra("mobileNumber",userMobileNumber);
            intent.putExtra("locationID",userLocationId);
            intent.putExtra("location",userLocationName);
            intent.putExtra("fcmTockenID",fcm_token);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"user info not saved",Toast.LENGTH_SHORT).show();
        }
    }
}
