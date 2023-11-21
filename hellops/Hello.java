
import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Hello extends EJBObject {

    String getCle() throws RemoteException;

    String getValeur() throws RemoteException;

    void setValeur(String valeur) throws RemoteException;
}
