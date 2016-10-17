package divine.calcify.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Calcify3 on 24-08-2016.
 * person who do pooja either in home/temple
 */
public class Partner implements Serializable{
    private String partnerName;
    private String serviceID;
    private String serviceName;
    private String serviceCost;
    private String userID;
    private String location;
    private String locationId;
    private boolean isSelected;//using for both selecting partner for service and art
    private int serviceCharge;
    //required by cart
    private String enquiryId;

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(int serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public static class PartnerComparator implements Comparator<Partner> {
        @Override
        public int compare(Partner obj1, Partner obj2) {
            //return obj1.getPartnerName().compareToIgnoreCase(obj2.getPartnerName());
            String firstObjectPrice = obj1.getServiceCost().replace("\"", "").trim();
            String secondObjectPrice = obj2.getServiceCost().replace("\"","").trim();
            obj1.setServiceCharge(Integer.parseInt(firstObjectPrice.replaceAll("[\\D]", "")));
            obj2.setServiceCharge(Integer.parseInt(secondObjectPrice.replaceAll("[\\D]", "")));
            return obj1.getServiceCharge() - obj2.getServiceCharge();
        }
    }
}
