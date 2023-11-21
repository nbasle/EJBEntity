package com.yaps.petstore.server.domain.order;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.address.AddressLocal;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;
import com.yaps.petstore.server.domain.customer.CustomerLocal;

import javax.ejb.CreateException;
import java.util.Collection;
import java.util.Date;

/**
 * An order represents the items that a customer buys. This order has several
 * order items and is relevant for one customer. The order has address information
 * like the street, the city, the country... This is because a customer can order
 * a pet and wants it delivered at another adress. By default, the order address
 * is the same that the customer's one but it can be changed.
 *
 * @see CustomerLocal
 */
public abstract class OrderBean extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used to get a unique id with the UniqueIdGenerator
    private static final String COUNTER_NAME = "Order";

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderBean() {
    }

    // ======================================
    // =          ejbCreate methods         =
    // ======================================
    public String ejbCreate(String firstname, String lastname, CustomerLocal customer) throws CreateException, CheckException {
        setId(getUniqueId(COUNTER_NAME));
        setOrderDate(new Date());
        setFirstname(firstname);
        setLastname(lastname);
        // Checks data integrity
        checkData();
        return null;
    }

    public void ejbPostCreate(String firstname, String lastname, CustomerLocal customer) throws CreateException, CheckException {
        setCustomer(customer);
        // Checks data integrity
        if (getCustomer() == null)
            throw new CheckException("Invalid customer");
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    public void checkData() throws CheckException {
        checkId(getId());
        if (getFirstname() == null || "".equals(getFirstname()))
            throw new CheckException("Invalid first name");
        if (getLastname() == null || "".equals(getLastname()))
            throw new CheckException("Invalid last name");
//  TODO      if (getCity() == null || "".equals(getCity()))
//            throw new CheckException("Invalid city");
//        if (getCountry() == null || "".equals(getCountry()))
//            throw new CheckException("Invalid country");
//        if (getStreet1() == null || "".equals(getStreet1()))
//            throw new CheckException("Invalid street");
//        if (getZipcode() == null || "".equals(getZipcode()))
//            throw new CheckException("Invalid zipcode");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public abstract Date getOrderDate();

    public abstract void setOrderDate(final Date orderDate);

    public abstract String getFirstname();

    public abstract void setFirstname(final String firstname);

    public abstract String getLastname();

    public abstract void setLastname(final String lastname);

    // ======================================
    // =      CMR Getters and Setters       =
    // ======================================
    public abstract AddressLocal getAddress();

    public abstract void setAddress(final AddressLocal address);

    public abstract CreditCardLocal getCreditCard();

    public abstract void setCreditCard(final CreditCardLocal creditCard);

    public abstract CustomerLocal getCustomer();

    public abstract void setCustomer(final CustomerLocal customer);

    public abstract Collection getOrderLines();

    public abstract void setOrderLines(final Collection orderLines);

    // ======================================
    // =  Non abstract Getters and Setters  =
    // ======================================
    public String getStreet1() {
        return getAddress() == null ? null : getAddress().getStreet1();
    }

    public String getStreet2() {
        return getAddress() == null ? null : getAddress().getStreet2();
    }

    public String getCity() {
        return getAddress() == null ? null : getAddress().getCity();
    }

    public String getState() {
        return getAddress() == null ? null : getAddress().getState();
    }

    public String getZipcode() {
        return getAddress() == null ? null : getAddress().getZipcode();
    }

    public String getCountry() {
        return getAddress() == null ? null : getAddress().getCountry();
    }

    public String getCreditCardNumber() {
        return getCreditCard() == null ? null : getCreditCard().getCreditCardNumber();
    }

    public String getCreditCardType() {
        return getCreditCard() == null ? null : getCreditCard().getCreditCardType();
    }

    public String getCreditCardExpiryDate() {
        return getCreditCard() == null ? null : getCreditCard().getCreditCardExpiryDate();
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Order{");
        buf.append("id=").append(getId());
        buf.append(",orderDate=").append(getOrderDate());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",state=").append(getState());
        buf.append(",zipcode=").append(getZipcode());
        buf.append(",country=").append(getCountry());
        buf.append(",creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append('}');
        return buf.toString();
    }
}
