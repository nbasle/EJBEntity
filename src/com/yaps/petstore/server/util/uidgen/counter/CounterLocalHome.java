package com.yaps.petstore.server.util.uidgen.counter;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface CounterLocalHome extends EJBLocalHome {

    public static final String JNDI_NAME = "ejb/utility/Counter";

    public CounterLocal create(String name) throws CreateException;

    CounterLocal findByPrimaryKey(String key) throws FinderException;
}
