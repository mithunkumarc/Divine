package divine.calcify.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.Utility.Utilities;
import divine.calcify.adapters.TimeGridAdapter;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.model.Services;
import divine.calcify.ui.CalendarView;

import divine.calcify.com.divine.R;
import divine.calcify.webservices.DivineServicesWebService;

public class CalendarScreenActivity extends AppCompatActivity {
    public divine.calcify.ui.CalendarView calendarView;
    //public TimePicker time_picker;
    private static final String DATE_FORMAT_main = "dd-MMM-yyyy";
    String format = "";//am or pm
    public static String selectedServicePartnerListDate;
    public Services services;
    public static ArrayList<String> hoursList = new ArrayList<>();
    //public Toolbar camera_screen_toolbar;
    public TextView calendar_AM_text;
    public TextView calendar_PM_text;
    public boolean flagAM = false;
    public boolean flagPM = false;

    //selected time
    public static String calendarSelectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_screen_layout);
        //change status bar to primary dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.calendar_screen_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        calendarView = ((divine.calcify.ui.CalendarView)findViewById(R.id.calendar_view));
        calendarView.updateCalendar(events);
        calendar_AM_text = (TextView)findViewById(R.id.calendar_AM_text);
        calendar_PM_text = (TextView)findViewById(R.id.calendar_PM_text);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.calendar_recyclerview_time_grid);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new TimeGridAdapter(this,hoursList);
        recyclerView.setAdapter(adapter);

        //time_picker = (TimePicker)findViewById(R.id.time_picker);
        //time_picker.setIs24HourView(false);

        //Toast.makeText(getApplicationContext(),time_picker.getCurrentHour()+""+time_picker.getCurrentMinute(),Toast.LENGTH_SHORT).show();
        /*time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minutes) {
                format = Utilities.getTimeFormat(hourOfDay);
                //Toast.makeText(getApplicationContext(),hourOfDay+""+minutes+""+format,Toast.LENGTH_SHORT).show();
            }
        });*/

        //service_object
        services = (Services) getIntent().getSerializableExtra("service_object");
        /*Log.d("partner list size",GroupDetailFragment.partnerArrayList.size()+"");
        Log.d("services.getService=",services.getService_id());
        Log.d("location=",HomeScreenActivity.locations.getLocation_id());
        */
        /*GroupDetailFragment.partnerArrayList.clear();
        DivineServicesWebService listOfPartnersOfServiceWebservice = new DivineServicesWebService(this, DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY, services.getService_id(), HomeScreenActivity.locations.getLocation_id());
        listOfPartnersOfServiceWebservice.execute();
*/

        // assign event handler
        calendarView.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(CalendarScreenActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        //select am
        calendar_AM_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagAM){
                    calendar_AM_text.setBackgroundColor(getResources().getColor(R.color.white));
                    calendar_AM_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    flagAM = false;
                }else {
                    if(flagPM){
                        calendar_AM_text.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        calendar_AM_text.setTextColor(getResources().getColor(R.color.white));
                        flagAM=true;
                        calendar_PM_text.setBackgroundColor(getResources().getColor(R.color.white));
                        calendar_PM_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        flagPM=false;
                    }else{
                        calendar_AM_text.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        calendar_AM_text.setTextColor(getResources().getColor(R.color.white));
                        flagAM=true;
                    }
                }
            }
        });
        //select pm
        calendar_PM_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagPM){
                    calendar_PM_text.setBackgroundColor(getResources().getColor(R.color.white));
                    calendar_PM_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    flagPM=false;
                }else {
                    if(flagAM){
                        calendar_PM_text.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        calendar_PM_text.setTextColor(getResources().getColor(R.color.white));
                        flagPM=true;
                        calendar_AM_text.setBackgroundColor(getResources().getColor(R.color.white));
                        calendar_AM_text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        flagAM = false;
                    }else {
                        calendar_PM_text.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        calendar_PM_text.setTextColor(getResources().getColor(R.color.white));
                        flagPM=true;
                    }
                }

            }
        });
    }
    //if error TintContextWrapper, comes again, try setonclick listener
    public void showPartnersList(View view){
        if(CalendarView.selectedDate!=null && !CalendarView.selectedDate.equals("")){
            if(calendarSelectedTime!=null && !calendarSelectedTime.equals("")){
                if(flagAM || flagPM){
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_main);
                    String finalDate = sdf.format(CalendarView.selectedDate.getTime());
                    SimpleDateFormat simpleDateformatForDay = new SimpleDateFormat("EEEE"); // the day of the week abbreviated
                    String selectedDay  = simpleDateformatForDay.format(CalendarView.selectedDate);
                    String finalTime="";
                    if(flagAM){
                        finalTime = calendarSelectedTime+" "+"AM";
                    }
                    if (flagPM){
                        finalTime = calendarSelectedTime+" "+"PM";
                    }
                    Intent intent = new Intent(CalendarScreenActivity.this,ServicePartnerListActivity.class);
                    intent.putExtra("service_object",services);
                    intent.putExtra("calendar_selected_date",finalDate);
                    intent.putExtra("calendar_selected_time",finalTime);
                    intent.putExtra("calendar_selected_day",selectedDay);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"select AM or PM",Toast.LENGTH_SHORT).show();
                }
                //if(flagAM || flagPM){
                    //Toast.makeText(getApplicationContext(),"show partnerlist",Toast.LENGTH_SHORT).show();
                    //format = Utilities.getTimeFormat(time_picker.getCurrentHour());//am or pm
                    //SimpleDateFormat sdf1 = new SimpleDateFormat();
                    //SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_main);
                    //String finalDate = sdf.format(CalendarView.selectedDate.getTime());//+""+time_picker.getCurrentHour()+":"+time_picker.getCurrentMinute();
                    //selectedServicePartnerListDate = finalDate+""+format;
                    //Toast.makeText(getApplicationContext(),finalDate+""+format,Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(CalendarScreenActivity.this,ServicePartnerListActivity.class);
                    //intent.putExtra("service_object",services);
                    //startActivity(intent);
                /*}else {
                    Toast.makeText(getApplicationContext(),"select AM or PM",Toast.LENGTH_SHORT);
                }*/
            }else {
                Toast.makeText(getApplicationContext(),"please select time",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"please select date", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //GroupDetailFragment.partnerArrayList.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }


    //static block used to add time hours to list
                    static {
        hoursList.add("01");
        hoursList.add("02");
        hoursList.add("03");
        hoursList.add("04");
        hoursList.add("05");
        hoursList.add("06");
        hoursList.add("07");
        hoursList.add("08");
        hoursList.add("09");
        hoursList.add("10");
        hoursList.add("11");
        hoursList.add("12");
    }


}
