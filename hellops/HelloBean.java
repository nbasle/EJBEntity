
import javax.ejb.*;
import java.rmi.RemoteException;

public abstract class HelloBean implements EntityBean {

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public abstract String getCle() throws RemoteException;

    public abstract void setCle(String cle) throws RemoteException;

    public abstract String getValeur() throws RemoteException;

    public abstract void setValeur(String valeur) throws RemoteException;

    // ======================================
    // =           EJB Create method        =
    // ======================================

    public String ejbCreate(String cle) throws RemoteException, CreateException {
        setCle(cle);
        return null;
    }

    public void ejbPostCreate(String cle) throws CreateException {
    }

    // ======================================
    // =            EJB callbacks           =
    // ======================================
    public void setEntityContext(EntityContext entityContext) throws EJBException {
    }

    public void unsetEntityContext() throws EJBException {
    }

    public void ejbRemove() throws RemoveException, EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
    }

    public void ejbStore() throws EJBException {
    }
}
