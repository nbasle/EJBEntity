package com.yaps.petstore.common.delegate;

import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.server.cart.ShoppingCart;
import com.yaps.petstore.server.cart.ShoppingCartHome;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

/**
 * This class follows the Delegate design pattern. It's a one to one method
 * with the Cart component class. Each method delegates the call to the
 * Cart class
 */
public final class ShoppingCartDelegate {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static ShoppingCart _shoppingCart;

    // ======================================
    // =      Category Business methods     =
    // ======================================
    public static Map getCart() throws RemoteException {
        return getShoppingCart().getCart();
    }

    /**
     * Delegates the call to the {@link com.yaps.petstore.server.cart.ShoppingCart#addItem(String) addItem} method.
     */
    public static void addItem(final String itemId) throws RemoteException {
        getShoppingCart().addItem(itemId);
    }

    /**
     * Delegates the call to the {@link com.yaps.petstore.server.cart.ShoppingCart#getItems() getItems} method.
     */
    public static Collection getItems() throws RemoteException {
        return getShoppingCart().getItems();
    }

    /**
     * Delegates the call to the {@link com.yaps.petstore.server.cart.ShoppingCart#removeItem(String) removeItem} method.
     */
    public static void removeItem(final String itemId) throws RemoteException {
        getShoppingCart().removeItem(itemId);
    }

    /**
     * Delegates the call to the {@link com.yaps.petstore.server.cart.ShoppingCart#updateItemQuantity(String, int) updateItemQuantity} method.
     */
    public static void updateItemQuantity(final String itemId, final int newQty) throws RemoteException {
        getShoppingCart().updateItemQuantity(itemId, newQty);
    }

    /**
     * Delegates the call to the {@link com.yaps.petstore.server.cart.ShoppingCart#getTotal() getSubTotal} method.
     */
    public static Double getTotal() throws RemoteException {
        return getShoppingCart().getTotal();
    }

    /**
     * Delegates the call to the {@link com.yaps.petstore.server.cart.ShoppingCart#empty() empty} method.
     */
    public static void empty() throws RemoteException {
        getShoppingCart().empty();
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    private static ShoppingCart getShoppingCart() throws RemoteException {
        ShoppingCartHome shoppingCartHome = null;
        if (_shoppingCart == null) {
            try {
                // Looks up for the home interface
                shoppingCartHome = (ShoppingCartHome) ServiceLocator.getInstance().getHome(ShoppingCartHome.JNDI_NAME, ShoppingCartHome.class);
                // Creates the remote interface
                _shoppingCart = shoppingCartHome.create();
            } catch (Exception e) {
                throw new RemoteException("Lookup or Create exception", e);
            }
        }
        return _shoppingCart;
    }
}
