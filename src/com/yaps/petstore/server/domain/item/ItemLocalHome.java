/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.item;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.product.ProductLocal;

/**
 * @author Veronique
 *
 */
public interface ItemLocalHome extends EJBLocalHome {
	
	static final String JNDI_NAME = "ejb/domain/Item";
	
	ItemLocal create (String id, String name, double unitCost, ProductLocal product) throws CreateException, CheckException;
	
	Collection findAll() throws FinderException;
	
	ItemLocal findByPrimaryKey(String id) throws FinderException;

}
