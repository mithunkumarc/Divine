package divine.calcify.model;

import java.io.Serializable;

/**
 * Created by Calcify3 on 29-09-2016.
 */
public class Events implements Serializable{
    private String eventId;
    private String eventName;
    private String eventTimings;
    private String eventDate;
    private String TempleName;
    private String TempleId;
    private String location;
    private String locationId;
    private String eventInfo;
    private String eventCost;
    private String groupId;
    private String groupName;
    private String eventDescription;

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEventCost() {
        return eventCost;
    }

    public void setEventCost(String eventCost) {
        this.eventCost = eventCost;
    }

    public String getTempleName() {
        return TempleName;
    }

    public void setTempleName(String templeName) {
        TempleName = templeName;
    }

    public String getTempleId() {
        return TempleId;
    }

    public void setTempleId(String templeId) {
        TempleId = templeId;
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

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTimings() {
        return eventTimings;
    }

    public void setEventTimings(String eventTimings) {
        this.eventTimings = eventTimings;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
