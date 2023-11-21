
import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface HelloHome extends EJBHome {

    Hello create(String cle) throws RemoteException, CreateException;

    Hello findByPrimaryKey(String cle) throws RemoteException, FinderException;
}
