package divine.calcify.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.com.divine.R;
import divine.calcify.webservices.DivineServicesWebService;

public class LocationActivity extends Activity {
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<String> locations = new ArrayList<String>();
    String selectedLocation;
    String selectedLocation_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_location);
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        String[] areas = new String[HomeScreenActivity.locationsArrayList.size()];
        if(HomeScreenActivity.locationsArrayList.size()>0){
            for(int index =0; index <HomeScreenActivity.locationsArrayList.size(); index++){
                areas[index] = HomeScreenActivity.locationsArrayList.get(index).getLocation_name();

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,areas);
        autoCompleteTextView.setAdapter(adapter);
        //Log.d("index ----",this.getCallingActivity().toString());
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                selectedLocation = (String)parent.getItemAtPosition(position);
                selectedLocation_id =HomeScreenActivity.locationsArrayList.get(position).getLocation_id();
                Toast.makeText(view.getContext(),rowId+"",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void backToHomeScreen(View view){
        if (selectedLocation != null) {
            Intent intent = new Intent(LocationActivity.this,HomeScreenActivity.class);
            intent.putExtra("location_selected",selectedLocation);
            intent.putExtra("location_selected_id",selectedLocation_id);
            startActivity(intent);
        }else{
            Intent intent = new Intent(LocationActivity.this,HomeScreenActivity.class);
            intent.putExtra("location_selected",selectedLocation);
            intent.putExtra("location_selected_id",selectedLocation_id);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(getApplicationContext(),"disabled",Toast.LENGTH_SHORT).show();
    }
}
