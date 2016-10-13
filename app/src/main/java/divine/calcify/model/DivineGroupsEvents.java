package divine.calcify.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Calcify3 on 29-09-2016.
 */
public class DivineGroupsEvents {
    private String groupId;
    private String groupName;
    private HashSet<String> uniqueTempleIds=new HashSet<String>();

    public HashSet<String> getUniqueTempleIds() {
        return uniqueTempleIds;
    }

    public void setUniqueTempleIds(HashSet<String> uniqueTempleIds) {
        this.uniqueTempleIds = uniqueTempleIds;
    }

    private ArrayList<Temple> templeArrayList = new ArrayList<>();

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<Temple> getTempleArrayList() {
        return templeArrayList;
    }

    public void setTempleArrayList(ArrayList<Temple> templeArrayList) {
        this.templeArrayList = templeArrayList;
    }
}