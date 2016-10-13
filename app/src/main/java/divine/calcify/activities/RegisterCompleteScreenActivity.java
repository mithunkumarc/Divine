package divine.calcify.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import divine.calcify.com.divine.R;

public class RegisterCompleteScreenActivity extends AppCompatActivity {
    private Button rcs_complete_profile_button;
    private TextView rcs_skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_completed_screen);
        final Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final String userMobileNumber = intent.getStringExtra("mobileNumber");
        final String userLocationId = intent.getStringExtra("locationID");
        final String userLocationName = intent.getStringExtra("location");
        final String fcmTockenID= intent.getStringExtra("fcmTockenID");


        rcs_complete_profile_button = (Button)findViewById(R.id.rcs_complete_profile_button);
        rcs_skip = (TextView)findViewById(R.id.rcs_skip);
        rcs_complete_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterCompleteScreenActivity.this,RegUpdateMyProfileActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("mobileNumber",userMobileNumber);
                intent.putExtra("locationID",userLocationId);
                intent.putExtra("location",userLocationName);
                intent.putExtra("fcmTockenID",fcmTockenID);
                startActivity(intent);

                Toast.makeText(getApplicationContext(),"not completed",Toast.LENGTH_SHORT).show();
            }
        });
        rcs_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterCompleteScreenActivity.this,HomeScreenActivity.class);
                startActivity(intent);
            }
        });

    }
}
