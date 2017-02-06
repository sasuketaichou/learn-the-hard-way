package com.example.amieruljapri.myapplication27;

/**
 * Created by amierul.japri on 1/26/2017.
 */

public class PojoCreateTicketValues {
    String type;
    String urgency;
    String category;
    String itemtype;
    String item = "777";
    String title = "title";
    String content1 = "content";
    String user_email;
    String user_email_notification;

    private static volatile PojoCreateTicketValues instance;

    public PojoCreateTicketValues() {
    }

    public static PojoCreateTicketValues getInstance(){
        if(instance == null){
            synchronized (ApiClient.class){
                if(instance == null){
                    instance = new PojoCreateTicketValues();
                }
            }
        }
        return instance;
    }
}
