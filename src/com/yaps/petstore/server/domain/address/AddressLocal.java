/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.address;

import javax.ejb.EJBLocalObject;

/**
 * @author Veronique
 *
 */
public interface AddressLocal extends EJBLocalObject {
	
	// ======================================
	// =	Getters and Setters				=
	// ======================================
	String getStreet1();
	
	void setStreet1(final String street1);
	
	String getStreet2();
	
	void setStreet2(final String street2);
	
	String getCity();
	
	void setCity(final String city);
	
	String getState();
	
	void setState(final String state);
	
	String getZipcode();
	
	void setZipcode(final String zipcode);
	
	String getCountry();
	
	void setCountry(final String country);
}
