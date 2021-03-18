package ObjetsBdd;

import java.io.Serializable;

public class ComposantBdd implements Serializable {
    int nbExemplaire,famille;
    float prix;
    String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getNbExemplaire() {
        return nbExemplaire;
    }

    public void setNbExemplaire(int nbExemplaire) {
        this.nbExemplaire = nbExemplaire;
    }

    public int getFamille() {
        return famille;
    }

    public void setFamille(int famille) {
        this.famille = famille;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
