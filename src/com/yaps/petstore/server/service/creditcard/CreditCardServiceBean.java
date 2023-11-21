package com.yaps.petstore.server.service.creditcard;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.creditcard.CreditCardLocal;
import org.dom4j.Document;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * This class verifies a credit card with the barkbank's credit card service.
 *
 * @see com.barkbank.verifier.VerifyCreditCardServlet
 */
public class CreditCardServiceBean implements SessionBean {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // XPath
    private static final String XPATH_VERIFIER_STATUS = "//CreditCard/@Status";

    // ======================================
    // =           Business methods         =
    // ======================================
    public void verifyCreditCard(final CreditCardLocal creditCard) throws CheckException {
        final String mname = "verifyCreditCard";
        Trace.entering(_cname, mname, creditCard);

        // The credit card hasn't been used. The customer has paid by check
        if ((creditCard.getCreditCardType() == null || "".equals(creditCard.getCreditCardType())) &&
                (creditCard.getCreditCardNumber() == null || "".equals(creditCard.getCreditCardNumber())) &&
                (creditCard.getCreditCardExpiryDate() == null || "".equals(creditCard.getCreditCardExpiryDate())))
            return;

        // Sends the XML stream to the servlet that will verify the credit card
        final Document creditCardVerifiedXML = HTTPSender.send(creditCard.toXML());

        // Gets the response from the servlet and analyses the response
        analyseResponse(creditCardVerifiedXML);

        Trace.exiting(_cname, mname);
    }

    // ======================================
    // =           Private methods          =
    // ======================================
    private static void analyseResponse(final Document creditCardVerifiedXML) throws CheckException {

        // Gets the status from the XML document
        final String status = creditCardVerifiedXML.selectSingleNode(XPATH_VERIFIER_STATUS).getText();

        // If the credit card is not 'Valid' an exception is thrown
        if (!"Valid".equals(status))
            throw new CheckException("Invalid Credit Card: " + status);
    }

    // ======================================
    // =            EJB callbacks           =
    // ======================================
    public void ejbCreate() throws CreateException {
    }

    public void setSessionContext(SessionContext sessionContext) throws EJBException {
    }

    public void ejbRemove() throws EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }
}
