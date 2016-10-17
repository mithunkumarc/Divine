package divine.calcify.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Calcify3 on 21-05-2016.
 */
public class Services implements Serializable {
    private String service_name;
    private String service_id;
    //how many services available in that location for particular service
    private String service_count;
    //below required for cart
    private String dateSelectedByCustomerForService;
    private String timeSelectedByCustomerForService;
    private String statusOfServiceSelectedByCustomer;
    private String enquiryId;//cart service id
    private ArrayList<Partner> partnerArrayList = new ArrayList<>();
    private String serviceCost;

    private HashSet<String> uniqueServiceIds = new HashSet<>();

    public HashSet<String> getUniqueServiceIds() {
        return uniqueServiceIds;
    }

    public void setUniqueServiceIds(HashSet<String> uniqueServiceIds) {
        this.uniqueServiceIds = uniqueServiceIds;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getDateSelectedByCustomerForService() {
        return dateSelectedByCustomerForService;
    }

    public void setDateSelectedByCustomerForService(String dateSelectedByCustomerForService) {
        this.dateSelectedByCustomerForService = dateSelectedByCustomerForService;
    }

    public String getTimeSelectedByCustomerForService() {
        return timeSelectedByCustomerForService;
    }

    public void setTimeSelectedByCustomerForService(String timeSelectedByCustomerForService) {
        this.timeSelectedByCustomerForService = timeSelectedByCustomerForService;
    }

    public String getStatusOfServiceSelectedByCustomer() {
        return statusOfServiceSelectedByCustomer;
    }

    public void setStatusOfServiceSelectedByCustomer(String statusOfServiceSelectedByCustomer) {
        this.statusOfServiceSelectedByCustomer = statusOfServiceSelectedByCustomer;
    }

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public ArrayList<Partner> getPartnerArrayList() {
        return partnerArrayList;
    }

    public void setPartnerArrayList(ArrayList<Partner> partnerArrayList) {
        this.partnerArrayList = partnerArrayList;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_count() {
        return service_count;
    }

    public void setService_count(String service_count) {
        this.service_count = service_count;
    }
}
