package com.driver;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class Repository {

     Map<String,Order> orderMap = new HashMap<>();
     Map<String,DeliveryPartner> partnerMap = new HashMap<>();

    Map<DeliveryPartner,List<Order>> pair = new HashMap<>();

    public void addOrder(Order order) {
        if(order != null) {
            String id = order.getId();
            orderMap.put(id, order);
        }
    }
    public void addPartner(String id)  {
        DeliveryPartner partner = new DeliveryPartner(id);
        partnerMap.put(id, partner);
    }

    public void addOrderPartnerPair(Order order, DeliveryPartner partner) {
        if(partnerMap.containsKey(partner.getId()) && orderMap.containsKey(order.getId())) {
            if (pair.containsKey(partner)) {
                List<Order> orderList = pair.get(partner);
                orderList.add(order);
                pair.put(partner, orderList);
            } else {
                List<Order> orderList = new ArrayList<>();
                orderList.add(order);
                pair.put(partner, orderList);
            }
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
            partnerMap.put(partner.getId(),partner);
        }
    }

    public Order getOrderById(String id) {
        if(!orderMap.containsKey(id))
            return null;
        return orderMap.get(id);
    }

    public DeliveryPartner getPartnerById(String id) {
        if(!partnerMap.containsKey(id))
            return null;
        return partnerMap.get(id);
    }

    public int getOrderCountByPartnerId(String id) {
        if(partnerMap.containsKey(id)) {
            DeliveryPartner partner = partnerMap.get(id);
            return partner.getNumberOfOrders();
        }
        return 0;
    }

    public List<Order> getOrdersByPartnerId(String partnerId) {
        if(partnerMap.containsKey(partnerId)) {
            DeliveryPartner partner = partnerMap.get(partnerId);
            if (partnerMap.containsKey(partnerId) && pair.containsKey(partner))
                return pair.get(partner);
        }
        return null;
    }

    public List<Order> getAllOrders() {
        if(orderMap.size() == 0)
            return null;
        return new ArrayList<>(orderMap.values());
    }

    public int getCountOfUnassignedOrders() {
        int countInPair = 0;
        if(pair.size() == 0)
            return orderMap.size();
        for (List<Order> orderList : pair.values()) {
            countInPair += orderList.size();
            }
        if (countInPair == 0) {
            if(orderMap.size() == 0)
                return  0;
            else
                return orderMap.size();
        }
            return orderMap.size() - countInPair;


    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String currentTime,String partnerId) {
        Integer count = 0;
        int currentInttime = convertStringDeliveryTimetoInt(currentTime);
        if(partnerMap.containsKey(partnerId)) {
            DeliveryPartner partner = partnerMap.get(partnerId);
            List<Order> orders1 = pair.get(partner);
            for (Order order2 : orders1) {
                if (order2.getDeliveryTime() > currentInttime)
                    count++;
            }
        }

        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String id) {
        if(partnerMap.containsKey(id)) {
            DeliveryPartner partner = partnerMap.get(id);
            List<Order> orderList = pair.get(partner);
            int time = 0;
            for (Order order2 : orderList) {
                time = Math.max(time, order2.getDeliveryTime());
            }
            if (time == 0)
                return null;
            return convertIntToStringDeliveryTime(time);
        }
        return null;
    }

    public void deletePartnerById(String id){
        if(partnerMap.containsKey(id)) {
            DeliveryPartner partner = partnerMap.get(id);
            partnerMap.remove(id);
            pair.remove(partner);
        }
    }

    public void deleteOrderById(String id) {
        if(orderMap.containsKey(id)) {
            Order order1 = orderMap.get(id);
            orderMap.remove(id);
            for (DeliveryPartner partner : pair.keySet()) {
                List<Order> orderList = pair.get(partner);
                if (orderList.contains(order1)) {
                    orderList.remove(order1);
                    pair.put(partner, orderList);
                    partner.setNumberOfOrders(partner.getNumberOfOrders() - 1);
                    return;
                }
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
        return  hh * 60 + mm;

    }


    public String convertIntToStringDeliveryTime(int time1) {
        int hh = time1 / 60;
        int mm = time1 % 60;
        String HH = String.valueOf(hh);
        String MM = String.valueOf(mm);
        if(HH.length() == 1)
            HH = "0" + HH;
        if(MM.length() == 1)
            MM = "0" + MM;

        return  HH + ":" + MM;
    }


}



