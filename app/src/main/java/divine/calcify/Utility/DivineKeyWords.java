package divine.calcify.Utility;

import java.net.PortUnreachableException;

/**
 * Created by Calcify3 on 25-05-2016.
 */
public class DivineKeyWords {
    public static String ipAddress = "192.168.1.114";
    //list of partner-services available for each service within groups corresponding to current/home location
    public static String SERVICES_KEY = "SERVICES";
    public static String GROUP_SERVICES_URL = "http://"+ipAddress+":8080/App/partner/getAllServiceByLocationId";


    //list of events of temples based on location
    public static String EVENTS_KEY = "EVENTS";
    public static String TEMPLE_EVENTS_URL="http://"+ipAddress+":8080/App/temple/getEventDetailsByLocationID";



    //to get list of locations from webservice
    public static String LOCATIONS_KEY = "LOCATIONS";
    public static String LOCATIONS_URL = "http://"+ipAddress+":8080/App/partner/getLocation";

    //to show the list of groups
    public static String GROUPS_KEY ="GROUPS";
    public static String ALL_GROUPS_URL= "http://"+ipAddress+":8080/App/partner/getGroup";


    //to show list of partners available for service
    public static String PARTNERS_LIST_OF_SERVICE_KEY = "PARTNERS_SERVICES";
    public static String PARTNERS_LIST_OF_SERVICE_URL = "http://"+ipAddress+":8080/App/partner/getPartnerAndServicedetails";
    //parameters to send -ex: locationID=1&serviceID=1

    //get partner profile info
    public static String PARTNER_PROFILE_INFO = "PARTNER_PROFILE_INFO";
    public static String PARTNER_PROFILE_URL = "http://"+ipAddress+":8080/App/partner/getPartnerDetailsByPartnerID";
    //Parameter to send ex : partnerID=39

    //send quoted information for service
    public static String QUOTED_INFO_OF_SERVICE_KEY = "QUOTED_INFO_OF_SERVICE";
    public static String QUOTED_INFO_OF_SERVICE_URL = "http://"+ipAddress+":8080/App/partner/insertUserQuotesDetails";

    //new user authentication //DATE TOBE SENT EX : mobileNumber=9008051613
    public static String USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY = "USER_CHECK_UNIQUE_MOBILE_NUMBER_KEY";
    public static String USER_UNIQUE_MOBILE_NUMBER_URL = "http://"+ipAddress+":8080/App/user/userMobileNumberAuthentication";


    //save user data after signup // DATA
    public static String SAVE_USER_LOGIN_INFO_KEY = "SAVE_USER_LOGIN_INFO_KEY";
    public static String SAVE_USER_LOGIN_INFO_URL = "http://"+ipAddress+":8080/App/user/insertUserDetails";

    //update information of profile
    public static String UPDATE_PROFILE_OF_USER_KEY = "UPDATE_PROFILE_OF_USER_KEY";
    public static String UPDATE_PROFILE_OF_USER_URL = "http://"+ipAddress+":8080/App/user/updateUserDetails";

    //get event information // eventID=15
    public static String GET_EVENT_INFORMATION_KEY = "GET_EVENT_INFORMATION_KEY";
    public static String GET_EVENT_INFORMATION_URL = "http://"+ipAddress+":8080/App/temple/getEventDetailsByEventID";

    //insert service cart details
    public static String INSERT_SERVICE_CART_DETAILS_KEY = "INSERT_SERVICE_CART_DETAILS_KEY";
    public static String INSERT_SERVICE_CART_DETAILS_URL ="http://"+ipAddress+":8080/App/partner/insertEnquiryDetails";

    //get list of services saved in the cart
    public static String GET_SERVICES_LIST_FROM_CART_KEY = "GET_SERVICES_LIST_FROM_CART_KEY";
    public static String GET_SERVICES_LIST_FROM_CART_URL = "http://"+ipAddress+":8080/App/partner/getCartListByUserID";


    //purchase order for service cart
    public static String PURCHASE_ORDER_SERVICE_CART_KEY = "PURCHASE_ORDER_SERVICE_CART_KEY";
    public static String PURCHASE_ORDER_SERVICE_CART_URL = "http://"+ipAddress+":8080/App/partner/orderConformByUser";



    //divine group services - god with its pooja list
    //services - simply list of pooja, each services identified with id to match which pooja belong to which god.
    //detailgroupactivity - show list of god with services according to location

    //Exception Tags

    //FIREBASE TOKEN
    public static String FcmSharedPreferences = "FCM_TOKEN_SHARED_PREF";
    public static String FcmToken = "FCM_TOKEN";

    //cart keys for service cart and event cart
    public static String SERVICE_CART_DATA = "SERVICES_CART";
    public static String EVENT_CART_DATA = "EVENTS_CART";


}
