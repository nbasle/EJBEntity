package com.yaps.petstore.server.util.uidgen.generator;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface UniqueIdGeneratorLocalHome extends EJBLocalHome {

    public static final String JNDI_NAME = "ejb/utility/UniqueIdGenerator";

    UniqueIdGeneratorLocal create() throws CreateException;
}