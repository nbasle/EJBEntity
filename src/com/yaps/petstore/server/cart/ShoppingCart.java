package com.yaps.petstore.server.cart;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

/**
 * This interface gives a remote view of the ShoppingCartBean. Any distant client that wants to call
 * a method on the ShoppingCartBean has to use this interface. Because it extends the EJBObject interface
 * (which extends Remote), every method must throw RemoteException.
 */
public interface ShoppingCart extends EJBObject {

    // ======================================
    // =           Business methods         =
    // ======================================

    /**
     * This method returns the shopping cart. The shopping cart is represented as a Map (key, value)
     * where item ids and quantities are stored.
     *
     * @return the shopping cart
     * @throws RemoteException
     */
    Map getCart() throws RemoteException;

    /**
     * This method returns a collection of ShoppingCartDTO. It uses the item id that is stored
     * in the shopping cart to get all item information (id, name, product, quantity, cost).
     *
     * @return a collection of ShoppingCartDTO
     * @throws RemoteException
     */
    Collection getItems() throws RemoteException;

    /**
     * This method adds an item to the shopping cart with a quantity equals to one.
     *
     * @param itemId
     * @throws RemoteException
     */
    void addItem(String itemId) throws RemoteException;

    /**
     * This method removes an item from the shopping cart.
     *
     * @param itemId
     * @throws RemoteException
     */
    void removeItem(String itemId) throws RemoteException;

    /**
     * This method updates the quantity of a given item in the shopping cart. If the quantity is
     * equal to zero, the item is removed.
     *
     * @param itemId
     * @param newQty
     * @throws RemoteException
     */
    void updateItemQuantity(String itemId, int newQty) throws RemoteException;

    /**
     * This method computes the total amount in the shopping cart by multiplying all items
     * by their quantity.
     *
     * @return
     * @throws RemoteException
     */
    Double getTotal() throws RemoteException;

    /**
     * This method empties the shopping cart.
     *
     * @throws RemoteException
     */
    void empty() throws RemoteException;
}
