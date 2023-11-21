package com.yaps.petstore.web.servlet.cart;

import com.yaps.petstore.common.delegate.ShoppingCartDelegate;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This servlet updates the quantity for an item.
 */
public class UpdateCartServlet extends AbstractServlet {

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(getCname(), mname);

        final String itemId;
        final int qty;

        try {
            // Gets the parameters from the request
            itemId = request.getParameter("itemId");
            qty = new Integer(request.getParameter("quantity")).intValue();

            // Updates the Shopping Cart
            ShoppingCartDelegate.updateItemQuantity(itemId, qty);

            getServletContext().getRequestDispatcher("/viewcart").forward(request, response);

        } catch (RemoteException e) {
            Trace.throwing(getCname(), mname, e);
            getServletContext().getRequestDispatcher("/error.jsp?exception=Cannot update the shopping cart").forward(request, response);
        }
    }
}
