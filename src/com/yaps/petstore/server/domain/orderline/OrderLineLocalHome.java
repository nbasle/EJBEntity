package com.yaps.petstore.server.domain.orderline;

import com.yaps.petstore.server.domain.item.ItemLocal;
import com.yaps.petstore.common.exception.CheckException;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;


public interface OrderLineLocalHome extends EJBLocalHome {

    static final String JNDI_NAME = "ejb/domain/OrderLine";

    OrderLineLocal create(int quantity, double unitCost, ItemLocal item) throws CreateException, CheckException ;

    OrderLineLocal findByPrimaryKey(String id) throws FinderException;
}
