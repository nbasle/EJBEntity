/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.category;

import java.util.Collection;

import javax.ejb.EJBLocalObject;

import com.yaps.petstore.common.exception.CheckException;



/**
 * @author Veronique
 *
 */
public interface CategoryLocal extends EJBLocalObject {
	// ===============================
	// =		Buisiness methods
	// ===============================
	
	// ===============================
	// =		Getters and Setters
	// ===============================
	String getId();
	
	String getName();
	
	void setName(final String name);
	
	String getDescription();
	
	void setDescription(final String name);
	
	Collection getProducts();

	void setProducts(final Collection products);
	
	void checkData() throws CheckException;

}
