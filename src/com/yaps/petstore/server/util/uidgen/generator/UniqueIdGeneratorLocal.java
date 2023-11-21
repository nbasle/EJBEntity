package com.yaps.petstore.server.util.uidgen.generator;

import javax.ejb.EJBLocalObject;

public interface UniqueIdGeneratorLocal extends EJBLocalObject {

    // ======================================
    // =           Business methods         =
    // ======================================
    String getUniqueId(String idPrfix);
}
