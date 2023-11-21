package com.yaps.petstore.server.domain.customer;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.address.AddressLocal;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;

import javax.ejb.EJBLocalObject;

public interface CustomerLocal extends EJBLocalObject {

    // ======================================
    // =           Business methods         =
    // ======================================
    void matchPassword(String password) throws CheckException;

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    String getId();

    String getFirstname();

    void setFirstname(final String firstname);

    String getLastname();

    void setLastname(final String lastname);

    String getPassword();

    void setPassword(final String password);

    String getTelephone();

    void setTelephone(final String telephone);

    String getEmail();

    void setEmail(final String email);

    // ======================================
    // =      CMR Getters and Setters       =
    // ======================================
    AddressLocal getAddress();

    void setAddress(AddressLocal address);

    CreditCardLocal getCreditCard();

    void setCreditCard(CreditCardLocal creditCard);

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