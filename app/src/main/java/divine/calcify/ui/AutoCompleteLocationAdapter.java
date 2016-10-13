package divine.calcify.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.com.divine.R;
import divine.calcify.model.Locations;

/**
 * Created by Calcify3 on 22-09-2016.
 */

public class AutoCompleteLocationAdapter extends ArrayAdapter<Locations> {

    private ArrayList<Locations> list;
    Context context;
    LayoutInflater inflater;
    int resource;

    public AutoCompleteLocationAdapter(Context context, int resource, ArrayList<Locations> list) {
        super(context, resource);
        this.context = context;
        this.resource = resource;

        this.list = list;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(resource, null);
        }

        Locations location=getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.rhs_location_name);
        //TextView textViewColor = (TextView) view.findViewById(R.id.txtColorCode);

        textView.setText(location.getLocation_name());
        //textViewColor.setBackgroundColor(location.getLocationId());

        view.setTag(location);
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Locations) (resultValue)).getLocation_name();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                ArrayList<Locations> suggestions = new ArrayList<>();

                for (Locations location : list) {
                    Log.d("location =",location.getLocation_name()+"");
                    if (location.getLocation_name().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(location);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Locations>) results.values);
            }
            notifyDataSetChanged();
        }
    };
}
