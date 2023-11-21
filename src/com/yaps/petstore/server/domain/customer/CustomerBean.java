package com.yaps.petstore.server.domain.customer;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.address.AddressLocal;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;

import javax.ejb.CreateException;

/**
 * This class represents a customer for the YAPS company.
 */
public abstract class CustomerBean extends PersistentObject {

    // ======================================
    // =            Constructors            =
    // ======================================
    public CustomerBean() {
    }

    // ======================================
    // =          ejbCreate methods         =
    // ======================================
    public String ejbCreate(String id, String firstname, String lastname) throws CreateException, CheckException {
        setId(id);
        setFirstname(firstname);
        setLastname(lastname);
        // Checks data integrity
        checkData();
        return null;
    }

    public void ejbPostCreate(String id, String firstname, String lastname) throws CreateException, CheckException {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Given a password, this method then checks if it matches the user
     *
     * @param password
     * @throws CheckException thrown if the password is empty or different than the one
     *                        store in database
     */
    public void matchPassword(final String password) throws CheckException {
        if (password == null || "".equals(password))
            throw new CheckException("Invalid password");

        // The password entered by the customer is not the same stored in database
        if (!password.equals(getPassword()))
            throw new CheckException("Password doesn't match");
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    public void checkData() throws CheckException {
        checkId(getId());
        if (getFirstname() == null || "".equals(getFirstname()))
            throw new CheckException("Invalid first name");
        if (getLastname() == null || "".equals(getLastname()))
            throw new CheckException("Invalid last name");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public abstract String getFirstname();

    public abstract void setFirstname(final String firstname);

    public abstract String getLastname();

    public abstract void setLastname(final String lastname);

    public abstract String getPassword();

    public abstract void setPassword(final String password);

    public abstract String getTelephone();

    public abstract void setTelephone(final String telephone);

    public abstract String getEmail();

    public abstract void setEmail(final String email);

    // ======================================
    // =      CMR Getters and Setters       =
    // ======================================
    public abstract AddressLocal getAddress();

    public abstract void setAddress(AddressLocal address);

    public abstract CreditCardLocal getCreditCard();

    public abstract void setCreditCard(CreditCardLocal creditCard);

    // ======================================
    // =  Non abstract Getters and Setters  =
    // ======================================
    public String getStreet1() {
        return getAddress() == null ? null : getAddress().getStreet1();
    }

    public String getStreet2() {
        return getAddress() == null ? null : getAddress().getStreet2();
    }

    public String getCity() {
        return getAddress() == null ? null : getAddress().getCity();
    }

    public String getState() {
        return getAddress() == null ? null : getAddress().getState();
    }

    public String getZipcode() {
        return getAddress() == null ? null : getAddress().getZipcode();
    }

    public String getCountry() {
        return getAddress() == null ? null : getAddress().getCountry();
    }

    public String getCreditCardNumber() {
        return getCreditCard() == null ? null : getCreditCard().getCreditCardNumber();
    }

    public String getCreditCardType() {
        return getCreditCard() == null ? null : getCreditCard().getCreditCardType();
    }

    public String getCreditCardExpiryDate() {
        return getCreditCard() == null ? null : getCreditCard().getCreditCardExpiryDate();
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Customer{");
        buf.append("id=").append(getId());
        buf.append(",firstname=").append(getFirstname());
        buf.append(",lastname=").append(getLastname());
        buf.append(",password=").append(getPassword());
        buf.append(",telephone=").append(getTelephone());
        buf.append(",email=").append(getEmail());
        buf.append(",street1=").append(getStreet1());
        buf.append(",street2=").append(getStreet2());
        buf.append(",city=").append(getCity());
        buf.append(",state=").append(getState());
        buf.append(",zipcode=").append(getZipcode());
        buf.append(",country=").append(getCountry());
        buf.append(",creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append('}');
        return buf.toString();
    }
}
