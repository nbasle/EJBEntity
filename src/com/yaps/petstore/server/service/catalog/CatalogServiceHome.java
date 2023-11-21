package com.yaps.petstore.server.service.catalog;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * This interface gives a remote client the ability to create a reference to CatalogService remote interface.
 * Because it extends the EJBHome interface (which extends Remote), every method must throw RemoteException.
 */
public interface CatalogServiceHome extends EJBHome {

    static final String JNDI_NAME = "ejb/stateless/CatalogService";

    CatalogService create() throws RemoteException, CreateException;
}
