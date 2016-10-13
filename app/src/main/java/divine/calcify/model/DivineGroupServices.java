package divine.calcify.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Calcify3 on 21-05-2016.
 */
/*
* this class has the group name and its services list
* */
public class DivineGroupServices {
    private String groupServiceId;
    private String groupServiceName;
    private ArrayList<Services> groupServicesList = new ArrayList<Services>();

    public String getGroupServiceId() {
        return groupServiceId;
    }

    public void setGroupServiceId(String groupServiceId) {
        this.groupServiceId = groupServiceId;
    }

    public String getGroupServiceName() {
        return groupServiceName;
    }

    public void setGroupServiceName(String groupServiceName) {
        this.groupServiceName = groupServiceName;
    }

    public ArrayList<Services> getGroupServicesList() {
        return groupServicesList;
    }

    public void setGroupServicesList(ArrayList<Services> groupServicesList) {
        this.groupServicesList = groupServicesList;
    }
}
