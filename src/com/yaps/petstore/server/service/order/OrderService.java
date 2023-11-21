package com.yaps.petstore.server.service.order;

import com.yaps.petstore.common.dto.OrderDTO;
import com.yaps.petstore.common.exception.CheckException;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * This interface gives a remote view of the OrderServiceBean. Any distant client that wants to call
 * a method on the OrderServiceBean has to use this interface. Because it extends the EJBObject interface
 * (which extends Remote), every method must throw RemoteException.
 */
public interface OrderService extends EJBObject {

    // ======================================
    // =           Business methods         =
    // ======================================

    /**
     * Given a customer id and a shopping card, this method creates a Order. It first gets the
     * customer information, creates an Order domain object, uses the Order object to apply the
     * business rules for creation. It then creates OrderLines using the shopping cart
     * and sends back the OrderId.
     *
     * @param customerId   cannot be null.
     * @param shoppingCart cannot be null.
     * @return the order id
     * @throws CreateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    String createOrder(final String customerId, Map shoppingCart) throws CreateException, CheckException, RemoteException;

    /**
     * Given a OrderDTO object, this method creates a Order. It first transforms
     * a OrderDTO into a Order domain object, uses the Order object to apply the
     * business rules for creation. It then transforms back the Order object into a
     * OrderDTO.
     *
     * @param orderDTO cannot be null.
     * @return the created OrderDTO
     * @throws CreateException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    OrderDTO createOrder(OrderDTO orderDTO) throws CreateException, CheckException, RemoteException;

    /**
     * Given an id this method uses the Order domain object to load all the data of this
     * object. It then transforms this object into a OrderDTO and returns it.
     *
     * @param orderId identifier
     * @return OrderDTO
     * @throws ObjectNotFoundException is thrown if no object with this given id is found
     * @throws FinderException         is thrown if a DomainException is caught
     *                                 or a system failure is occurs
     * @throws CheckException          is thrown if a invalid data is found
     * @throws RemoteException         is thrown if a remote call fails
     */
    OrderDTO findOrder(String orderId) throws FinderException, CheckException, RemoteException;

    /**
     * Given an id, this method finds an Order domain object and then calls its deletion
     * method.
     *
     * @param orderId identifier
     * @throws RemoveException is thrown if a DomainException is caught
     *                         or a system failure is occurs
     * @throws CheckException  is thrown if a invalid data is found
     * @throws RemoteException is thrown if a remote call fails
     */
    void deleteOrder(String orderId) throws RemoveException, CheckException, RemoteException;
}
