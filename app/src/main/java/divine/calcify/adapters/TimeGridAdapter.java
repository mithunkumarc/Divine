package divine.calcify.adapters;

/**
 * Created by Calcify3 on 01-09-2016.
 * this adapter is created for time grid used in calendar
 */
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.activities.CalendarScreenActivity;
import divine.calcify.com.divine.R;


public class TimeGridAdapter extends RecyclerView.Adapter<TimeGridAdapter.ViewHolder> {
    private ArrayList<String> hoursList;
    private Activity activity;
    //previous selected hour and its view
    public int previousSelectedHour;
    public ViewHolder previousViewHolder;


    public TimeGridAdapter(Activity activity,ArrayList<String> hoursList) {
        this.hoursList = hoursList;
        this.activity = activity;
    }

    @Override
    public TimeGridAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_time_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimeGridAdapter.ViewHolder viewHolder,final int i) {

        viewHolder.calendar_time_grid_numbers.setText(hoursList.get(i));
        viewHolder.calendar_time_grid_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saving only position of hourlist , not hour itself
                viewHolder.calendar_time_grid_numbers.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
                viewHolder.calendar_time_grid_numbers.setTextColor(activity.getResources().getColor(R.color.white));
                CalendarScreenActivity.calendarSelectedTime = CalendarScreenActivity.hoursList.get(i);
                if (previousViewHolder!=null){
                    if (previousSelectedHour!=i){
                        previousViewHolder.calendar_time_grid_numbers.setBackgroundColor(activity.getResources().getColor(R.color.white));
                        previousViewHolder.calendar_time_grid_numbers.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                    }
                }
                previousSelectedHour = i;
                previousViewHolder = viewHolder;
            }
        });
    }

    @Override
    public int getItemCount() {
        return hoursList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView calendar_time_grid_numbers;
        public ViewHolder(View view) {
            super(view);

            calendar_time_grid_numbers = (TextView)view.findViewById(R.id.calendar_time_grid_numbers);
        }
    }

}