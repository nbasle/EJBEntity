package com.yaps.petstore.server.domain.creditcard;

import org.dom4j.Document;

import javax.ejb.EJBLocalObject;

public interface CreditCardLocal extends EJBLocalObject {

    // ======================================
    // =           Business methods         =
    // ======================================
    Document toXML();

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    String getCreditCardNumber();

    void setCreditCardNumber(final String creditCardNumber);

    String getCreditCardType();

    void setCreditCardType(final String creditCardType);

    String getCreditCardExpiryDate();

    void setCreditCardExpiryDate(final String creditCardExpiryDate);
}
