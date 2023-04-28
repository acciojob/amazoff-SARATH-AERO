package com.driver;

import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.*;

@org.springframework.stereotype.Repository
public class Repository {
     Map<String,Order> orderMap = new HashMap<>();
     Map<String,DeliveryPartner> partnerMap = new HashMap<>();

    Map<DeliveryPartner,List<Order>> pair = new HashMap<>();

    public void addOrder(String id, Order order) {
        orderMap.put(id, order);
    }
    public void addPartner(String id)  {
        DeliveryPartner partner = new DeliveryPartner(id);
        partnerMap.put(id, partner);
    }

    public void addOrderPartnerPair(Order order, DeliveryPartner partner) {
        if(pair.containsKey(partner)){
            List<Order> orderList = pair.get(partner);
            orderList.add(order);
            pair.put(partner, orderList);
        }else{
            List<Order> orderList = new ArrayList<>();
            orderList.add(order);
            pair.put(partner, orderList);
        }
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
    }

    public Order getOrderById(String id) {
        return orderMap.get(id);
    }

    public DeliveryPartner getPartnerById(String id) {
        return partnerMap.get(id);
    }

    public int getOrderCountByPartnerId(String id) {
        DeliveryPartner partner = partnerMap.get(id);
        return  pair.get(partner).size();
    }

    public List<Order> getOrdersByPartnerId(String partnerId) {
        DeliveryPartner partner = partnerMap.get(partnerId);
        return pair.get(partner);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    public int getCountOfUnassignedOrders() {
        int countInPair = 0;
        for (List<Order> orderList : pair.values()) {
            countInPair += orderList.size();
            }
        return orderMap.size() - countInPair;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String currentTime,String partnerId) {
        Integer count = 0;
        int currentInttime = convertStringDeliveryTimetoInt(currentTime);
        DeliveryPartner partner = partnerMap.get(partnerId);
        List<Order> orders1 = pair.get(partner);
            for (Order order2 : orders1) {
                if(order2.getDeliveryTime() > currentInttime)
                    count++;
            }

        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String id) {
        DeliveryPartner partner = partnerMap.get(id);
        List<Order> orderList = pair.get(partner);
        int time = 0;
        for (Order order2 : orderList) {
            time = Math.max(time, order2.getDeliveryTime());
        }
        return convertIntToStringDeliveryTime(time);
    }

    public void deletePartnerById(String id){
        DeliveryPartner partner = partnerMap.get(id);
        partnerMap.remove(partner);
        pair.remove(partner);
    }

    public void deleteOrderById(String id) {
        Order order1 = orderMap.get(id);
        orderMap.remove(id);
        for (DeliveryPartner partner : pair.keySet()) {
            List<Order> orderList = pair.get(partner);
            if(orderList.contains(order1)){
                orderList.remove(order1);
                pair.put(partner, orderList);
                partner.setNumberOfOrders(partner.getNumberOfOrders()-1);
                return;
            }
        }
    }

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



