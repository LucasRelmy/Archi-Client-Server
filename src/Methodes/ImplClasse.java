package Methodes;

import ObjetsBdd.ClientBdd;
import ObjetsBdd.ComposantBdd;
import ObjetsBdd.FactureBdd;

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

    @Override
    public boolean AchatComposant(int IDClient,int nb,  String pRef) throws Exception {
        String sqlCompo = "SELECT * FROM composant WHERE Ref = '" + pRef + "'";
        ResultSet resCompo = manager.getData(sqlCompo);
        if(resCompo.next()) {
            if(resCompo.getInt("NbExemplaire") < nb)
            {
                return false;
            }
            else {
                //On update le nombre restant d'exemplaire du composant
                int total = resCompo.getInt("NbExemplaire")-nb;
                String sqlCompoUpdate = "UPDATE composant SET NbExemplaire = '" + total + "' Where Ref = '" + pRef + "'";
                int resUpdateCompo = manager.setData(sqlCompo);

                // on ajoute le prix de l'achat à la facture en cour du client
                String sqlClient = "SELECT * FROM client where ID = " + IDClient ;
                ResultSet resClient = manager.getData(sqlClient);
                float totalFacture = resClient.getInt("TotalFacture")+(nb*resCompo.getFloat("Prix"));

                String sqlUpdateClient = "UPDATE client SET TotalFacture = '" + totalFacture + "' Where ID = '" + IDClient + "'";
                int resUpdateClient = manager.setData(sqlUpdateClient);

                return true;
            }
        }
        return false;
    }

    @Override
    public void PayerFacture(int IDClient) throws Exception {

        //On récupère la facture actuelle du client
        String sqlClient = "SELECT * FROM client where ID = " + IDClient ;
        ResultSet resClient = manager.getData(sqlClient);

        //insertion de la nouvelle facture
        Date date = new Date();
        int result=manager.setData("INSERT INTO facture (Prix, Date, IdClient)" + "VALUES(" + resClient.getInt("TotalFacture") +","+ date + "," + IDClient +")");

        //remise a zero de la facture actuelle du client
        String sqlUpdateClient = "UPDATE client SET TotalFacture = '" + 0 + "' Where ID = '" + IDClient + "'";
        int resUpdateClient = manager.setData(sqlUpdateClient);


    }

    @Override
    public List<FactureBdd> getFactures(int pIDClient) throws Exception {
        List<FactureBdd> liste = new ArrayList<FactureBdd>();

        String sql = "SELECT * FROM facture WHERE IdClient = '" + pIDClient + "'";
        ResultSet res = manager.getData(sql);
        //Extraire des données de ResultSet
        if (res.next()){
            while(res.next()) {
                // Récupérer par nom de colonne
                int id = res.getInt("id");
                float prix = res.getFloat("prix");
                int IDClient = res.getInt("IdClient");
                String date = res.getString("Date");

                // Définir les valeurs
                FactureBdd f = new FactureBdd();
                f.setID(id);
                f.setPrix(prix);
                f.setIDClient(IDClient);
                f.setDate(date);
                liste.add(f);
            }

        }
        res.close();
        return liste;
    }

    @Override
    public void AjoutProduit(String pRef,int nb) throws Exception {
        String sql = "SELECT * FROM composant where Ref = " + pRef ;
        ResultSet res = manager.getData(sql);
        int total = res.getInt("NbExemplaire")+nb;
        String rq = "UPDATE composant SET NbExemplaire = '" + total + "' Where Ref = '" + pRef + "'";
    }

}