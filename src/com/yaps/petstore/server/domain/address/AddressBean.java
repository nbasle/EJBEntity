/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.domain.address;

import javax.ejb.CreateException;

import com.yaps.petstore.server.domain.PersistentObject;

/**
 * @author Veronique
 *
 */
public abstract class AddressBean extends PersistentObject {
	
	// ====================================
	// =		Attributes				  =
	// ====================================
	
	// Used to get a unique id with the UniqueIdGenerator
	private static final String COUNTER_NAME = "Address";
	
	// ====================================
	// =		Constructors			  =
	// ====================================
	public AddressBean() {
		
	}

	// =====================================
	// =		ejbCreate methods		   =
	// =====================================
	public String ejbCreate() throws CreateException {
		setId(getUniqueId(COUNTER_NAME));
		// Checks data integrity
		return null;
	}
	
	public void ejbPostCreate() throws CreateException {
		
	}
	

//	 ======================================
	// =	Getters and Setters				=
	// ======================================
	public abstract String getStreet1();
	
	public abstract void setStreet1(final String street1);
	
	public abstract String getStreet2();
	
	public abstract void setStreet2(final String street2);
	
	public abstract String getCity();
	
	public abstract void setCity(final String city);
	
	public abstract String getState();
	
	public abstract void setState(final String state);
	
	public abstract String getZipcode();
	
	public abstract void setZipcode(final String zipcode);
	
	public abstract String getCountry();
	
	public abstract void setCountry(final String country);
	
	public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Address{");
        buf.append("street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",state=").append(getState());
        buf.append(",zipCode=").append(getZipcode());
        buf.append(",country=").append(getCountry());
        buf.append('}');
        return buf.toString();
    }
	
}
