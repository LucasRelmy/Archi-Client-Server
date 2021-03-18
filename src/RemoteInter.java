import ObjetsBdd.ClientBdd;

import java.rmi.*;
import java.util.*;
// Créer l'interface de l'objet distante
public interface RemoteInter extends Remote {
    public List<ClientBdd> getClients() throws Exception;
}