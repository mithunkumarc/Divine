package divine.calcify.activities;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import divine.calcify.com.divine.R;
import divine.calcify.model.Events;

public class PayForEventActivity extends AppCompatActivity {
    public Events eventsInfo;
    public TextView pfe_amount_text;
    public TextView pfe_donate_oath_text;
    public TextView pfe_temple_name;
    public TextView pfe_location_name;
    public TextView pfe_donate_quote;
    //public TextView pfe_amount_for_event;
    public Button pfe_pay_now_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_for_event);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        eventsInfo = (Events) getIntent().getSerializableExtra("event_object");
        Toolbar toolbar = (Toolbar)findViewById(R.id.pfe_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        String rupee_symbol = getResources().getString(R.string.rupee_symbol);
        pfe_amount_text = (TextView)findViewById(R.id.pfe_amount_text);
        pfe_amount_text.setText("Amount for "+rupee_symbol+" "+eventsInfo.getEventCost());
        pfe_donate_oath_text = (TextView)findViewById(R.id.pfe_donate_oath_text);
        pfe_temple_name = (TextView)findViewById(R.id.pfe_temple_name);
        pfe_temple_name.setText(eventsInfo.getTempleName());
        pfe_location_name = (TextView)findViewById(R.id.pfe_location_name);
        pfe_location_name.setText(eventsInfo.getLocation());
        pfe_donate_quote = (TextView)findViewById(R.id.pfe_donate_quote);
        String donate_quote_string = getResources().getString(R.string.donate_quote);
        String donate_quote_sub_string = getResources().getString(R.string.donate_subquote);
        pfe_donate_quote.setText(donate_quote_string+" "+rupee_symbol+" "+donate_quote_sub_string);
        //pfe_amount_for_event = (TextView) findViewById(R.id.pfe_amount_for_event);
        //pfe_amount_for_event.setText(eventsInfo.getEventCost());
        pfe_pay_now_button= (Button)findViewById(R.id.pfe_pay_now_button);
        pfe_pay_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"to be implemented",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
