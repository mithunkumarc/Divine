package divine.calcify.Utility;

import android.content.Intent;
import android.util.Log;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import divine.calcify.activities.CartActivity;
import divine.calcify.activities.DisplayEventInformation;
import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.Events;
import divine.calcify.model.Locations;
import divine.calcify.model.Services;
import divine.calcify.webservices.DivineServicesWebService;

/**
 * Created by Calcify3 on 24-08-2016.
 */
public class Utilities {
    //get the name of the month
    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
    //get the time format
    public static String getTimeFormat(int hourOfDay){
        String format;
        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        }
        else if (hourOfDay == 12) {
            format = "PM";
        }
        else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        }
        else {
            format = "AM";
        }
        return format;
    }
    //check whether the service providers are available for services, if yes send true else false
    public static boolean getServiceProvidersAvailableOrNot(ArrayList<Services> servicesArrayList){
        boolean flag= false;
                if (servicesArrayList.size()>0){
                    for (int inner_loop_index =0 ; inner_loop_index<servicesArrayList.size(); inner_loop_index++){
                        if(!servicesArrayList.get(inner_loop_index).getService_count().equals("0")){
                            flag = true;// means some service has service provides. so no need to remove view all button
                        }
                    }
                }else{
                    return flag;
                }


        return flag;
    }
    //validate location
    //validate location
    public static boolean validateHomeLocation(String homeLocation,ArrayList<Locations> locationsArrayList){
        boolean locationFlag = false;
        for(int indexHomeLocation =0; indexHomeLocation<locationsArrayList.size();indexHomeLocation++){
            Log.d(homeLocation,locationsArrayList.get(indexHomeLocation).getLocation_name());
            if (homeLocation.equalsIgnoreCase(locationsArrayList.get(indexHomeLocation).getLocation_name())){
                locationFlag = true;
            }
        }
        return locationFlag;
    }

    //getTotalAmoutOfServiceCartList
    /*public static Double getTotalAmoutOfServiceCartList(){
        Double total = 0.0;
        for (int index = 0; index< CartActivity.serviceCartArrayList.size();index++){
            total = total + Double.parseDouble(CartActivity.serviceCartArrayList.get(index).getCost());
        }
        return total;
    }*/

}
