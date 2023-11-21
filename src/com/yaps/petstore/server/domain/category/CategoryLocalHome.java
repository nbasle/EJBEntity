/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.category;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.yaps.petstore.common.exception.CheckException;

/**
 * @author Veronique
 *
 */
public interface CategoryLocalHome extends EJBLocalHome {
	
	static final String JNDI_NAME ="ejb/domain/Category";
	
	CategoryLocal create(String id, String name, String description) throws CreateException, CheckException;
	
	Collection findAll() throws FinderException;
	
	CategoryLocal findByPrimaryKey(String id) throws FinderException;

}
