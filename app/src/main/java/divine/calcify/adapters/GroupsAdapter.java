package divine.calcify.adapters;

/**
 * Created by Calcify3 on 21-06-2016.
 */

import android.app.Activity;
import android.content.Context;
        import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
        import android.widget.TextView;
        import com.squareup.picasso.Picasso;
        import java.util.ArrayList;

import divine.calcify.activities.GroupsListActivity;
import divine.calcify.com.divine.R;
        import divine.calcify.model.DivineGroupServices;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private ArrayList<DivineGroupServices> groupsList;
    private Context context;
    public Activity activity;
    public GroupsAdapter(Activity activity,ArrayList<DivineGroupServices> groupsList) {
        this.groupsList = groupsList;
        this.activity = activity;
    }

    @Override
    public GroupsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.groups_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.group_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupsListActivity groupsListActivity = (GroupsListActivity) activity;
                groupsListActivity.showGroupDetailActivity(groupsList.get(i));
            }
        });
        viewHolder.group_name.setText(groupsList.get(i).getGroupServiceName());
        Picasso.with(activity.getApplicationContext()).load(R.drawable.ganapathi).resize(240, 120).into(viewHolder.group_image);
        //groupsList.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img_android

    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView group_name;
        private ImageView group_image;
        private FrameLayout group_item_layout;
        public ViewHolder(View view) {
            super(view);
            group_name = (TextView)view.findViewById(R.id.group_name);
            group_image = (ImageView) view.findViewById(R.id.group_image);
            group_item_layout=(FrameLayout)view.findViewById(R.id.group_item_layout);
        }
    }

}
