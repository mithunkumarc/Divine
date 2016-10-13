package divine.calcify.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import divine.calcify.com.divine.R;
import divine.calcify.model.Events;

public class AddMembersToEventActivity extends AppCompatActivity {
    public Events eventsInfo;
    TextView amt_event_name;
    TextView amt_temple_name;
    TextView amt_event_date_time;
    Button amt_add_member_done_button;
    String userName;
    TextView amt_user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members_to_event);
        SharedPreferences userSharedPref = getSharedPreferences(RegisterOtpScreenActivity.UserAccountInfo, Context.MODE_PRIVATE);
        userName = userSharedPref.getString(RegisterOtpScreenActivity.UserName, "");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.amt_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        eventsInfo = (Events) getIntent().getSerializableExtra("event_object");
        amt_user_name = (TextView)findViewById(R.id.amt_user_name);
        amt_user_name.setText(userName);
        amt_event_name = (TextView)findViewById(R.id.amt_event_name);
        amt_event_name.setText(eventsInfo.getEventName());
        amt_temple_name = (TextView)findViewById(R.id.amt_temple_name);
        amt_temple_name.setText(eventsInfo.getTempleName());
        amt_event_date_time= (TextView)findViewById(R.id.amt_event_date_time);
        amt_event_date_time.setText(eventsInfo.getEventDate()+", "+eventsInfo.getEventTimings());
        amt_add_member_done_button= (Button)findViewById(R.id.amt_add_member_done_button);
        amt_add_member_done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMembersToEventActivity.this,PayForEventActivity.class);
                intent.putExtra("event_object",eventsInfo);
                startActivity(intent);
            }
        });
    }
}
