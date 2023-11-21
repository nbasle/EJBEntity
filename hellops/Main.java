
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {

        InitialContext ic = null;

        Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming");

        try {
            ic = new InitialContext(properties);
            Object objRef = (HelloHome) ic.lookup("ejb/Hello");
            HelloHome home = (HelloHome) PortableRemoteObject.narrow(objRef, HelloHome.class);
            // Insere une valeur dans la table
            Hello hello = home.create("Hello");
            hello.setValeur("PetStore!");
            // Recherche cette valeur et affiche le contenu
            hello = home.findByPrimaryKey("Hello");
            System.out.println(hello.getCle());
            System.out.println(hello.getValeur());
            // Modifie les valeurs
            hello.setValeur("PetStore Modifie!");
            // Supprime
            hello.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
