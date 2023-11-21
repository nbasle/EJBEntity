/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.product;

import java.util.Collection;

import javax.ejb.CreateException;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.category.CategoryLocal;

/**
 * @author Veronique
 *
 */
public abstract class ProductBean extends PersistentObject {
	
	// =====================================
	// =		  Constructors 			   =
	// =====================================
	public ProductBean() {
		
	}
	
	// =====================================
	// =     ejbCreate methods			   =
	// =====================================
	public String ejbCreate(String id, String name, String description, CategoryLocal category) throws CreateException, CheckException
	{ 
		setId(id);
		setName(name);
		setDescription(description);
		
		// Checks data integrity
		checkData();
		return null;
		
	}
	public void ejbPostCreate(String id, String name, String description, CategoryLocal category) throws CreateException, CheckException {
		
		setCategory(category);
//		 Checks data integrity
		if(getCategory() == null)
			throw new CheckException("Invalid category");
	}
	
	
//	 ======================================
    // =          Protected methods         =
    // ======================================
	public void checkData() throws CheckException {
		checkId(getId());
		
		if(getName() == null || "".equals(getName()))
		{
			_entityContext.setRollbackOnly();
			throw new CheckException("Invalid name");
		}
		
		if(getDescription() == null || "".equals(getDescription()))
		{
			_entityContext.setRollbackOnly();
			throw new CheckException("Invalid description");
		}
	}

//	 ======================================
    // =         Getters and Setters        =
    // ======================================
    public abstract String getName();

    public abstract void setName(final String name);
    
    public abstract String getDescription();

    public abstract void setDescription(final String description);
    
    public abstract CategoryLocal getCategory();

    public abstract void setCategory(final CategoryLocal category);
    
    public abstract Collection getItems();
    
    public abstract void setItems(final Collection items);
}
