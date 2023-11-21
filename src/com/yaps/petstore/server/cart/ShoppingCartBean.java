package com.yaps.petstore.server.cart;

import com.yaps.petstore.common.dto.ItemDTO;
import com.yaps.petstore.common.dto.ShoppingCartItemDTO;
import com.yaps.petstore.common.locator.ejb.ServiceLocator;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.service.catalog.CatalogService;
import com.yaps.petstore.server.service.catalog.CatalogServiceHome;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.util.*;

/**
 * This class is a shopping cart. It is a statefull session bean meaning that it keeps its state.
 * Every customer who logs in the Web application can buy several items. These items are then stored
 * in this stateful bean that represent a shopping cart.
 * Its state is represented by a Map (key, value) that stores item ids and quantity. It then gives
 * several method to update the shopping cart (remove, add items and update there quantity).
 */
public class ShoppingCartBean implements SessionBean {

    // ======================================
    // =             Attributes             =
    // ======================================
    private Map _shoppingCart;

    // Used for logging
    private final transient String _cname = this.getClass().getName();
    protected SessionContext _sessionContext;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ShoppingCartBean() {
        _shoppingCart = new HashMap();
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    public Map getCart() {
        return _shoppingCart;
    }

    public Collection getItems() {
        final String mname = "getItems";
        Trace.entering(getCname(), mname);

        final Map map = getCart();
        final ArrayList items = new ArrayList();
        final Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            final String itemId = (String) it.next();
            final Integer quantity = (Integer) map.get(itemId);
            ItemDTO itemDTO ;
            try {
                itemDTO = getCatalogService().findItem(itemId);
                // convert catalog item to cart item
                final ShoppingCartItemDTO ci = new ShoppingCartItemDTO(itemDTO.getId(), itemDTO.getName(), itemDTO.getProductDescription(), quantity.intValue(), itemDTO.getUnitCost());
                items.add(ci);
            } catch (Exception e) {
                Trace.throwing(getCname(), mname, e);
            }
        }

        Trace.exiting(getCname(), mname, new Integer(items.size()));
        return items;
    }

    public void addItem(final String itemId) {
        final String mname = "addItem";
        Trace.entering(getCname(), mname, itemId);

        _shoppingCart.put(itemId, new Integer(1));

        Trace.exiting(getCname(), mname, new Integer(_shoppingCart.size()));
    }

    public void addItem(final String itemId, final int qty) {
        final String mname = "addItem";
        Trace.entering(getCname(), mname, new Object[]{itemId, new Integer(qty)});

        _shoppingCart.put(itemId, new Integer(qty));

        Trace.exiting(getCname(), mname, new Integer(_shoppingCart.size()));
    }

    public void removeItem(final String itemId) {
        final String mname = "removeItem";
        Trace.entering(getCname(), mname, itemId);

        _shoppingCart.remove(itemId);

        Trace.exiting(getCname(), mname, new Integer(_shoppingCart.size()));
    }

    public void updateItemQuantity(final String itemId, final int newQty) {
        final String mname = "updateItemQuantity";
        Trace.entering(getCname(), mname, new Object[]{itemId, new Integer(newQty)});

        _shoppingCart.remove(itemId);
        // remove item if it is less than or equal to 0
        if (newQty > 0) _shoppingCart.put(itemId, new Integer(newQty));

        Trace.exiting(getCname(), mname, new Integer(_shoppingCart.size()));
    }

    public Double getTotal() {
        final String mname = "getTotal";
        Trace.entering(getCname(), mname);

        final Collection items = getItems();
        if (items == null) return null;
        double total = 0.0d;
        // total up the quantities
        for (Iterator it = items.iterator(); it.hasNext();) {
            final ShoppingCartItemDTO i = (ShoppingCartItemDTO) it.next();
            total += (i.getUnitCost() * i.getQuantity());
        }

        Trace.exiting(getCname(), mname, new Double(total));
        return new Double(total);
    }

    public void empty() {
        final String mname = "empty";
        Trace.entering(getCname(), mname);

        _shoppingCart.clear();
    }

    // ======================================
    // =            EJB callbacks           =
    // ======================================
    public void ejbCreate() throws CreateException {
        final String mname = "ejbCreate";
        Trace.entering(getCname(), mname);

        _shoppingCart = new HashMap();
    }

    public void setSessionContext(SessionContext sessionContext) throws EJBException {
        _sessionContext = sessionContext;
    }

    public void ejbRemove() throws EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    // ======================================
    // =            Private methods         =
    // ======================================
    private String getCname() {
        return _cname;
    }

    private CatalogService getCatalogService() throws RemoteException {
        final String mname = "getCatalogService";

        CatalogServiceHome catalogServiceHome ;
        CatalogService catalogServiceRemote = null;
        try {
            // Looks up for the home interface
            catalogServiceHome = (CatalogServiceHome) new ServiceLocator().getHome(CatalogServiceHome.JNDI_NAME, CatalogServiceHome.class);
            // Creates the remote interface
            catalogServiceRemote = catalogServiceHome.create();
        } catch (CreateException e) {
            Trace.throwing(_cname, mname, e);
        }
        return catalogServiceRemote;
    }
}
