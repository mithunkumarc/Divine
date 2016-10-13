package divine.calcify.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import divine.calcify.model.Locations;

public class SharedPreference {

    // Avoid magic numbers.
    private static final int MAX_SIZE = 3;


    public SharedPreference() {
        super();
    }


    public static void storeList(Context context,String pref_name, String key, List<Locations> locations) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(locations);
        editor.putString(key, jsonFavorites);
        editor.apply();
    }

 /*   public static void storeList(Context context,String pref_name, String key, List countries) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(countries);
        editor.putString(key, jsonFavorites);
        editor.apply();
    }*/



    public static ArrayList<Locations> loadList(Context context,String pref_name, String key) {

        SharedPreferences settings;
        List<Locations> favorites=new ArrayList<>();
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

        //settings.edit().clear().commit(); it will clear all sp
        //settings.edit().clear().commit();
        if (settings.contains(key)) {
            Gson gson = new Gson();
            String jsonFavorites = settings.getString(key, null);
            Log.d("gson====",jsonFavorites);
            Type type = new TypeToken<List<Locations>>(){}.getType();
            favorites = gson.fromJson(jsonFavorites, type);
            return (ArrayList<Locations>) favorites;
        } else{
            return null;
        }



        /*Locations locations1 = new Locations();
        locations1.setLocation_id("1");
        locations1.setLocation_name("Bangalore");
        Locations locations2 = new Locations();
        locations2.setLocation_id("2");
        locations2.setLocation_name("Shivamogga");
        favorites.add(locations1);
        favorites.add(locations2);
        return (ArrayList<Locations>) favorites;*/
    }

    public static void addList(Context context, String pref_name, String key,Locations locations) {
        List<Locations> favorites = loadList(context, pref_name, key);

        if (favorites == null){
            favorites = new ArrayList<Locations>();
        }

        if(favorites.size() > MAX_SIZE) {
            //favorites.clear();
            favorites.remove(favorites.get(0));
            deleteList(context, pref_name);
        }

        Set<String> location_keys = new HashSet<String>();
        if(favorites.size()==0){
            favorites.add(locations);
        }if(favorites.size()>0) {
//check it out
            for(int index =0; index<favorites.size(); index++){
                location_keys.add(favorites.get(index).getLocation_id());
            }
        }
        if (!location_keys.contains(locations.getLocation_id())){
            favorites.add(locations);
        }
        /*if(flag){

            favorites.add(locations);
        }*/
            storeList(context, pref_name, key, favorites);

    }

//    public static void removeList(Context context,String pref_name, String key, String country) {
//        ArrayList favorites = loadList(context, pref_name,key);
//        if (favorites != null) {
//            favorites.remove(country);
//            storeList(context, pref_name, key, favorites);
//        }
//    }


    public static void deleteList(Context context, String pref_name){

        SharedPreferences myPrefs = context.getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
    }

}