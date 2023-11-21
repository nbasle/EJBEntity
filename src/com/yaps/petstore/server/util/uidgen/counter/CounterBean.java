package com.yaps.petstore.server.util.uidgen.counter;

import javax.ejb.*;

public abstract class CounterBean implements EntityBean {

    // ======================================
    // =            Constructors            =
    // ======================================
    public CounterBean() {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public int getNextValue() {
        final int value = getValue() + 1;
        setValue(value);
        return value;
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public abstract int getValue();

    public abstract void setValue(int value);

    public abstract String getName();

    public abstract void setName(String name);

    // ======================================
    // =           EJB Create method        =
    // ======================================
    public String ejbCreate(String name) throws CreateException {
        setName(name);
        setValue(0);
        return null;
    }

    public void ejbPostCreate(String name) throws CreateException {
    }

    // ======================================
    // =            EJB callbacks           =
    // ======================================
    public void setEntityContext(EntityContext entityContext) throws EJBException {
    }

    public void unsetEntityContext() throws EJBException {
    }

    public void ejbRemove() throws RemoveException, EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
    }

    public void ejbStore() throws EJBException {
    }
}
