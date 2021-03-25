package Methodes;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
public class Server extends ImplClasse {
    public Server() {}

    public static void main(String args[]) {
        try {
            //int port = 1060;
            //LocateRegistry.createRegistry(port);
            //Ouvrir RMI dans ../methode

            // crée l'objet distant
            ImplClasse obj = new ImplClasse();
            // ici, nous exportons l'objet distant vers le stub
            RemoteInter stub = (RemoteInter) UnicastRemoteObject.exportObject(obj, 0);
            // Liaison de l'objet distant (stub) dans le Registre
            Registry reg = LocateRegistry.getRegistry();
            reg.rebind("Methodes.RemoteInter", stub);
            System.out.println("Le Serveur est prêt...");
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}