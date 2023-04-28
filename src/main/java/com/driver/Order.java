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

    public int convertStringDeliveryTimetoInt(String currentTime) {
//        List<String> list = Arrays.asList(deliveryTime.split(":"));
//        System.out.println(list.toString());
//        int hh = Integer.parseInt(list.get(0));
//        int mm = Integer.parseInt(list.get(1));

//        return  hh * 60 + mm;
        int hh = Integer.parseInt(currentTime.substring(0,2));
        int mm = Integer.parseInt(currentTime.substring(3));
        System.out.println(hh +"    "+mm);
        System.out.println(currentTime);
        return  hh * 60 + mm;

    }

    public String convertIntToStringDeliveryTime(int deliveryTime) {
        int hh = deliveryTime / 60;
        int mm = deliveryTime % 60;
        String HH = String.valueOf(hh);
        String MM = String.valueOf(mm);
        if(HH.length() == 1)
            HH = "0" + HH;
        if(MM.length() == 1)
            MM = "0" + MM;

        return  HH + ":" + MM;
    }
}
