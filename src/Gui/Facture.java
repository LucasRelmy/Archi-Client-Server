package Gui;

import ObjetsBdd.FactureBdd;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Facture extends JFrame {
    private JLabel textArea1;
    private JPanel panel1;

    public Facture(){
        setContentPane(panel1);
        setVisible(true);
        pack();
    }

    public void SetFacture(List<FactureBdd> factures) {
        String lesFactures="Mes factures : \n";
        for (FactureBdd f : factures){
            lesFactures += "Facture n°" + f.getID() + " prix : " + f.getPrix() + "€. Date : " + f.getDate() + "\n";

        }
        textArea1.setText(lesFactures);
    }
}
