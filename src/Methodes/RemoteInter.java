package Methodes;

import ObjetsBdd.ClientBdd;
import ObjetsBdd.ComposantBdd;
import ObjetsBdd.FactureBdd;

import java.rmi.Remote;
import java.util.List;
// Créer l'interface de l'objet distante
public interface RemoteInter extends Remote {
    public List<ClientBdd> getClients() throws Exception;
    public ComposantBdd FindComposantByRef(String pRef) throws Exception;
    public List<ComposantBdd> FindComposantByFamille(int pFamille) throws Exception;
    public boolean AchatComposant(int IDClient,int nb,String pRef) throws Exception;
    public void PayerFacture(int IDClient) throws Exception;
    public List<FactureBdd> getFactures(int IDClient) throws Exception;
    public void AjoutProduit(String pRef,int nb)throws Exception;
}