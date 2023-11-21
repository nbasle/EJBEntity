/*
 * Created on 28 janv. 2006
 *
 */
package com.yaps.petstore.server.service.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;

import javax.ejb.FinderException;
import javax.ejb.RemoveException;


import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.locator.ejb.ServiceLocator;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.address.AddressLocal;
import com.yaps.petstore.server.domain.address.AddressLocalHome;

import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocalHome;
import com.yaps.petstore.server.domain.customer.CustomerLocal;
import com.yaps.petstore.server.domain.customer.CustomerLocalHome;
import com.yaps.petstore.server.service.AbstractRemoteService;
import com.yaps.petstore.server.service.creditcard.CreditCardServiceLocal;
import com.yaps.petstore.server.service.creditcard.CreditCardServiceLocalHome;


/**
 * @author Veronique
 *
 */
public class CustomerServiceBean extends AbstractRemoteService {
//	 =======================================
    // =             Constructors          =
    // =====================================
	public CustomerServiceBean() {
    }
	 // ======================================
    // =           Business methods         =
    // ======================================
    public CustomerDTO authenticate(final String customerId, final String password) throws FinderException, CheckException {
        final String mname = "authenticate";
        Trace.entering(getCname(), mname, new Object[]{customerId, password});

        if (customerId == null || "".equals(customerId))
            throw new CheckException("Invalid id");
        if (password == null || "".equals(password))
            throw new CheckException("Invalid password");

        final CustomerLocal customer;

        // Finds the object
        customer= getCustomerHome().findByPrimaryKey(customerId);

        // Check if it's the right password, if not, a CheckException is thrown
        customer.matchPassword(password);

        // Transforms domain object into DTO
        final CustomerDTO customerDTO = transformCustomer2DTO(customer);

        Trace.exiting(getCname(), mname, customerDTO);
        return customerDTO;
    }

    public CustomerDTO createCustomer(final CustomerDTO customerDTO) throws CreateException, CheckException {
        final String mname = "createCustomer";
        Trace.entering(getCname(), mname, customerDTO);

        if (customerDTO == null)
            throw new CheckException("Customer object is null");

        // Transforms DTO into domain object
        final CustomerLocal customer = getCustomerHome().create(customerDTO.getId(), customerDTO.getFirstname(), customerDTO.getLastname());
        customer.setPassword(customerDTO.getPassword());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setEmail(customerDTO.getEmail());

    if (customerDTO.getAddress().isDirty()) {
        final AddressLocal address = getAddressHome().create();
        address.setCity(customerDTO.getCity());
        address.setCountry(customerDTO.getCountry());
        address.setState(customerDTO.getState());
        address.setStreet1(customerDTO.getStreet1());
        address.setStreet2(customerDTO.getStreet2());
        address.setZipcode(customerDTO.getZipcode());
        customer.setAddress(address);
        }
    if (customerDTO.getCreditCard().isDirty()) {
        final CreditCardLocal creditCard = getCreditCardHome().create();
        creditCard.setCreditCardExpiryDate(customerDTO.getCreditCardExpiryDate());
        creditCard.setCreditCardNumber(customerDTO.getCreditCardNumber());
        creditCard.setCreditCardType(customerDTO.getCreditCardType());
        customer.setCreditCard(creditCard);

        // Checks if the credit card is valid
        getCreditCardService().verifyCreditCard(customer.getCreditCard());
    }

        // Transforms domain object into DTO
        final CustomerDTO result = transformCustomer2DTO(customer);

        Trace.exiting(getCname(), mname, result);
        return result;
    }

    public CustomerDTO findCustomer(final String customerId) throws FinderException, CheckException {
        final String mname = "findCustomer";
        Trace.entering(getCname(), mname, customerId);

        if (customerId == null || "".equals(customerId))
            throw new CheckException("Invalid id");

        final CustomerLocal customer;

        // Finds the object
        customer= getCustomerHome().findByPrimaryKey(customerId);

        // Transforms domain object into DTO
        final CustomerDTO customerDTO = transformCustomer2DTO(customer);

        Trace.exiting(getCname(), mname, customerDTO);
        return customerDTO;
    }

    public void deleteCustomer(final String customerId) throws RemoveException, CheckException {
        final String mname = "deleteCustomer";
        Trace.entering(getCname(), mname, customerId);

        if (customerId == null || "".equals(customerId))
            throw new CheckException("Invalid id");

        final CustomerLocal customer;

        // Checks if the object exists
        try {
            customer= getCustomerHome().findByPrimaryKey(customerId);
        } catch (FinderException e) {
            throw new CheckException("Customer must exist to be deleted");
        }

        // Deletes the object
        customer.remove();
    }

    public void updateCustomer(final CustomerDTO customerDTO) throws CheckException {
        final String mname = "updateCustomer";
        Trace.entering(getCname(), mname, customerDTO);

        if (customerDTO == null)
            throw new CheckException("Customer object is null");
        if (customerDTO.getId() == null || "".equals(customerDTO.getId()))
            throw new CheckException("Invalid id");

        final CustomerLocal customer;

        // Checks if the object exists
        try {
            customer= getCustomerHome().findByPrimaryKey(customerDTO.getId());
        } catch (FinderException e) {
            throw new CheckException("Customer must exist to be updated");
        }

        // Transforms DTO into domain object
        customer.setFirstname(customerDTO.getFirstname());
        customer.setLastname(customerDTO.getLastname());
        customer.setPassword(customerDTO.getPassword());
        customer.setTelephone(customerDTO.getTelephone());
        customer.setEmail(customerDTO.getEmail());

        if (customerDTO.getAddress().isDirty()) {
        	try {
                final AddressLocal address = getAddressHome().create();
        	
                address.setCity(customerDTO.getCity());
                address.setCountry(customerDTO.getCountry());
                address.setState(customerDTO.getState());
                address.setStreet1(customerDTO.getStreet1());
                address.setStreet2(customerDTO.getStreet2());
                address.setZipcode(customerDTO.getZipcode());
                customer.setAddress(address);
        	 } catch(CreateException ex) {
        		throw new CheckException("Address must exist to be updated");
        	 }
            }
        if (customerDTO.getCreditCard().isDirty()) {
        	try {
            final CreditCardLocal creditCard = getCreditCardHome().create();
            creditCard.setCreditCardExpiryDate(customerDTO.getCreditCardExpiryDate());
            creditCard.setCreditCardNumber(customerDTO.getCreditCardNumber());
            creditCard.setCreditCardType(customerDTO.getCreditCardType());
            customer.setCreditCard(creditCard);

            // Checks if the credit card is valid
            getCreditCardService().verifyCreditCard(customer.getCreditCard());
        	} catch(CreateException ex) {
        		throw new CheckException("CreditCard must exist to be updated");
        	}
        }
        // Updates the object
        //!!!customer.checkData();
    }

    public Collection findCustomers() throws FinderException {
        final String mname = "findCustomers";
        Trace.entering(getCname(), mname);

        // Finds all the objects
        final Collection customers = getCustomerHome().findAll();

        // Transforms domain objects into DTOs
        final Collection customersDTO = transformCustomers2DTOs(customers);

        Trace.exiting(getCname(), mname, new Integer(customersDTO.size()));
        return customersDTO;
    }

    // ======================================
    // =          Private Methods           =
    // ======================================
    private CustomerDTO transformCustomer2DTO(final CustomerLocal customer) {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setCity(customer.getCity());
        customerDTO.setCountry(customer.getCountry());
        customerDTO.setFirstname(customer.getFirstname());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setLastname(customer.getLastname());
        customerDTO.setState(customer.getState());
        customerDTO.setStreet1(customer.getStreet1());
        customerDTO.setStreet2(customer.getStreet2());
        customerDTO.setTelephone(customer.getTelephone());
        customerDTO.setZipcode(customer.getZipcode());
        customerDTO.setCreditCardNumber(customer.getCreditCardNumber());
        customerDTO.setCreditCardType(customer.getCreditCardType());
        customerDTO.setCreditCardExpiryDate(customer.getCreditCardExpiryDate());
        return customerDTO;
    }

    private Collection transformCustomers2DTOs(final Collection customers) {
        final Collection customersDTO = new ArrayList();
        for (Iterator iterator = customers.iterator(); iterator.hasNext();) {
            final CustomerLocal customer = (CustomerLocal) iterator.next();
            customersDTO.add(transformCustomer2DTO(customer));
        }
        return customersDTO;
    }
    private CustomerLocalHome getCustomerHome() {
        return (CustomerLocalHome) new ServiceLocator().getLocalHome(CustomerLocalHome.JNDI_NAME);
    }
    private AddressLocalHome getAddressHome() {
        return (AddressLocalHome) new ServiceLocator().getLocalHome(AddressLocalHome.JNDI_NAME);
    }

    private CreditCardLocalHome getCreditCardHome() {
        return (CreditCardLocalHome) new ServiceLocator().getLocalHome(CreditCardLocalHome.JNDI_NAME);
    }
    
    private CreditCardServiceLocal getCreditCardService() {
        final String mname = "getCreditCardService";

        CreditCardServiceLocal creditCardServiceLocal = null;
        CreditCardServiceLocalHome creditCardServiceHome ;

        try {
            // Looks up for the home interface
            creditCardServiceHome = (CreditCardServiceLocalHome) new ServiceLocator().getLocalHome(CreditCardServiceLocalHome.JNDI_NAME);
            // Creates the remote interface
            creditCardServiceLocal = creditCardServiceHome.create();
        } catch (javax.ejb.CreateException e) {
            Trace.throwing(getCname(), mname, e);
        }
        return creditCardServiceLocal;
    }
}
