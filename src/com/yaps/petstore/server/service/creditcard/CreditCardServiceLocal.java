package com.yaps.petstore.server.service.creditcard;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;

import javax.ejb.EJBLocalObject;

/**
 * This interface gives a local view of the CreditCardBean. Any local client that wants to call
 * a method on the CreditCardBean has to use this interface.
 */
public interface CreditCardServiceLocal extends EJBLocalObject {

    // ======================================
    // =           Business methods         =
    // ======================================
    void verifyCreditCard(CreditCardLocal creditCard) throws CheckException;
}
