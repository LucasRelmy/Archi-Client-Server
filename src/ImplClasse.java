import ObjetsBdd.ClientBdd;

import java.util.*;
import java.sql.ResultSet;

// Implémenter l'interface de l'objet distante
public class ImplClasse implements RemoteInter {

    // Implémenter la méthode de l'interface
    public List<ClientBdd> getClients() throws Exception
    {
        List<ClientBdd> liste = new ArrayList<ClientBdd>();

        MySQLManager manager=MySQLManager.getInstance();

        String sql = "SELECT * FROM client";
        ResultSet res = manager.getData(sql);
        //Extraire des données de ResultSet
        while(res.next()) {
            // Récupérer par nom de colonne
            int id = res.getInt("id");
            int totalFacture = res.getInt("TotalFacture");
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
}