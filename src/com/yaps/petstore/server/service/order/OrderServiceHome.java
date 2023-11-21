package com.yaps.petstore.server.service.order;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * This interface gives a remote client the ability to create a reference to OrderService remote interface.
 * Because it extends the EJBHome interface (which extends Remote), every method must throw RemoteException.
 */
public interface OrderServiceHome extends EJBHome {

    static final String JNDI_NAME = "ejb/stateless/OrderService";

    OrderService create() throws RemoteException, CreateException;
}
