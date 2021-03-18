import ObjetsBdd.ClientBdd;
import ObjetsBdd.ComposantBdd;

import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

// Implémenter l'interface de l'objet distante
public class ImplClasse implements RemoteInter {

    MySQLManager manager=MySQLManager.getInstance();

    // Implémenter la méthode de l'interface
    public List<ClientBdd> getClients() throws Exception
    {
        List<ClientBdd> liste = new ArrayList<ClientBdd>();


        String sql = "SELECT * FROM client";
        ResultSet res = manager.getData(sql);
        //Extraire des données de ResultSet
        while(res.next()) {
            // Récupérer par nom de colonne
            int id = res.getInt("id");
            float totalFacture = res.getFloat("TotalFacture");
            int payement = res.getInt("ModePayement");
            String nom = res.getString("nom");
            String adresse = res.getString("Adresse");

            // Définir les valeurs
            ClientBdd c = new ClientBdd();
            c.setID(id);
            c.setTotalFacture(totalFacture);
            c.setNom(nom);
            c.setAdresse(adresse);
            liste.add(c);
        }
        res.close();
        return liste;
    }
    public ComposantBdd FindComposantByRef(String pRef) throws Exception {

        String sql = "SELECT * FROM composant where Ref = " + pRef ;
        ResultSet res = manager.getData(sql);
        ComposantBdd compo = new ComposantBdd();

        while(res.next()) {
            // Récupérer par nom de colonne
            String ref = res.getString("ref");
            int famille = res.getInt("Famille");
            int nbExemplaire = res.getInt("NbExemplaire");
            float prix = res.getFloat("Prix");

            // Définir les valeurs
            compo.setRef(ref);
            compo.setFamille(famille);
            compo.setNbExemplaire(nbExemplaire);
            compo.setPrix(prix);
        }
        res.close();
        return compo;
    }
    public List<ComposantBdd> FindComposantByFamille(int pFamille) throws Exception
    {
        List<ComposantBdd> liste = new ArrayList<ComposantBdd>();

        String sql = "SELECT * FROM composant WHERE famille = " + pFamille + " AND NbExemplaire > 0";
        System.out.println(sql);
        ResultSet res = manager.getData(sql);
        //Extraire des données de ResultSet
        while(res.next()) {
            // Récupérer par nom de colonne
            String ref = res.getString("ref");
            int famille = res.getInt("Famille");
            int nbExemplaire = res.getInt("NbExemplaire");
            float prix = res.getFloat("Prix");

            // Définir les valeurs
            ComposantBdd compo = new ComposantBdd();
            compo.setRef(ref);
            compo.setFamille(famille);
            compo.setNbExemplaire(nbExemplaire);
            compo.setPrix(prix);
            liste.add(compo);
        }
        res.close();
        return liste;
    }
}