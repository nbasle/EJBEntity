package com.yaps.petstore.common.locator.ejb;

import com.yaps.petstore.common.locator.ServiceLocatorException;
import com.yaps.petstore.common.logging.Trace;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import java.net.URL;

/**
 * This class gives EJBs methods to look for resources (like home, remote interfaces...).
 */
public class ServiceLocator {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static String cname = ServiceLocator.class.getName();
    private transient InitialContext ic;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ServiceLocator() throws ServiceLocatorException {
        try {
            ic = new InitialContext();
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * will get the ejb Local home factory.
     * clients need to cast to the type of EJBHome they desire
     *
     * @return the Local EJB Home corresponding to the homeName
     */
    public EJBLocalHome getLocalHome(String jndiHomeName) throws ServiceLocatorException {
        String methodName = "getLocalHome";
        Trace.entering(cname, methodName, jndiHomeName);

        try {
            return (EJBLocalHome) ic.lookup(jndiHomeName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * will get the ejb Remote home factory.
     * clients need to cast to the type of EJBHome they desire
     *
     * @return the EJB Home corresponding to the homeName
     */
    public EJBHome getHome(String jndiHomeName, Class className) throws ServiceLocatorException {
        String methodName = "getHome";
        Object[] params = {jndiHomeName, className};
        Trace.entering(cname, methodName, params);

        try {
            Object objref = ic.lookup(jndiHomeName);
            return (EJBHome) PortableRemoteObject.narrow(objref, className);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return the factory for the factory to get queue connections from
     */
    public QueueConnectionFactory getQueueConnectionFactory(String qConnFactoryName) throws ServiceLocatorException {
        try {
            return (QueueConnectionFactory) ic.lookup(qConnFactoryName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return the Queue Destination to send messages to
     */
    public Queue getQueue(String queueName) throws ServiceLocatorException {
        String methodName = "getQueue";
        Trace.entering(cname, methodName, queueName);

        try {
            return (Queue) ic.lookup(queueName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * This method helps in obtaining the topic factory
     *
     * @return the factory for the factory to get topic connections from
     */
    public TopicConnectionFactory getTopicConnectionFactory(String topicConnFactoryName) throws ServiceLocatorException {
        try {
            return (TopicConnectionFactory) ic.lookup(topicConnFactoryName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * This method obtains the topc itself for a caller
     *
     * @return the Topic Destination to send messages to
     */
    public Topic getTopic(String topicName) throws ServiceLocatorException {
        String methodName = "getTopic";
        Trace.entering(cname, methodName, topicName);

        try {
            return (Topic) ic.lookup(topicName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * This method obtains the datasource itself for a caller
     *
     * @return the DataSource corresponding to the name parameter
     */
    public DataSource getDataSource(String dataSourceName) throws ServiceLocatorException {
        String methodName = "getDataSource";
        Trace.entering(cname, methodName, dataSourceName);

        try {
            return (DataSource) ic.lookup(dataSourceName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return the URL value corresponding
     *         to the env entry name.
     */
    public URL getUrl(String envName) throws ServiceLocatorException {
        try {
            return (URL) ic.lookup(envName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return the boolean value corresponding
     *         to the env entry such as SEND_CONFIRMATION_MAIL property.
     */
    public boolean getBoolean(String envName) throws ServiceLocatorException {
        try {
            return ((Boolean) ic.lookup(envName)).booleanValue();
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return the String value corresponding
     *         to the env entry name.
     */
    public String getString(String envName) throws ServiceLocatorException {
        try {
            return (String) ic.lookup(envName);
        } catch (Exception e) {
            throw new ServiceLocatorException(e);
        }
    }
}
