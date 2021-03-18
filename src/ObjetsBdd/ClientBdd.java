package ObjetsBdd;
import java.io.Serializable;


public class ClientBdd implements Serializable{
    private int id,payement;
    private String nom, adresse;
    float totalFacture;

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getAdresse() {
        return adresse;
    }
    public float getTotalFacture() {
        return totalFacture;
    }
    public int getPayement() {
        return payement;
    }
    public void setID(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setAdresse(String addresse) {
        this.adresse = addresse;
    }
    public void setTotalFacture(float totalFacture) {
        this.totalFacture = totalFacture;
    }
    public void setPayement(int payement) {
        this.payement = payement;
    }
}

