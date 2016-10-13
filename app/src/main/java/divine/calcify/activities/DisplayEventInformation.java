package divine.calcify.activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import divine.calcify.com.divine.R;
import divine.calcify.model.Events;
import divine.calcify.model.Partner;

public class DisplayEventInformation extends AppCompatActivity {
    TextView dei_event_info;

    CollapsingToolbarLayout dei_collapsing_toolbar;
    Events eventInfo;
    TextView dei_event_name;
    Button dei_select_event_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_information);

        dei_select_event_button= (Button)findViewById(R.id.dei_select_event_button);
        dei_event_info = (TextView) findViewById(R.id.dei_event_info);
        dei_event_name = (TextView)findViewById(R.id.dei_event_name);
        eventInfo = (Events) getIntent().getSerializableExtra("event_object");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        //event_info = (Events) getIntent().getSerializableExtra("event_object");
        dei_collapsing_toolbar =
                (CollapsingToolbarLayout) findViewById(R.id.dei_collapsing_toolbar);
        dei_collapsing_toolbar.setTitle(eventInfo.getTempleName()+", "+eventInfo.getLocation());
        dei_event_name.setText(eventInfo.getEventName());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.dei_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        dei_event_info.setText(eventInfo.getEventDescription());
        dei_select_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayEventInformation.this,AddMembersToEventActivity.class);
                intent.putExtra("event_object",eventInfo);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
