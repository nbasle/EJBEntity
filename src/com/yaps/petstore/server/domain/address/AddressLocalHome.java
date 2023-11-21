/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.address;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.yaps.petstore.common.exception.CheckException;

/**
 * @author Veronique
 *
 */
public interface AddressLocalHome extends EJBLocalHome {
	
static final String JNDI_NAME ="ejb/domain/Address";

AddressLocal create()throws CreateException;

AddressLocal findByPrimaryKey(String id) throws FinderException;

}
