package divine.calcify.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.com.divine.R;
import divine.calcify.model.Temple;

/**
 * Created by Calcify3 on 29-09-2016.
 */
public class DivineTempleEventListAdapter extends RecyclerView.Adapter<DivineTempleEventListAdapter.DivineTempleEcventListViewHolder> {
    private Activity activity;
    private ArrayList<Temple> templeArrayList;
    int position;
    public DivineTempleEventListAdapter(Activity activity,ArrayList<Temple> templeArrayList,int position){
        this.activity = activity;
        this.templeArrayList = templeArrayList;
        this.position = position;
    }

    @Override
    public DivineTempleEcventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hs_temple_list_horizontal_cardview, parent, false);
        DivineTempleEcventListViewHolder pvh = new DivineTempleEcventListViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(DivineTempleEcventListViewHolder holder, final int position) {
        holder.hs_temple_name_in_event_list.setText(templeArrayList.get(position).getTempleName().substring(0,9)+"..("+templeArrayList.get(position).getEventsArrayList().size()+")");
        holder.hs_temple_list_item_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeScreenActivity) activity).showSingleTempleEventListWithDate(templeArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (templeArrayList.size()>=2){
            return 2;//inorder to accommodate view all temple buttn
        }
        if (templeArrayList.size()<2){
            return templeArrayList.size();
        }
        return 2;
    }

    public class DivineTempleEcventListViewHolder extends RecyclerView.ViewHolder{
        public CardView hs_temple_list_item_card_view;
        public TextView hs_temple_name_in_event_list;

        public DivineTempleEcventListViewHolder(View view){
            super(view);
            hs_temple_list_item_card_view= (CardView)view.findViewById(R.id.hs_temple_list_item_card_view);
            hs_temple_name_in_event_list=(TextView)view.findViewById(R.id.hs_temple_name_in_event_list);
        }

    }

}
