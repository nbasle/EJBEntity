package com.yaps.petstore.server.service.creditcard;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * This interface gives a local client the ability to create a reference to CatalogService local interface.
 */
public interface CreditCardServiceLocalHome extends EJBLocalHome {

    static final String JNDI_NAME = "ejb/stateless/CreditCardService";

    CreditCardServiceLocal create() throws CreateException;
}
