/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.item;

import javax.ejb.CreateException;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.product.ProductLocal;

/**
 * @author Veronique
 *
 */
public abstract class ItemBean extends PersistentObject {
	
	// ====================================
	// = 		Constructors			  =
	// ====================================
	public ItemBean() {
		
	}
    
	// ====================================
	// =		ejbCreate methods 		  =
	// ====================================
	public String ejbCreate (String id, String name, double unitCost, ProductLocal product) throws CreateException, CheckException
	{
		setId(id);
		setName(name);
		setUnitCost(unitCost);
		
		//Check data integrity
		checkData();
		return null;
	}
	public void ejbPostCreate (String id, String name, double unitCost, ProductLocal product) throws CreateException, CheckException
	{
		setProduct(product);
		
		// Checks data integrity
		if(getProduct() == null)
			throw new CheckException("Invalid product");
	}
	// =========================================
	// = 		Protected methods			   =
	// ========================================
	public void checkData() throws CheckException {
		checkId(getId());
		if (getName() == null || "".equals(getName()))
		{
			_entityContext.setRollbackOnly();
			throw new CheckException("Invalid Name");
		}
		if (getUnitCost() <= 0)
		{
			_entityContext.setRollbackOnly();
            throw new CheckException("Invalid unit cost");
		}
        
	}

	// ================================
	// =		Getters and Setters   =
	// ================================
	public abstract String getName();
	
	public abstract void setName(final String name);
	
	public abstract double getUnitCost();
	
	public abstract void setUnitCost(final double unitCost);
	
	public abstract ProductLocal getProduct();
	
	public abstract void setProduct(final ProductLocal product);
	
	public abstract String getImagePath();
	
	public abstract void setImagePath(final String imagePath);

}
