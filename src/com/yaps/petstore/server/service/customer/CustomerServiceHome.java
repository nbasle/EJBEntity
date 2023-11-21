package com.yaps.petstore.server.service.customer;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * This interface gives a remote client the ability to create a reference to CustomerService remote interface.
 * Because it extends the EJBHome interface (which extends Remote), every method must throw RemoteException.
 */
public interface CustomerServiceHome extends EJBHome {

    static final String JNDI_NAME = "ejb/stateless/CustomerService";

    CustomerService create() throws RemoteException, CreateException;
}
