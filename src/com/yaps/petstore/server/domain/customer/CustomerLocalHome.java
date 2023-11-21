package com.yaps.petstore.server.domain.customer;

import com.yaps.petstore.common.exception.CheckException;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

public interface CustomerLocalHome extends EJBLocalHome {

    static final String JNDI_NAME = "ejb/domain/Customer";

    CustomerLocal create(String id, String firstname, String lastname) throws CreateException, CheckException ;

    Collection findAll() throws FinderException;

    CustomerLocal findByPrimaryKey(String id) throws FinderException;
}
