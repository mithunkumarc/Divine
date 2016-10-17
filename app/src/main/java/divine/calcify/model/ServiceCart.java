package divine.calcify.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Calcify3 on 06-10-2016.
 */
public class ServiceCart {
    private String cartId;
    private String serviceId;
    private String serviceName;
    private String serviceDate;
    private String serviceTime;
    private String locationName;
    private String locationId;
    private String partnerName;
    private String partnerID;
    private String serviceAmount;
    //private String userQuoteAmout;
    private String userName;
    private String mobileNumber;
    private String time;
    private String date;
    private String cost;
    private String status;
    private ArrayList<ServiceCartListByDates> serviceCartListByDates = new ArrayList<>();

    private HashSet<String> uniqueDateList = new HashSet<>();


    public HashSet<String> getUniqueDateList() {
        return uniqueDateList;
    }

    public void setUniqueDateList(HashSet<String> uniqueDateList) {
        this.uniqueDateList = uniqueDateList;
    }

    public ArrayList<ServiceCartListByDates> getServiceCartListByDates() {
        return serviceCartListByDates;
    }

    public void setServiceCartListByDates(ArrayList<ServiceCartListByDates> serviceCartListByDates) {
        this.serviceCartListByDates = serviceCartListByDates;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }



    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
