package divine.calcify.model;

import java.io.Serializable;

/**
 * Created by Calcify3 on 21-05-2016.
 */
public class Services implements Serializable {
    private String service_name;
    private String service_id;
    //how many services available in that location for particular service
    private String service_count;

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
