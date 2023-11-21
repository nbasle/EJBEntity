/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.category;

import java.util.Collection;

import javax.ejb.CreateException;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;

/**
 * @author Veronique
 *
 */
public abstract class CategoryBean extends PersistentObject {
	
	// ===================================
	// =	Constructors				 =
	// ===================================
	public CategoryBean() {
		
	}
	public String ejbCreate(String id, String name, String description)throws CreateException, CheckException {
		//setId(getUniqueId(id));
		setId(id);
		setName(name);
		setDescription(description);
		
		// Checks data integrity
		checkData();
		return null;
	}
	public void ejbPostCreate (String id,  String name, String description)throws CreateException, CheckException {
		
	}
	// =======================================
	// = 		Protected methods            =
	// =======================================
	public void checkData() throws CheckException {
		checkId(getId());
    if (getName() == null || "".equals(getName()))
    {
    	_entityContext.setRollbackOnly();
	throw new CheckException("Invalid name");
    }
    
    if(getDescription()== null || "".equals(getDescription()))
    {
    	_entityContext.setRollbackOnly();
    	throw new CheckException("Invalid description");
    }
	}
	

	// ===================================
	// = 	Getters and Setters			 =
	// ===================================
	public abstract String getName();
	
	public abstract void setName(final String name);
	
	public abstract String getDescription();
	
	public abstract void setDescription(final String description);	
	
	public abstract Collection getProducts();

	public abstract void setProducts(final Collection products);
		
	public String toString() {
		final StringBuffer buf = new StringBuffer();
		buf.append("Category{");
		buf.append("id=").append(getId());
		buf.append(", name=").append(getName());
		buf.append(", description=").append(getDescription());
		buf.append('}');
		return buf.toString();
	}
}
