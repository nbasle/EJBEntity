package com.yaps.petstore.server.domain.creditcard;

import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.PersistentObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.ejb.CreateException;


/**
 * This class encapsulates all the data for a credit card.
 *
 * @see com.yaps.petstore.server.domain.customer.CustomerLocal
 * @see com.yaps.petstore.server.domain.order.OrderLocal
 */
public abstract class CreditCardBean extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    // Used to get a unique id with the UniqueIdGenerator
    private static final String COUNTER_NAME = "CreditCard";

    // For XML
    private static final String XML_CREDITCARD = "CreditCard";
    private static final String XML_CARD_NUMBER = "CardNumber";
    private static final String XML_CARD_TYPE = "CardType";
    private static final String XML_CARD_EXPIRY_DATE = "ExpiryDate";
    private static final String XML_EXPIRY_MONTH = "Month";
    private static final String XML_EXPIRY_YEAR = "Year";

    // ======================================
    // =            Constructors            =
    // ======================================
    public CreditCardBean() {
    }

    // ======================================
    // =          ejbCreate methods         =
    // ======================================
    public String ejbCreate() throws CreateException {
        setId(getUniqueId(COUNTER_NAME));
        return null;
    }

    public void ejbPostCreate() throws CreateException {
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * Returns the XML representation of a credit card. It looks like that
     * <CreditCard>
     * <CardNumber>1213 4654 1321 4562</CardNumber>
     * <CardType>Visa</CardType>
     * <ExpiryDate Month="01" Year="05"/>
     * </CreditCard>
     *
     * @return the XML Document representaing the credit card
     */
    public Document toXML() {
        final String mname = "toXML";
        Trace.entering(getCname(), mname);

        // Creating a new DOM document
        final Document document = DocumentHelper.createDocument();

        // Creates the Root element
        final Element root = document.addElement(XML_CREDITCARD);

        // Adds the element CardNumber
        root.addElement(XML_CARD_NUMBER).addText(getCreditCardNumber());

        // Adds the element CardType
        root.addElement(XML_CARD_TYPE).addText(getCreditCardType());

        // Creates the element CardExpiryDate
        root.addElement(XML_CARD_EXPIRY_DATE).addAttribute(XML_EXPIRY_MONTH, getExpiryMonth()).addAttribute(XML_EXPIRY_YEAR, getExpiryYear());

        Trace.exiting(getCname(), mname);
        return document;
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public abstract String getCreditCardNumber();

    public abstract void setCreditCardNumber(final String creditCardNumber);

    public abstract String getCreditCardType();

    public abstract void setCreditCardType(final String creditCardType);

    public abstract String getCreditCardExpiryDate();

    public abstract void setCreditCardExpiryDate(final String creditCardExpiryDate);

    private String getExpiryMonth() {
        final String dateString = getCreditCardExpiryDate();
        if (dateString != null) {
            // get the slash and return the text before it
            final int slashStart = dateString.indexOf("/");
            if (slashStart != -1) return dateString.substring(0, slashStart);
        }
        return "01";
    }

    private String getExpiryYear() {
        final String dateString = getCreditCardExpiryDate();
        if (dateString != null) {
            // get the slash and return the text after it
            final int slashStart = dateString.indexOf("/");
            if (slashStart != -1) return dateString.substring(slashStart + 1, dateString.length());

        }
        return "50";
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("CreditCard{");
        buf.append("creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append('}');
        return buf.toString();
    }
}
