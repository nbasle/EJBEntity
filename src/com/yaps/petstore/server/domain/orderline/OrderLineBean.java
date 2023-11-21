package com.yaps.petstore.server.domain.orderline;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.item.ItemLocal;

import javax.ejb.CreateException;

/**
 * An Order has several order lines. This class represent one order line.
 */
public abstract class OrderLineBean extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used to get a unique id with the UniqueIdGenerator
    private static final String COUNTER_NAME = "OrderLine";

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderLineBean() {
    }

    // ======================================
    // =          ejbCreate methods         =
    // ======================================
    public String ejbCreate(int quantity, double unitCost, ItemLocal item) throws CreateException, CheckException {
        setId(getUniqueId(COUNTER_NAME));
        setUnitCost(unitCost);
        setQuantity(quantity);
        // Checks data integrity
        checkData();
        return null;
    }

    public void ejbPostCreate(int quantity, double unitCost, ItemLocal item) throws CreateException, CheckException {
        setItem(item);
        // Checks data integrity
        if (getItem() == null)
            throw new CheckException("Invalid item");
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    public void checkData() throws CheckException {
        checkId(getId());
        if (getUnitCost() <= 0)
            throw new CheckException("Invalid unit cost");
        if (getQuantity() <= 0)
            throw new CheckException("Invalid quantity");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public abstract int getQuantity();

    public abstract void setQuantity(final int quantity);

    public abstract double getUnitCost();

    public abstract void setUnitCost(final double unitCost);

    // ======================================
    // =      CMR Getters and Setters       =
    // ======================================
//    public abstract  OrderLocal getOrder() ;

//    public abstract  void setOrder(final OrderLocal order) ;

    public abstract ItemLocal getItem();

    public abstract void setItem(final ItemLocal item);

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("OrderLine{");
        buf.append(",id=").append(getId());
        buf.append(",quantity=").append(getQuantity());
        buf.append(",unitCost=").append(getUnitCost());
        buf.append('}');
        return buf.toString();
    }
}
