package divine.calcify.model;

import java.util.ArrayList;

/**
 * Created by Calcify3 on 11-05-2016.
 */
public class DivineServices {
    private String name;
    private String text;
    public static ArrayList<String> service_list=new ArrayList<String>();

    public DivineServices(String name,String text){
        this.name = name;
        this.text = text;
    }

    public ArrayList<String> getService_list() {
        return service_list;
    }

    public static void setService_list() {
        service_list.add("service1");
        service_list.add("service2");
        service_list.add("service3");
        service_list.add("service4");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
