package divine.calcify.Utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.*;

import java.util.ArrayList;
import java.util.HashSet;

import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.DivineGroupsEvents;
import divine.calcify.model.Events;
import divine.calcify.model.Locations;
import divine.calcify.model.Partner;
import divine.calcify.model.PartnerProfileInfo;
import divine.calcify.model.ServiceCart;
import divine.calcify.model.Services;
import divine.calcify.model.Temple;

/**
 * Created by Calcify3 on 20-05-2016.
 */
public class JsonParserUtility {

    //to parse json for services
    public static ArrayList<DivineGroupServices> divineServicesList(String jsonString)throws JSONException {
        //to add only unique group by adding only groupid to hashset
        HashSet<String> GroupID = new HashSet<String>();
        ArrayList<DivineGroupServices> divineGroupServicesArrayList = new ArrayList<DivineGroupServices>();
        JSONObject jsonServices = new JSONObject(jsonString);
        JSONArray servicesArray = jsonServices.getJSONArray("serviceBO");
        for(int i=0;i<servicesArray.length();i++){
            JSONObject groupObject = (JSONObject)servicesArray.get(i);
            if(GroupID.add(groupObject.getString("groupID"))){
                DivineGroupServices divineGroupServices = new DivineGroupServices();
                divineGroupServices.setGroupServiceId(groupObject.getString("groupID"));
                divineGroupServices.setGroupServiceName(groupObject.getString("groupName"));
                divineGroupServicesArrayList.add(divineGroupServices);
                //adding first service
                if(divineGroupServicesArrayList.size()>0){
                    for(int index=0;index<divineGroupServicesArrayList.size();index++){
                        if(groupObject.getString("groupID").equals(divineGroupServicesArrayList.get(index).getGroupServiceId())){
                            Services services = new Services();
                            services.setService_id(groupObject.getString("serviceID"));
                            services.setService_name(groupObject.getString("serviceName"));
                            services.setService_count(groupObject.getString("count"));
                            divineGroupServicesArrayList.get(index).getGroupServicesList().add(services);
                        }
                    }
                }
            }else{
                if(divineGroupServicesArrayList.size()>0){
                    for(int index=0;index<divineGroupServicesArrayList.size();index++){
                        if(groupObject.getString("groupID").equals(divineGroupServicesArrayList.get(index).getGroupServiceId())){
                            Services services = new Services();
                            services.setService_id(groupObject.getString("serviceID"));
                            services.setService_name(groupObject.getString("serviceName"));
                            services.setService_count(groupObject.getString("count"));
                            divineGroupServicesArrayList.get(index).getGroupServicesList().add(services);
                        }
                    }
                }
            }
        }
        return divineGroupServicesArrayList;
    }

    //divine event list. to parse json for events__________________________________________
    public static ArrayList<DivineGroupsEvents> parseEventsData(String jsonTempleEvetns){
        Log.d("jsonTempleEvetns=",jsonTempleEvetns);
        HashSet<String> GroupEventID = new HashSet<String>();
        ArrayList<DivineGroupsEvents> divineGroupEventsArrayList = new ArrayList<DivineGroupsEvents>();
        try{
            JSONObject jsonEvents = new JSONObject(jsonTempleEvetns);
            JSONArray EventsArray = jsonEvents.getJSONArray("eventsBO");

            for(int i=0;i<EventsArray.length();i++){
                DivineGroupsEvents divineGroupsEvents = new DivineGroupsEvents();
                JSONObject groupObject = (JSONObject)EventsArray.get(i);
                if(GroupEventID.add(groupObject.getString("groupID"))){
                    divineGroupsEvents.setGroupId(groupObject.getString("groupID"));
                    divineGroupsEvents.setGroupName(groupObject.getString("groupName"));
                    //event
                    Events events = new Events();
                    events.setEventId(groupObject.getString("eventID"));
                    events.setEventName(groupObject.getString("eventName"));
                    events.setEventDate(groupObject.getString("eventDate"));
                    events.setEventTimings(groupObject.getString("eventTime"));
                    //temple//
                    Temple temple = new Temple();
                    temple.setTempleId(groupObject.getString("templeID"));
                    //Log.d(groupObject.getString("groupID"),"="+groupObject.getString("templeID"));
                    temple.setTempleName(groupObject.getString("templeName"));
                    temple.setEventsCount(groupObject.getString("count"));
                    temple.setLocationId(groupObject.getString("locationID"));
                    temple.setLocationName(groupObject.getString("locationName"));
                    temple.getEventsArrayList().add(events);
                    //
                    divineGroupsEvents.getTempleArrayList().add(temple);
                    divineGroupsEvents.getUniqueTempleIds().add(groupObject.getString("templeID"));
                    divineGroupEventsArrayList.add(divineGroupsEvents);
                }else {
                    for (int indexInner=0;indexInner<divineGroupEventsArrayList.size();indexInner++){
                        if (divineGroupEventsArrayList.get(indexInner).getGroupId().equals(groupObject.getString("groupID"))){
                            Events events = new Events();
                            events.setEventId(groupObject.getString("eventID"));
                            events.setEventName(groupObject.getString("eventName"));
                            events.setEventDate(groupObject.getString("eventDate"));
                            events.setEventTimings(groupObject.getString("eventTime"));

                            if (divineGroupEventsArrayList.get(indexInner).getUniqueTempleIds().add(groupObject.getString("templeID"))){
                                //temple//
                                Temple temple = new Temple();
                                temple.setTempleId(groupObject.getString("templeID"));
                                //Log.d(groupObject.getString("groupID"),"="+groupObject.getString("templeID"));
                                temple.setTempleName(groupObject.getString("templeName"));
                                temple.setEventsCount(groupObject.getString("count"));
                                temple.setLocationId(groupObject.getString("locationID"));
                                temple.setLocationName(groupObject.getString("locationName"));
                                temple.getEventsArrayList().add(events);
                                divineGroupEventsArrayList.get(indexInner).getTempleArrayList().add(temple);
                                divineGroupEventsArrayList.get(indexInner).getUniqueTempleIds().add(groupObject.getString("templeID"));
                            }else {
                                for (int indexd = 0;indexd<divineGroupEventsArrayList.get(indexInner).getTempleArrayList().size();indexd++){
                                    if (divineGroupEventsArrayList.get(indexInner).getTempleArrayList().get(indexd).getTempleId().equals(groupObject.getString("templeID"))){
                                        divineGroupEventsArrayList.get(indexInner).getTempleArrayList().get(indexd).getEventsArrayList().add(events);
                                        Log.d(divineGroupEventsArrayList.get(indexInner).getTempleArrayList().get(indexd).getTempleId(),"=templeid");
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch (Exception ex){
            Log.d("EventsParseErr:=",ex.getMessage());
        }
        return divineGroupEventsArrayList;
    }
    //divine event list to parse json for events__________________________________________


    //for locations
    public static ArrayList<Locations> listLocations(String location_json)throws JSONException{
        ArrayList<Locations> locationsArrayList = new ArrayList<Locations>();
        JSONObject jsonLocations = new JSONObject(location_json);
        JSONArray locationArray = jsonLocations.getJSONArray("partnerBO");
        for(int index =0 ; index < locationArray.length(); index++){
            Locations locations = new Locations();
            JSONObject jsonObject = (JSONObject)locationArray.get(index);
            locations.setLocation_id(jsonObject.getString("locationID"));
            locations.setLocation_name(jsonObject.getString("location"));
            locationsArrayList.add(locations);
        }
        return locationsArrayList;
    }

    //for all groups or god names
    public static ArrayList<DivineGroupServices> listAllGroups(String allGroups_json)throws JSONException{
        Log.d("jsongroup=",allGroups_json);
        ArrayList<DivineGroupServices> divineAllGroupList = new ArrayList<DivineGroupServices>();
        JSONObject jsonGroupsList = new JSONObject(allGroups_json);
        JSONArray groupsArray = jsonGroupsList.getJSONArray("partnerBO");
        for(int index =0 ; index < groupsArray.length(); index++){
            DivineGroupServices divineGroupServices = new DivineGroupServices();
            JSONObject jsonObject = (JSONObject)groupsArray.get(index);
            divineGroupServices.setGroupServiceId(jsonObject.getString("groupID"));
            divineGroupServices.setGroupServiceName(jsonObject.getString("groupName"));
            divineAllGroupList.add(divineGroupServices);
        }
        return divineAllGroupList;
    }

    public static String formatLocationNameGoogleApi(String location_name){
        location_name = location_name.trim();
        if(location_name.length()>0){
            if(location_name.length()==1){
                return location_name;
            }
            if(location_name.length()>1){
                String[] get_location = location_name.split(" ");
                int length = get_location.length;
                location_name= get_location[length-1];
            }
        }
        location_name = location_name.trim();
        return location_name;
    }

    public static ArrayList<Partner> getPartnerListOfService(String partnerlistJson){
        ArrayList<Partner> partnerListOfService = new ArrayList<Partner>();
        JSONObject jsonGroupsList;
        JSONArray partnerArray;
        try {
            jsonGroupsList = new JSONObject(partnerlistJson);
            partnerArray = jsonGroupsList.getJSONArray("serviceBO");
            for(int index =0 ; index < partnerArray.length(); index++){
                Partner partner = new Partner();
                JSONObject jsonObject = (JSONObject)partnerArray.get(index);
                partner.setUserID(jsonObject.getString("userID"));
                partner.setPartnerName(jsonObject.getString("partnerName"));
                partner.setServiceID(jsonObject.getString("serviceID"));
                partner.setServiceName(jsonObject.getString("serviceName"));
                partner.setServiceCost(jsonObject.getString("serviceCost"));
                partner.setLocationId(jsonObject.getString("locationID"));
                partner.setLocation(jsonObject.getString("location"));
                partnerListOfService.add(partner);
            }
        }catch (Exception e){
            String jsonobjStr = partnerlistJson.substring(13,221);
            try {
                JSONObject singleObj = new JSONObject(jsonobjStr);
                Partner partner = new Partner();
                partner.setUserID(singleObj.getString("userID"));
                partner.setPartnerName(singleObj.getString("partnerName"));
                partner.setServiceID(singleObj.getString("serviceID"));
                partner.setServiceName(singleObj.getString("serviceName"));
                partner.setServiceCost(singleObj.getString("serviceCost"));
                partner.setLocationId(singleObj.getString("locationID"));
                partner.setLocation(singleObj.getString("location"));
                partnerListOfService.add(partner);
                /*Log.d("jsonobjStr=",jsonobjStr);
                Log.d("whats valure",partnerlistJson.charAt(13)+"");*/
            }catch (Exception e1){

            }
        }

        return partnerListOfService;
    }

    //get partner profile info
    public static PartnerProfileInfo getPartnerProfileInfo(String partnerInfo)throws JSONException{
        PartnerProfileInfo partnerProfileInfo = new PartnerProfileInfo();
        JSONObject jsonObject = new JSONObject(partnerInfo);
        partnerProfileInfo.setPartnerName(jsonObject.getString("partnerFirstName").trim());
        partnerProfileInfo.setPartnerLocation(jsonObject.getString("partnerLocation").trim());
        partnerProfileInfo.setPartnerProfile(jsonObject.getString("partnerProfile").trim());
        return partnerProfileInfo;
    }

    //check mobile number is uniquer or not, for sign up while installing
    public static String IsMobileNumberUnique(String result)throws JSONException{
        JSONObject jsonObject = new JSONObject(result);
        String uniqueResult = jsonObject.getString("unique");
        return uniqueResult;
    }

    //save/insert user data in db, before update info
    public static String saveUserDataInfo(String result)throws JSONException{
        JSONObject jsonObject = new JSONObject(result);
        String savedResult = jsonObject.getString("exception");
        return savedResult;
    }


    //update profile information of user
    public static String updateUserDataInfo(String result)throws JSONException{
        JSONObject jsonObject = new JSONObject(result);
        String savedResult = jsonObject.getString("exception");
        return savedResult;
    }

    //insert service cart data
    public static String getResultOfInsertServiceCart(String result){
        String saveResult="";
        try {
            JSONObject jsonObject = new JSONObject(result);
            saveResult = jsonObject.getString("exception");
        }catch (Exception e){
            Log.d("parseServiceCartErr:",e.getMessage());
        }
        return saveResult;
    }

    //getEventInfo
    public static Events parseEventInfo(String jsonEvent){
        Events events = new Events();
        try {
            JSONObject jsonObject = new JSONObject(jsonEvent);
            events.setEventId(jsonObject.getString("eventID"));
            events.setEventName(jsonObject.getString("eventName"));
            events.setEventTimings(jsonObject.getString("eventTime"));
            events.setEventDate(jsonObject.getString("eventDate"));
            events.setEventCost(jsonObject.getString("eventCost"));
            events.setLocationId(jsonObject.getString("locationID"));
            events.setTempleId(jsonObject.getString("templeID"));
            events.setGroupId(jsonObject.getString("groupID"));
            events.setGroupName(jsonObject.getString("groupName"));
            events.setEventDescription(jsonObject.getString("eventDiscription"));
            events.setTempleName(jsonObject.getString("templeName"));
            events.setLocation(jsonObject.getString("locationName"));
            Log.d("reply=", events.getEventInfo());
        }catch (Exception e){
            Log.d("parse event=",e.getMessage());
        }
        return events;
    }

    //get list of services saved in cart along with selected partners
    public static ArrayList<ServiceCart> getServicesFromCart(String jsonServicesFromCart){
        ArrayList<ServiceCart> serviceCartArrayList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonServicesFromCart);
            JSONArray jsonArray = jsonObject.getJSONArray("cartBO");
            for (int index=0;index<jsonArray.length();index++){
                JSONObject innerJsonObj = jsonArray.getJSONObject(index);
                ServiceCart serviceCart = new ServiceCart();
                serviceCart.setCartId(innerJsonObj.getString("cartID"));
                serviceCart.setCost(innerJsonObj.getString("cost"));
                serviceCart.setDate(innerJsonObj.getString("dateOfEvent"));
                serviceCart.setTime(innerJsonObj.getString("timeOfEvent"));
                serviceCart.setServiceName(innerJsonObj.getString("serviceName"));
                serviceCart.setPartnerName(innerJsonObj.getString("partnerName"));
                serviceCart.setStatus(innerJsonObj.getString("status"));
                serviceCartArrayList.add(serviceCart);
            }
        }catch (Exception e){
            Log.d("parseServideFrmCartErr:",e.getMessage());
            String newj = jsonServicesFromCart.substring(10,jsonServicesFromCart.length()-1);
            try {
                JSONObject innerJsonObjtc = new JSONObject(newj);
                ServiceCart serviceCarttc = new ServiceCart();
                serviceCarttc.setCartId(innerJsonObjtc.getString("cartID"));
                serviceCarttc.setCost(innerJsonObjtc.getString("cost"));
                serviceCarttc.setDate(innerJsonObjtc.getString("dateOfEvent"));
                serviceCarttc.setTime(innerJsonObjtc.getString("timeOfEvent"));
                serviceCarttc.setServiceName(innerJsonObjtc.getString("serviceName"));
                serviceCarttc.setPartnerName(innerJsonObjtc.getString("partnerName"));
                serviceCarttc.setStatus(innerJsonObjtc.getString("status"));
                serviceCartArrayList.add(serviceCarttc);
            }catch (Exception e1){

            }

        }
        return serviceCartArrayList;
    }

    //parsePurchaseOrderForServiceCart
    public static String parsePurchaseOrderForServiceCart(String jsonString){
        String result="";
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            result = jsonObject.getString("exception");
        }catch (Exception e){
            Log.d("parsePurchaseErr:",e.getMessage());
        }
        return result;
    }

}
