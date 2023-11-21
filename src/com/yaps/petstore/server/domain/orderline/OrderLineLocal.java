package com.yaps.petstore.server.domain.orderline;

import com.yaps.petstore.server.domain.item.ItemLocal;

import javax.ejb.EJBLocalObject;

public interface OrderLineLocal extends EJBLocalObject {

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    int getQuantity();

    void setQuantity(final int quantity);

    double getUnitCost();

    void setUnitCost(final double unitCost);

    // ======================================
    // =      CMR Getters and Setters       =
    // ======================================
//    OrderLocal getOrder();

//    void setOrder(final OrderLocal order);

    ItemLocal getItem();

    void setItem(final ItemLocal item);
}
