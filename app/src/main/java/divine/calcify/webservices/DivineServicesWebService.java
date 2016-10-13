package divine.calcify.webservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import divine.calcify.Utility.DivineKeyWords;
import divine.calcify.Utility.JsonParserUtility;
import divine.calcify.activities.CartActivity;
import divine.calcify.activities.DisplayEventInformation;
import divine.calcify.activities.HomeScreenActivity;
import divine.calcify.activities.PartnerProfileInfoActivity;
import divine.calcify.activities.PartnerProfileInformationActvity;
import divine.calcify.activities.RegUpdateMyProfileActivity;
import divine.calcify.activities.RegisterOtpScreenActivity;
import divine.calcify.activities.RegistrationHomeScreenActivity;
import divine.calcify.activities.ServicePartnerListActivity;
import divine.calcify.activities.ShowTempleListActivity;
import divine.calcify.com.divine.R;
import divine.calcify.fragments.GroupDetailFragment;
import divine.calcify.fragments.ServiceCartFragment;
import divine.calcify.fragments.ServicesFragment;
import divine.calcify.model.DivineGroupServices;
import divine.calcify.model.DivineServices;
import divine.calcify.model.Locations;
import divine.calcify.model.Partner;
import divine.calcify.model.Services;

/**
 * Created by Calcify3 on 20-05-2016.
 */
//this class for webservice call

public class DivineServicesWebService extends AsyncTask<String, Integer, Double> {

    //activity = represents which activity is calling webservice
    public Activity activity;
    //reply string = reply coming from server
    public String replyString,cart_id;
    //represents which webservice to call
    public String key;
    //path or url
    public String webservicePath;
    //location selected by the user
    public String location_selected;
    //get partners list of selected service
    public String locationId;
    public String serviceId;
    //partner profile info
    Partner partner;
    //hash map for quoting services to service providers
    HashMap<String,String> quoteHashMap;

    //hash map for user data saving while sign in
    HashMap<String,String> userDataHashMap,serviceCartInsertMap;

    //update profile
    HashMap<String,String> userProfileUpdate;

    //mobile number to check uniqueness while installing,signup+ getserviceCartListbyMObnum
    public String user_mobile_number;

    //list for namepairs
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    String eventId;//to get eventInfo

    ProgressDialog pDialog;

    public DivineServicesWebService(Activity activity,String key){
        //key used to decide which webservice to call, this constructor is now working for location and for service(with out location selection, need to modify for current)
        //location
        this.key = key;
        if(this.key.equals(DivineKeyWords.SERVICES_KEY)){
            this.webservicePath = DivineKeyWords.GROUP_SERVICES_URL;
        }
        //location
        if(this.key.equals(DivineKeyWords.LOCATIONS_KEY)){
            this.webservicePath = DivineKeyWords.LOCATIONS_URL;
        }
        //all groups
        if(this.key.equals(DivineKeyWords.GROUPS_KEY)){
            this.webservicePath = DivineKeyWords.ALL_GROUPS_URL;
        }
        this.activity = activity;
    }

    //this constructor is using to call webservice
    public DivineServicesWebService(Activity activity,String key,String value){
        this.activity = activity;
        this.key = key;
        //to get service list, with respect to location selected by the user
        if(this.key.equals(DivineKeyWords.SERVICES_KEY)){
            this.webservicePath = DivineKeyWords.GROUP_SERVICES_URL;
            this.location_selected = value;
        }
        //to check whether the mobile number is unique or not. while installing/signup
        if(this.key.equals(DivineKeyWords.USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY)){
            this.webservicePath = DivineKeyWords.USER_UNIQUE_MOBILE_NUMBER_URL;
            this.user_mobile_number = value;
        }
        //to get events list with respect ot locations selected by user
        if(this.key.equals(DivineKeyWords.EVENTS_KEY)){
            this.webservicePath = DivineKeyWords.TEMPLE_EVENTS_URL;
            this.location_selected = value;
        }
        //to get event information
        if (this.key.equals(DivineKeyWords.GET_EVENT_INFORMATION_KEY)){
            this.webservicePath = DivineKeyWords.GET_EVENT_INFORMATION_URL;
            this.eventId = value;
        }
        //get list of services saved in the cart list
        if (this.key.equals(DivineKeyWords.GET_SERVICES_LIST_FROM_CART_KEY)){
            this.webservicePath = DivineKeyWords.GET_SERVICES_LIST_FROM_CART_URL;
            this.user_mobile_number = value;
        }
        //purchase order for service cart
        if (this.key.equals(DivineKeyWords.PURCHASE_ORDER_SERVICE_CART_KEY)){
            this.webservicePath = DivineKeyWords.PURCHASE_ORDER_SERVICE_CART_URL;
            this.cart_id = value;
        }
    }

    //this constructor is used to get list of partners of selected service
    public DivineServicesWebService(Activity activity,String key,String serviceId,String locationId){
        this.activity = activity;
        this.key = key;
        this.serviceId = serviceId;
        this.locationId = locationId;
        if(this.key.equals(DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY)){
            this.webservicePath = DivineKeyWords.PARTNERS_LIST_OF_SERVICE_URL;
        }
    }

    //to get partner profile info
    public DivineServicesWebService(Activity activity, String key, Partner partner){
        this.activity = activity;
        this.key = key;
        this.partner = partner;
        if(this.key.equals(DivineKeyWords.PARTNER_PROFILE_INFO)){
            this.webservicePath = DivineKeyWords.PARTNER_PROFILE_URL;
        }
    }

    //
    public DivineServicesWebService(Activity activity, String key, HashMap<String,String> hashMap){
        this.activity = activity;
        this.key = key;
        //insert quote for selected service
        if(this.key.equals(DivineKeyWords.QUOTED_INFO_OF_SERVICE_KEY)){
            this.webservicePath = DivineKeyWords.QUOTED_INFO_OF_SERVICE_URL;
            this.quoteHashMap = hashMap;
        }
        //insert/save user data info while sign in, before update
        if (this.key.equals(DivineKeyWords.SAVE_USER_LOGIN_INFO_KEY)){
            this.webservicePath = DivineKeyWords.SAVE_USER_LOGIN_INFO_URL;
            this.userDataHashMap = hashMap;
        }
        //update profile information of user
        if (this.key.equals(DivineKeyWords.UPDATE_PROFILE_OF_USER_KEY)){
            this.webservicePath = DivineKeyWords.UPDATE_PROFILE_OF_USER_URL;
            this.userProfileUpdate = hashMap;
        }
        //insert service cart data to db
        if (this.key.equals(DivineKeyWords.INSERT_SERVICE_CART_DETAILS_KEY)){
            this.webservicePath = DivineKeyWords.INSERT_SERVICE_CART_DETAILS_URL;
            this.serviceCartInsertMap = hashMap;
        }

    }



    //**********************************************************************************//

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(pDialog!=null){
            pDialog.dismiss();
        }
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Please wait...");
            //pDialog.setCancelable(true);
    }


    @Override
    protected Double doInBackground(String... params) {
        // TODO Auto-generated method stub
        //
        this.key = key;
        this.serviceId = serviceId;
        this.locationId = locationId;

        //
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(webservicePath);
        HttpResponse response;

        try {
            //list of services based on selected location, here location is sent as parameter
            if(this.key.equals(DivineKeyWords.SERVICES_KEY) && location_selected!=null){
                nameValuePairs.add(new BasicNameValuePair("locationID", location_selected));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            //get list of partners available for particular service at given location
            if(this.key.equals(DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY)){
                nameValuePairs.add(new BasicNameValuePair("locationID",locationId));
                nameValuePairs.add(new BasicNameValuePair("serviceID",serviceId));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //get partner profile info
            if(this.key.equals(DivineKeyWords.PARTNER_PROFILE_INFO)){
                nameValuePairs.add(new BasicNameValuePair("partnerID",partner.getUserID()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //QUOTED SERVICE FOR SERVICE PROVIDERS
            if(this.key.equals(DivineKeyWords.QUOTED_INFO_OF_SERVICE_KEY)){
                nameValuePairs.add(new BasicNameValuePair("serviceID",quoteHashMap.get("serviceID")));
                nameValuePairs.add(new BasicNameValuePair("serviceName",quoteHashMap.get("serviceName")));
                nameValuePairs.add(new BasicNameValuePair("serviceDate",quoteHashMap.get("serviceDate")));
                nameValuePairs.add(new BasicNameValuePair("serviceTime",quoteHashMap.get("serviceTime")));
                nameValuePairs.add(new BasicNameValuePair("locationId",quoteHashMap.get("locationId")));
                nameValuePairs.add(new BasicNameValuePair("locationName",quoteHashMap.get("locationName")));
                nameValuePairs.add(new BasicNameValuePair("serviceAmount",quoteHashMap.get("serviceAmount")));
                nameValuePairs.add(new BasicNameValuePair("partnerName",quoteHashMap.get("partnerName")));
                nameValuePairs.add(new BasicNameValuePair("partnerID",quoteHashMap.get("partnerID")));
                nameValuePairs.add(new BasicNameValuePair("userQuoteAmout",quoteHashMap.get("userQuoteAmout")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            // to check uniqueness of mobile number while signup/installing for first time.DivineKeyWords.USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY
            if (this.key.equals(DivineKeyWords.USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY)){
                nameValuePairs.add(new BasicNameValuePair("mobileNumber",user_mobile_number));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //insert or saving user data info to the database, before update info of user
            if (this.key.equals(DivineKeyWords.SAVE_USER_LOGIN_INFO_KEY)){
                nameValuePairs.add(new BasicNameValuePair("userName",userDataHashMap.get("CustomerName")));
                nameValuePairs.add(new BasicNameValuePair("mobileNumber",userDataHashMap.get("MobileNumber")));
                nameValuePairs.add(new BasicNameValuePair("fcmTockenID",userDataHashMap.get("FcmTockenID")));
                nameValuePairs.add(new BasicNameValuePair("locationID",userDataHashMap.get("LocationID")));
                nameValuePairs.add(new BasicNameValuePair("location",userDataHashMap.get("Location")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //update profile information of user
            if (this.key.equals(DivineKeyWords.UPDATE_PROFILE_OF_USER_KEY)){
                nameValuePairs.add(new BasicNameValuePair("userName",userProfileUpdate.get("userName")));
                nameValuePairs.add(new BasicNameValuePair("mobileNumber",userProfileUpdate.get("mobileNumber")));
                nameValuePairs.add(new BasicNameValuePair("location",userProfileUpdate.get("location")));
                nameValuePairs.add(new BasicNameValuePair("locationID",userProfileUpdate.get("locationID")));
                nameValuePairs.add(new BasicNameValuePair("email",userProfileUpdate.get("email")));
                nameValuePairs.add(new BasicNameValuePair("gender",userProfileUpdate.get("gender")));
                nameValuePairs.add(new BasicNameValuePair("fcmTockenID",userProfileUpdate.get("fcmTockenID")));
                nameValuePairs.add(new BasicNameValuePair("ageGroup",userProfileUpdate.get("ageGroup")));
                nameValuePairs.add(new BasicNameValuePair("gotra",userProfileUpdate.get("gotra")));
                nameValuePairs.add(new BasicNameValuePair("nakshyatra",userProfileUpdate.get("nakshyatra")));
                nameValuePairs.add(new BasicNameValuePair("rashi",userProfileUpdate.get("rashi")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //to get the list of events based on location
            if (this.key.equals(DivineKeyWords.EVENTS_KEY)){
                nameValuePairs.add(new BasicNameValuePair("locationID",this.location_selected));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //to get event information
            if (this.key.equals(DivineKeyWords.GET_EVENT_INFORMATION_KEY)){
                nameValuePairs.add(new BasicNameValuePair("eventID",eventId));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //insert service cart data into db (EnquiriesCollection)
            if (this.key.equals(DivineKeyWords.INSERT_SERVICE_CART_DETAILS_KEY)){
                nameValuePairs.add(new BasicNameValuePair("serviceName",serviceCartInsertMap.get("serviceName")));
                nameValuePairs.add(new BasicNameValuePair("serviceID",serviceCartInsertMap.get("serviceID")));
                nameValuePairs.add(new BasicNameValuePair("serviceDate",serviceCartInsertMap.get("serviceDate")));
                nameValuePairs.add(new BasicNameValuePair("serviceTime",serviceCartInsertMap.get("serviceTime")));
                nameValuePairs.add(new BasicNameValuePair("locationName",serviceCartInsertMap.get("locationName")));
                Log.d("locationWS=",serviceCartInsertMap.get("locationName"));
                nameValuePairs.add(new BasicNameValuePair("locationId",serviceCartInsertMap.get("locationId")));
                nameValuePairs.add(new BasicNameValuePair("serviceAmount",serviceCartInsertMap.get("serviceAmount")));
                nameValuePairs.add(new BasicNameValuePair("partnerName",serviceCartInsertMap.get("partnerName")));
                nameValuePairs.add(new BasicNameValuePair("partnerID",serviceCartInsertMap.get("partnerID")));
                nameValuePairs.add(new BasicNameValuePair("customerName",serviceCartInsertMap.get("customerName")));
                nameValuePairs.add(new BasicNameValuePair("customerMobileNumber",serviceCartInsertMap.get("customerMobileNumber")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //to get list of services saved in the cart
            if (this.key.equals(DivineKeyWords.GET_SERVICES_LIST_FROM_CART_KEY)){
                nameValuePairs.add(new BasicNameValuePair("mobileNumber",user_mobile_number));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }

            //purchase order for service cart
            if (this.key.equals(DivineKeyWords.PURCHASE_ORDER_SERVICE_CART_KEY)){
                nameValuePairs.add(new BasicNameValuePair("cartID",cart_id));
            }

            //*************************http execution starts***********************************//

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);
            Log.d("after execute=",response.toString());
            replyString = EntityUtils.toString(response.getEntity());
            //list of services with out location, need to implement for current location
            if(this.key.equals(DivineKeyWords.SERVICES_KEY)){
                HomeScreenActivity.divineGroupServicesArrayList = JsonParserUtility.divineServicesList(replyString);
                Log.d("service string =",replyString);
            }
            //list of locations
            if(this.key.equals(DivineKeyWords.LOCATIONS_KEY)){
                //HomeScreenActivity.locationsArrayList = JsonParserUtility.listLocations(replyString);
                RegistrationHomeScreenActivity.locationsArrayList = JsonParserUtility.listLocations(replyString);
                HomeScreenActivity.locationsArrayList = RegistrationHomeScreenActivity.locationsArrayList;
            }
            //list of all available groups
            if(this.key.equals(DivineKeyWords.GROUPS_KEY)){
                HomeScreenActivity.divineAllGroupList = JsonParserUtility.listAllGroups(replyString);
            }
            //get partners list available for service at given location
            if(this.key.equals(DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY)){
                GroupDetailFragment.partnerArrayList = JsonParserUtility.getPartnerListOfService(replyString);
            }
            //get partner profile info
            if(this.key.equals(DivineKeyWords.PARTNER_PROFILE_INFO)){
                PartnerProfileInformationActvity.partnerProfileInfo = JsonParserUtility.getPartnerProfileInfo(replyString);
            }

            //quote services to service providers
            if ((this.key.equals(DivineKeyWords.QUOTED_INFO_OF_SERVICE_KEY))){
                Log.d("quote reply=",replyString);
            }

            //check whether mobile number is unique or not while sign up
            if(this.key.equals(DivineKeyWords.USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY)){
                RegistrationHomeScreenActivity.userUniqueNumberResult = JsonParserUtility.IsMobileNumberUnique(replyString);
            }

            //insert userdata info, before update profile
            if(this.key.equals(DivineKeyWords.SAVE_USER_LOGIN_INFO_KEY)){
                RegisterOtpScreenActivity.saveUserDetailsResult = JsonParserUtility.saveUserDataInfo(replyString);
            }

            //update profile information of user
            if (this.key.equals(DivineKeyWords.UPDATE_PROFILE_OF_USER_KEY)){
                RegUpdateMyProfileActivity.updateProfileResult = JsonParserUtility.updateUserDataInfo(replyString);
            }
            //to get the events list based on location
            if (this.key.equals(DivineKeyWords.EVENTS_KEY)){
                HomeScreenActivity.divineGroupsEventsArrayList = JsonParserUtility.parseEventsData(replyString);
            }

            //to get event information
            if (this.key.equals(DivineKeyWords.GET_EVENT_INFORMATION_KEY)){
                ShowTempleListActivity.event_info = JsonParserUtility.parseEventInfo(replyString);

            }

            //insert service cart details
            if(this.key.equals(DivineKeyWords.INSERT_SERVICE_CART_DETAILS_KEY)){
                Log.d("serviceCartResultWS=",replyString);
                ServicePartnerListActivity.insertServiceCartResult = JsonParserUtility.getResultOfInsertServiceCart(replyString);
            }

            //get the list of services saved in the cart
            if (this.key.equals(DivineKeyWords.GET_SERVICES_LIST_FROM_CART_KEY)){
                CartActivity.serviceCartArrayList = JsonParserUtility.getServicesFromCart(replyString);

                //Log.d("cart services =",newj);
            }

            //purchase order for service cart
            if (this.key.equals(DivineKeyWords.PURCHASE_ORDER_SERVICE_CART_KEY)){
                ServiceCartFragment.purchaseOrderServiceCartResult = JsonParserUtility.parsePurchaseOrderForServiceCart(replyString);
            }


        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            Log.d("EventsWS=",e.getMessage());
        }

        return 100.000;
    }
    //***************************************************post execute*******************************************//
    @Override
    protected void onPostExecute(Double result) {
        if (pDialog.isShowing()){
            if (this.key.equals(DivineKeyWords.SERVICES_KEY) && HomeScreenActivity.divineGroupServicesArrayList.size()>0){
                pDialog.dismiss();
            }
            if(this.key.equals(DivineKeyWords.LOCATIONS_KEY) && HomeScreenActivity.locationsArrayList.size()>0){
                pDialog.dismiss();
            }
            if(this.key.equals(DivineKeyWords.PARTNERS_LIST_OF_SERVICE_KEY) && GroupDetailFragment.partnerArrayList.size()>0){
                pDialog.dismiss();
            }
            if(this.key.equals(DivineKeyWords.PARTNER_PROFILE_INFO) && PartnerProfileInformationActvity.partnerProfileInfo!=null){
                TextView ppi_partner_info = (TextView) this.activity.findViewById(R.id.ppi_partner_info);
                ppi_partner_info.setText(PartnerProfileInformationActvity.partnerProfileInfo.getPartnerProfile());
                pDialog.dismiss();
            }
            if (this.key.equals(DivineKeyWords.QUOTED_INFO_OF_SERVICE_KEY)){
                pDialog.dismiss();
            }
            if(this.key.equals(DivineKeyWords.USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY)){
                pDialog.dismiss();
            }
            if (this.key.equals(DivineKeyWords.SAVE_USER_LOGIN_INFO_KEY)){
                pDialog.dismiss();
            }
            if(this.key.equals(DivineKeyWords.UPDATE_PROFILE_OF_USER_KEY)){
                pDialog.dismiss();
            }
            if (this.key.equals(DivineKeyWords.EVENTS_KEY)){
                pDialog.dismiss();
            }
            if (this.key.equals(DivineKeyWords.GET_EVENT_INFORMATION_KEY)){
                pDialog.dismiss();
            }
            if (this.key.equals(DivineKeyWords.INSERT_SERVICE_CART_DETAILS_KEY)){
                pDialog.dismiss();
            }
            if (this.key.equals(DivineKeyWords.GET_SERVICES_LIST_FROM_CART_KEY)){
                pDialog.dismiss();
            }

            if(result == 100.00){
                //Log.d("after exe =",result+"");
                //TextView ppi_partner_info = (TextView) this.activity.findViewById(R.id.ppi_partner_info);
                //ppi_partner_info.setText(PartnerProfileInformationActvity.partnerProfileInfo.getPartnerProfile());
                pDialog.dismiss();
            }
        }


        /*if(pDialog!=null){
            pDialog.dismiss();
        }*/
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}

