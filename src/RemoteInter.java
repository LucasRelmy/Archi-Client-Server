import ObjetsBdd.ClientBdd;
import ObjetsBdd.ComposantBdd;

import java.rmi.*;
import java.sql.SQLException;
import java.util.*;
// Créer l'interface de l'objet distante
public interface RemoteInter extends Remote {
    public List<ClientBdd> getClients() throws Exception;
    public ComposantBdd FindComposantByRef(String pRef) throws Exception;
    public List<ComposantBdd> FindComposantByFamille(int pFamille) throws Exception;
}