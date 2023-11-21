package com.yaps.petstore.server.service;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * A service is a class that follows the Facade Design Pattern. It gives a set of services
 * to remote or local client. Every service class should extend this class.
 */
public abstract class AbstractRemoteService implements SessionBean {

    // ======================================
    // =             Attributes             =
    // ======================================
    protected SessionContext _sessionContext;

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    protected final String getCname() {
        return _cname;
    }

    // ======================================
    // =            EJB callbacks           =
    // ======================================
    public void ejbCreate() throws CreateException {
    }

    public void setSessionContext(SessionContext sessionContext) throws EJBException {
        _sessionContext = sessionContext;
    }

    public void ejbRemove() throws EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }
}
