package com.yaps.petstore.server.domain.order;

import com.yaps.petstore.server.domain.customer.CustomerLocal;
import com.yaps.petstore.common.exception.CheckException;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;


public interface OrderLocalHome extends EJBLocalHome {

    static final String JNDI_NAME = "ejb/domain/Order";

    OrderLocal create(String firstname, String lastname, CustomerLocal customer) throws CreateException, CheckException ;

    OrderLocal findByPrimaryKey(String id) throws FinderException;
}
