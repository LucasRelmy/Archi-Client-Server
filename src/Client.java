import ObjetsBdd.ClientBdd;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client
{
    private Client(){}

    public static void main(String[] args) throws Exception {
        try {
            // Récupérer le registre
            Registry reg = LocateRegistry.getRegistry(null);
            System.out.println("Registre Récupéré");

            // Recherche dans le registre de l'objet distant
            RemoteInter stub = (RemoteInter) reg.lookup("RemoteInter");
            System.out.println("Objet Distant Récupéré");

            // Appel de la méthode distante à l'aide de l'objet obtenu
            List<ClientBdd> liste = (List)stub.getClients();
            System.out.println("Liste Client Récupérée");

            if(liste.isEmpty()){
                System.out.println("Pas de correspondance");
            }
            else{
                for (ClientBdd c : liste) {
                    System.out.println("ID: " + c.getId());
                    System.out.println("Nom: " + c.getNom());
                    System.out.println("Adresse: " + c.getAdresse());
                    System.out.println("Total Facture: " + c.getTotalFacture());
                    System.out.println("Mode Payement: " + c.getPayement());
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}