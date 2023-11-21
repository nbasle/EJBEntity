/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.item;

import javax.ejb.EJBLocalObject;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.product.ProductLocal;

/**
 * @author Veronique
 *
 */
public interface ItemLocal extends EJBLocalObject {
	// ======================================
	// = 		Getters and Setters		    =
	// ======================================
	String getId();
	
	String getName();
	
	void setName(final String name);
	
	double getUnitCost();
	
	void setUnitCost(final double unitCost);
	
	public String getImagePath();
	
	public void setImagePath(final String imagePath);
	
	public ProductLocal getProduct();
	
	public void setProduct(final ProductLocal product);

	void checkData() throws CheckException;
}
