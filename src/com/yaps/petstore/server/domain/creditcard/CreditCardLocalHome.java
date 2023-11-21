package com.yaps.petstore.server.domain.creditcard;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface CreditCardLocalHome extends EJBLocalHome {

    static final String JNDI_NAME = "ejb/domain/CreditCard";

    CreditCardLocal create() throws CreateException;

    CreditCardLocal findByPrimaryKey(String id) throws FinderException;
}
