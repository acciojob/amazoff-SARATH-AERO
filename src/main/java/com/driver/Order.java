package com.driver;

import java.util.Arrays;
import java.util.List;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;

        this.deliveryTime = convertStringDeliveryTimetoInt(deliveryTime);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public int convertStringDeliveryTimetoInt(String time1) {
//        List<String> list = Arrays.asList(deliveryTime.split(":"));
//        System.out.println(list.toString());
//        int hh = Integer.parseInt(list.get(0));
//        int mm = Integer.parseInt(list.get(1));

//        return  hh * 60 + mm;
        int hh = Integer.parseInt(time1.substring(0,2));
        int mm = Integer.parseInt(time1.substring(3));
        return  hh * 60 + mm;

    }

    public String convertIntToStringDeliveryTime(int time2) {
        int hh = time2 / 60;
        int mm = time2 % 60;
        String HH = String.valueOf(hh);
        String MM = String.valueOf(mm);
        if(HH.length() == 1)
            HH = "0" + HH;
        if(MM.length() == 1)
            MM = "0" + MM;

        return  HH + ":" + MM;
    }
}
