package divine.calcify.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.com.divine.R;
import divine.calcify.model.Locations;

public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mCountries;
    //locations
    private ArrayList<Locations> mLocations;
    private LayoutInflater mLayoutInflater;
    private boolean mIsFilterList;



    public SearchAdapter(Context context, ArrayList<String> countries, boolean isFilterList) {
        this.mContext = context;
        this.mCountries =countries;
        this.mIsFilterList = isFilterList;
    }

    //locations
    public SearchAdapter(Context context,  boolean isFilterList, ArrayList<Locations> locations) {
        this.mContext = context;
        this.mLocations =locations;
        this.mIsFilterList = isFilterList;
    }



    public void updateList(ArrayList<String> filterList, boolean isFilterList) {
        this.mCountries = filterList;
        this.mIsFilterList = isFilterList;
        notifyDataSetChanged ();
    }

    //locatins
    public void updateList( boolean isFilterList, ArrayList<Locations> filterList) {
        this.mLocations = filterList;
        this.mIsFilterList = isFilterList;
        notifyDataSetChanged ();
    }

    //@Override
    //public int getCount() {
    //    return mCountries.size();
    //}

    @Override
    public int getCount() {
        return mLocations.size();
    }


    //@Override
    //public String getItem(int position) {
    //    return mCountries.get(position);
    //}

    @Override
    public Locations getItem(int position) {
        return mLocations.get(position);
    }



    @Override
    public long getItemId(int i) {
        return 0;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if(v==null){

            holder = new ViewHolder();

            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = mLayoutInflater.inflate(R.layout.list_item_search, parent, false);
            holder.txtCountry = (TextView)v.findViewById(R.id.txt_country);
            v.setTag(holder);
        } else{

            holder = (ViewHolder) v.getTag();
        }

        holder.txtCountry.setText(mCountries.get(position));

        Drawable searchDrawable,recentDrawable;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp, null);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp, null);

        } else {
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp);
        }
        if(mIsFilterList) {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        }else {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(recentDrawable, null, null, null);

        }
        return v;
    }
    */
    //location getview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if(v==null){

            holder = new ViewHolder();

            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = mLayoutInflater.inflate(R.layout.item_location_name, parent, false);
            holder.txtCountry = (TextView)v.findViewById(R.id.loc_name);
            v.setTag(holder);
        } else{

            holder = (ViewHolder) v.getTag();
        }

        holder.txtCountry.setText(mLocations.get(position).getLocation_name());

        Drawable searchDrawable,recentDrawable;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp, null);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp, null);

        } else {
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp);
        }
        if(mIsFilterList) {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        }else {
            holder.txtCountry.setCompoundDrawablesWithIntrinsicBounds(recentDrawable, null, null, null);

        }
        return v;
    }

}

//location


class ViewHolder{
     TextView txtCountry;
}





