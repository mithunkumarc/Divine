package divine.calcify.interfaces;

import java.util.ArrayList;

import divine.calcify.model.Events;
import divine.calcify.model.Services;
import divine.calcify.model.Temple;

/**
 * Created by Calcify3 on 13-08-2016.
 */
public interface HomeScreenCallBack {
    public void callGroupActivity(String groupName);
    //to show calendar when service is clicked
    public void showCalenderOnServiceClickCallBack(Services services);
    public void showSingleTempleEventListWithDate(Temple temple);
    public void showAllTempleEventListWithDate(ArrayList<Temple> temples);

}
