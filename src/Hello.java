import java.rmi.Remote;
import java.rmi.RemoteException;

// Créer l'interface de l'objet distant
public interface Hello extends Remote {
    void showMsg() throws RemoteException;
}