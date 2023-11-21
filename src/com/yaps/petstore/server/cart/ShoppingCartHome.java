package com.yaps.petstore.server.cart;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * This interface gives a remote client the ability to create a reference to ShoppingCart remote interface.
 * Because it extends the EJBHome interface (which extends Remote), every method must throw RemoteException.
 */
public interface ShoppingCartHome extends EJBHome {

    static final String JNDI_NAME = "ejb/stateful/ShoppingCart";

    ShoppingCart create() throws RemoteException, CreateException;
}
