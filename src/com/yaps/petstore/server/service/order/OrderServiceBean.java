package com.yaps.petstore.server.service.order;

import com.yaps.petstore.common.dto.OrderDTO;
import com.yaps.petstore.common.dto.OrderLineDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.locator.ejb.ServiceLocator;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.address.AddressLocal;
import com.yaps.petstore.server.domain.address.AddressLocalHome;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocalHome;
import com.yaps.petstore.server.domain.customer.CustomerLocal;
import com.yaps.petstore.server.domain.customer.CustomerLocalHome;
import com.yaps.petstore.server.domain.item.ItemLocal;
import com.yaps.petstore.server.domain.item.ItemLocalHome;
import com.yaps.petstore.server.domain.order.OrderLocal;
import com.yaps.petstore.server.domain.order.OrderLocalHome;
import com.yaps.petstore.server.domain.orderline.OrderLineLocal;
import com.yaps.petstore.server.domain.orderline.OrderLineLocalHome;
import com.yaps.petstore.server.service.AbstractRemoteService;
import com.yaps.petstore.server.service.creditcard.CreditCardServiceLocal;
import com.yaps.petstore.server.service.creditcard.CreditCardServiceLocalHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is a session facade for all order services.
 */
public class OrderServiceBean extends AbstractRemoteService {

    // ======================================
    // =            Constructors            =
    // ======================================
    public OrderServiceBean() {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public String createOrder(final String customerId, final Map shoppingCart) throws CreateException, CheckException {
        final String mname = "createOrder";
        Trace.entering(getCname(), mname, new Object[]{customerId, shoppingCart});

        if (customerId == null || "".equals(customerId))
            throw new CheckException("Invalid id");
        if (shoppingCart == null || shoppingCart.size() < 0)
            throw new CheckException("Shopping Cart is empty");

        // Finds the customer
        final CustomerLocal customer;
        try {
            customer = getCustomerHome().findByPrimaryKey(customerId);
        } catch (FinderException e) {
            throw new CheckException("Customer doesn't exist");
        }

        // Creates the order
        final OrderLocal order = getOrderHome().create(customer.getFirstname(), customer.getLastname(), customer);

        // Sets the address and credit card of the customer
        order.setAddress(customer.getAddress());
        order.setCreditCard(customer.getCreditCard());

        // Checks if the credit card is valid if it hasn't been paid by check
        if (order.getCreditCard() != null)
            getCreditCardService().verifyCreditCard(order.getCreditCard());

        // Creates all the orderLines linked with the order
        final Collection orderLines = new ArrayList();
        final Iterator iterator = shoppingCart.keySet().iterator();
        while (iterator.hasNext()) {
            // Get the NEXT item id in the list
            final String itemId = (String) iterator.next();
            // Get the Value for the returned key
            final Integer quantity = (Integer) shoppingCart.get(itemId);
            // Finds the item
            final ItemLocal item;
            try {
                item = getItemHome().findByPrimaryKey(itemId);
            } catch (FinderException e) {
                throw new CheckException("Item must exist to create an order line");
            }
            // Creates the order line
            final OrderLineLocal orderLine = getOrderLineHome().create(quantity.intValue(), item.getUnitCost(), item);
            // Adds order line to a collection
            orderLines.add(orderLine);
        }
        // Sets orderLines into the order
        order.setOrderLines(orderLines);

        return order.getId();
    }

    public OrderDTO createOrder(final OrderDTO orderDTO) throws CreateException, CheckException {
        final String mname = "createOrder";
        Trace.entering(getCname(), mname, orderDTO);

        if (orderDTO == null)
            throw new CheckException("Order object is null");
        if (orderDTO.getCustomerId() == null || "".equals(orderDTO.getCustomerId()))
            throw new CheckException("Invalid id");
        if (orderDTO.getOrderLines() == null || orderDTO.getOrderLines().size() < 0)
            throw new CheckException("There are no order lines");

        // Finds the customer
        final CustomerLocal customer;
        try {
            customer = getCustomerHome().findByPrimaryKey(orderDTO.getCustomerId());
        } catch (FinderException e) {
            throw new CheckException("Customer must exist to create an order");
        }

        // Creates the order
        final OrderLocal order = getOrderHome().create(orderDTO.getFirstname(), orderDTO.getLastname(), customer);

        // If the address has been set, we create the address
        if (orderDTO.getAddress().isDirty()) {
            final AddressLocal address = getAddressHome().create();
            address.setCity(orderDTO.getCity());
            address.setCountry(orderDTO.getCountry());
            address.setState(orderDTO.getState());
            address.setStreet1(orderDTO.getStreet1());
            address.setStreet2(orderDTO.getStreet2());
            address.setZipcode(orderDTO.getZipcode());
            order.setAddress(address);
        }

        // If the creditcard has been set, we create the credit card
        if (orderDTO.getCreditCard().isDirty()) {
            final CreditCardLocal creditCard = getCreditCardHome().create();
            creditCard.setCreditCardExpiryDate(orderDTO.getCreditCardExpiryDate());
            creditCard.setCreditCardNumber(orderDTO.getCreditCardNumber());
            creditCard.setCreditCardType(orderDTO.getCreditCardType());
            order.setCreditCard(creditCard);

            // Checks if the credit card is valid
            getCreditCardService().verifyCreditCard(order.getCreditCard());
        }

        // Creates all the orderLines linked with the order
        final Collection orderLines = new ArrayList();
        for (Iterator iterator = orderDTO.getOrderLines().iterator(); iterator.hasNext();) {
            final OrderLineDTO orderLineDTO = (OrderLineDTO) iterator.next();
            final ItemLocal item;
            // Finds the item
            try {
                item = getItemHome().findByPrimaryKey(orderLineDTO.getItemId());
            } catch (FinderException e) {
                throw new CheckException("Item must exist to create an order line");
            }
            // Creates the order line
            final OrderLineLocal orderLine = getOrderLineHome().create(orderLineDTO.getQuantity(), orderLineDTO.getUnitCost(), item);
            // Adds order line to a collection
            orderLines.add(orderLine);
        }
        // Sets orderLines into the order
        order.setOrderLines(orderLines);

        // Transforms domain object into DTO
        final OrderDTO result = transformOrder2DTO(order);
        return result;
    }

    public OrderDTO findOrder(final String orderId) throws FinderException, CheckException {
        final String mname = "findOrder";
        Trace.entering(getCname(), mname, orderId);

        if (orderId == null || "".equals(orderId))
            throw new CheckException("Invalid id");

        final OrderLocal order;

        // Finds the object
        order = getOrderHome().findByPrimaryKey(orderId);

        // Transforms domain object into DTO
        final OrderDTO orderDTO = transformOrder2DTO(order);

        Trace.exiting(getCname(), mname, orderDTO);
        return orderDTO;
    }

    public void deleteOrder(final String orderId) throws RemoveException, CheckException {
        final String mname = "deleteOrder";
        Trace.entering(getCname(), mname, orderId);

        if (orderId == null || "".equals(orderId))
            throw new CheckException("Invalid id");

        final OrderLocal order;

        // Checks if the object exists
        try {
            order = getOrderHome().findByPrimaryKey(orderId);
        } catch (FinderException e) {
            throw new CheckException("Order must exist to be deleted");
        }

        // Deletes the object
        order.remove();
    }

    // ======================================
    // =          Private Methods           =
    // ======================================
    private OrderDTO transformOrder2DTO(final OrderLocal order) {
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCity(order.getCity());
        orderDTO.setCountry(order.getCountry());
        orderDTO.setCreditCardExpiryDate(order.getCreditCardExpiryDate());
        orderDTO.setCreditCardNumber(order.getCreditCardNumber());
        orderDTO.setCreditCardType(order.getCreditCardType());
        orderDTO.setCustomerId(order.getCustomer().getId());
        orderDTO.setFirstname(order.getFirstname());
        orderDTO.setLastname(order.getLastname());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setState(order.getState());
        orderDTO.setStreet1(order.getStreet1());
        orderDTO.setStreet2(order.getStreet2());
        orderDTO.setZipcode(order.getZipcode());
        // Transforms all the order lines
        orderDTO.setOrderLines(transformOrderLines2DTOs(order.getOrderLines()));
        return orderDTO;
    }

    private Collection transformOrderLines2DTOs(final Collection orderLines) {
        final Collection orderLinesDTO = new ArrayList();
        OrderLineDTO orderLineDTO;
        for (Iterator iterator = orderLines.iterator(); iterator.hasNext();) {
            final OrderLineLocal orderLine = (OrderLineLocal) iterator.next();
            orderLineDTO = new OrderLineDTO();
            orderLineDTO.setItemId(orderLine.getItem().getId());
            orderLineDTO.setItemName(orderLine.getItem().getName());
            orderLineDTO.setQuantity(orderLine.getQuantity());
            orderLineDTO.setUnitCost(orderLine.getUnitCost());
            orderLinesDTO.add(orderLineDTO);
        }
        return orderLinesDTO;
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

    private CustomerLocalHome getCustomerHome() {
        return (CustomerLocalHome) new ServiceLocator().getLocalHome(CustomerLocalHome.JNDI_NAME);
    }

    private OrderLocalHome getOrderHome() {
        return (OrderLocalHome) new ServiceLocator().getLocalHome(OrderLocalHome.JNDI_NAME);
    }

    private ItemLocalHome getItemHome() {
        return (ItemLocalHome) new ServiceLocator().getLocalHome(ItemLocalHome.JNDI_NAME);
    }

    private OrderLineLocalHome getOrderLineHome() {
        return (OrderLineLocalHome) new ServiceLocator().getLocalHome(OrderLineLocalHome.JNDI_NAME);
    }

    private AddressLocalHome getAddressHome() {
        return (AddressLocalHome) new ServiceLocator().getLocalHome(AddressLocalHome.JNDI_NAME);
    }

    private CreditCardLocalHome getCreditCardHome() {
        return (CreditCardLocalHome) new ServiceLocator().getLocalHome(CreditCardLocalHome.JNDI_NAME);
    }
}
