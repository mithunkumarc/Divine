package divine.calcify.model;

import java.util.ArrayList;

/**
 * Created by Calcify3 on 14-10-2016.
 */
public class ServiceCartListByDates {
    private String Date;
    private ArrayList<Services> servicesArrayList = new ArrayList<>();

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public ArrayList<Services> getServicesArrayList() {
        return servicesArrayList;
    }

    public void setServicesArrayList(ArrayList<Services> servicesArrayList) {
        this.servicesArrayList = servicesArrayList;
    }
}
