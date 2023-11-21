package com.yaps.petstore.server.domain.order;

import com.yaps.petstore.server.domain.address.AddressLocal;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;
import com.yaps.petstore.server.domain.customer.CustomerLocal;

import javax.ejb.EJBLocalObject;
import java.util.Collection;
import java.util.Date;

public interface OrderLocal extends EJBLocalObject {

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    String getId();

    Date getOrderDate();

    void setOrderDate(final Date orderDate);

    String getFirstname();

    void setFirstname(final String firstname);

    String getLastname();

    void setLastname(final String lastname);

    // ======================================
    // =      CMR Getters and Setters       =
    // ======================================
    AddressLocal getAddress();

    void setAddress(final AddressLocal address);

    CreditCardLocal getCreditCard();

    void setCreditCard(final CreditCardLocal creditCard);

    CustomerLocal getCustomer();

    void setCustomer(final CustomerLocal customer);

    Collection getOrderLines();

    void setOrderLines(final Collection orderLines);

    // ======================================
    // =  Non abstract Getters and Setters  =
    // ======================================
    String getStreet1();

    String getStreet2();

    String getCity();

    String getState();

    String getZipcode();

    String getCountry();

    String getCreditCardNumber();

    String getCreditCardType();

    String getCreditCardExpiryDate();
}
