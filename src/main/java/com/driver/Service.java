package com.driver;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    Repository repository;

    public void addOrder(Order order) {
        repository.addOrder(order.getId(),order);
    }

    public void addPartner(String  partnerId) {
        repository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        Order order = repository.orderMap.get(orderId);
        DeliveryPartner partner = repository.partnerMap.get(partnerId);
        repository.addOrderPartnerPair(order,partner);
    }

    public Order getOrderById(String id) {
        return repository.getOrderById(id);
    }

    public DeliveryPartner getPartnerById(String id) {
        return repository.getPartnerById(id);
    }

    public int getOrderCountByPartnerId(String id) {
        return repository.getOrderCountByPartnerId(id);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orders = new ArrayList<>();
        List<Order> orderList = repository.getOrdersByPartnerId(partnerId);
        for(Order order1 : orderList)
            orders.add(order1.getId());
        return orders;
    }

    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        List<Order> orderList = repository.getAllOrders();
        for(Order order1 : orderList)
            orders.add(order1.getId());
        return orders;

    }

    public int getCountOfUnassignedOrders() {
        return repository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String currentTime,String partnerId) {
        return repository.getOrdersLeftAfterGivenTimeByPartnerId(currentTime,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String id) {
        return repository.getLastDeliveryTimeByPartnerId(id);
    }

    public void deletePartnerById(String id) {
        repository.deletePartnerById(id);
    }

    public void deleteOrderById(String id) {
        repository.deleteOrderById(id);
    }
}
