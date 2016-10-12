package divine.calcify.com.divine;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import divine.calcify.Utility.DivineKeyWords;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("token refresh =",token);
        registerToken(token);

    }



    private void registerToken(String token) {
        SharedPreferences userDataSharedPref = getSharedPreferences(DivineKeyWords.FcmSharedPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userDataSharedPref.edit();
        editor.putString(DivineKeyWords.FcmToken,token);//to be implemented
        editor.commit();

        /*OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.141/fcm/register.php")
                .post(body)
                .build();
*/
        try {
            //client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
