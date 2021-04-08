package Methodes;

import ObjetsBdd.ClientBdd;
import ObjetsBdd.ComposantBdd;
import Gui.Menu;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client
{
    private Client(){}

    public static void main(String[] args) throws Exception {
        try {
            //Test GetClient
            // Récupérer le registre
            Registry reg = LocateRegistry.getRegistry(null);
            System.out.println("Registre Récupéré");

            // Recherche dans le registre de l'objet distant
            RemoteInter stub = (RemoteInter) reg.lookup("Methodes.RemoteInter");
            System.out.println("Objet Distant Récupéré");

            // Appel de la méthode distante à l'aide de l'objet obtenu
            List<ClientBdd> liste = (List)stub.getClients();
            System.out.println("Liste Methodes.Client Récupérée");

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
            //test FindComposantByRef
            ComposantBdd compo=stub.FindComposantByRef("'Turbo Resistance 2000Wut'");
            System.out.println("Element récupéré :" + compo.getRef());

            //Test findComposantByFamille

            // Appel de la méthode distante à l'aide de l'objet obtenu
            List<ComposantBdd> listeCompo = (List)stub.FindComposantByFamille(1);
            System.out.println("Liste Compo Récupérée");

            if(listeCompo.isEmpty()){
                System.out.println("Pas de correspondance");
            }
            else{
                for (ComposantBdd composant : listeCompo) {
                    System.out.println("Ref: " + composant.getRef());
                    System.out.println("Famille: " + composant.getFamille());
                    System.out.println("Prix: " + composant.getPrix());
                    System.out.println("Total NbExemplaire: " + composant.getNbExemplaire());
                }
            }

            //On lance le menu
            //Menu monMenu = new Menu();
            //monMenu.start();

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}