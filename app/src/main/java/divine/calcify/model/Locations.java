package divine.calcify.model;

/**
 * Created by Calcify3 on 19-05-2016.
 */
public class Locations {
    public String location_name;
    public String location_id;
    public Locations(){

    }
    public Locations(String location_name,String location_id){
        this.location_id = location_id;
        this.location_name = location_name;
    }
    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
}
