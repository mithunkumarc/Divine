package divine.calcify.adapters;

/**
 * Created by Calcify3 on 27-08-2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import divine.calcify.activities.ServicePartnerListActivity;
import divine.calcify.com.divine.R;
import divine.calcify.model.Partner;


public class SendQuotePartnerAdapter extends RecyclerView.Adapter<SendQuotePartnerAdapter.ViewHolder> {
    private ArrayList<Partner> selectedServicePartnerList;// = new ArrayList<>();
    private Context context;

    public SendQuotePartnerAdapter(Context context) {
        this.selectedServicePartnerList = ServicePartnerListActivity.selectedPartnerList;
        this.context = context;
    }

    @Override
    public SendQuotePartnerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sqp_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SendQuotePartnerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.sqp_partner_name.setText(selectedServicePartnerList.get(i).getPartnerName());
        viewHolder.sqp_cancel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedServicePartnerList.remove(i);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return selectedServicePartnerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private TextView sqp_partner_name;
        private ImageView sqp_cancel_image;

        public ViewHolder(View view) {
            super(view);

            sqp_partner_name = (TextView) view.findViewById(R.id.sqp_partner_name);
            sqp_cancel_image = (ImageView)view.findViewById(R.id.sqp_cancel_image);
        }
    }

}
