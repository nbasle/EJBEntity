package com.yaps.petstore.server.util.uidgen.generator;

import com.yaps.petstore.common.locator.ServiceLocatorException;
import com.yaps.petstore.common.locator.ejb.ServiceLocator;
import com.yaps.petstore.server.util.uidgen.counter.CounterLocal;
import com.yaps.petstore.server.util.uidgen.counter.CounterLocalHome;

import javax.ejb.*;

/**
 * This class manages unique IDs for Database.
 * It follows the Singleton design pattern.
 */
public class UniqueIdGeneratorBean implements SessionBean {

    // ======================================
    // =             Attributes             =
    // ======================================
    private CounterLocalHome counterLocalHome;

    // ======================================
    // =            Constructors            =
    // ======================================
    public UniqueIdGeneratorBean() {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public String getUniqueId(final String idPrefix) {
        return String.valueOf(getCounter(idPrefix).getNextValue());
    }

    // ======================================
    // =           EJB Create method        =
    // ======================================
    public void ejbCreate() {
        try {
            counterLocalHome = (CounterLocalHome) new ServiceLocator().getLocalHome(CounterLocalHome.JNDI_NAME);
        } catch (ServiceLocatorException e) {
            throw new EJBException("UniqueIdGenerator Got naming exception! " + e.getMessage());
        }
    }

    // ======================================
    // =           Private methods          =
    // ======================================
    private CounterLocal getCounter(final String name) {
        try {
            CounterLocal counter ;
            try {
                counter = counterLocalHome.findByPrimaryKey(name);
            } catch (FinderException fe) {
                counter = counterLocalHome.create(name);
            }
            return counter;
        } catch (CreateException ce) {
            throw new EJBException("Could not create counter " + name + ". Error: " + ce.getMessage());
        }
    }

    // ======================================
    // =            EJB callbacks           =
    // ======================================
    public void setSessionContext(SessionContext sessionContext) throws EJBException {
    }

    public void ejbRemove() throws EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }
}
