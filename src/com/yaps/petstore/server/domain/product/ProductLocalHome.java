/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.product;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.category.CategoryLocal;


/**
 * @author Veronique
 *
 */
public interface ProductLocalHome extends EJBLocalHome {
	
	static final String JNDI_NAME ="ejb/domain/Product";

	ProductLocal create( String id, String name, String description, CategoryLocal category) throws CreateException, CheckException;
	
	Collection findAll() throws FinderException;
	
	ProductLocal findByPrimaryKey(String id) throws FinderException;
}
