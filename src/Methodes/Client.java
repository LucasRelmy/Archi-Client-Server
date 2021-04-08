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


            //On lance le menu
            //Menu monMenu = new Menu();
            //monMenu.start();

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}