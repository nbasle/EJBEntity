/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.product;

import java.util.Collection;

import javax.ejb.EJBLocalObject;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.category.CategoryLocal;

/**
 * @author Veronique
 *
 */
public interface ProductLocal extends EJBLocalObject {
	
	// ===================================
	// =		Getters and Setters      =
	// ===================================
	String getId();
	
	String getName();
	
	void setName(final String name);
	
    String getDescription();
    
    void setDescription(final String description);
    
    CategoryLocal getCategory();
    
    void setCategory(final CategoryLocal category);
    
    Collection getItems();
    
    void setItems(final Collection items);
    
    void checkData() throws CheckException;

}
