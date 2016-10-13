package divine.calcify.Utility;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.model.Partner;
import divine.calcify.model.PartnerProfileInfo;
import divine.calcify.model.Services;
import divine.calcify.webservices.DivineServicesWebService;

/**
 * Created by Calcify3 on 31-08-2016.
 */
public class WebServiceHelper {
    //result arraylist loaded in divinewebservice class
    public static void getPartnerList(Activity activity, Services services)throws Exception{
        GroupDetailFragment.partnerArrayList.clear();
        DivineServicesWebService listOfPartnersOfServiceWebservice = new DivineServicesWebService(activity, DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY, services.getService_id(), HomeScreenActivity.locations.getLocation_id());
        Double result =(Double) listOfPartnersOfServiceWebservice.execute().get();
        Log.d("result -",result+"");
    }
}
