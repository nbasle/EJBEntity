package com.yaps.petstore.server.util.uidgen.counter;

import javax.ejb.EJBLocalObject;

public interface CounterLocal extends EJBLocalObject {

    // ======================================
    // =           Business methods         =
    // ======================================
    public int getNextValue();

}

