package Gui;

import Methodes.RemoteInter;
import ObjetsBdd.ClientBdd;

import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame {
    private JComboBox<ClientBdd> comboBox1;
    private ClientBdd clientSelect;
    private JTextField textField1;
    private JList list1;
    private JSpinner spinner1;
    private JButton acheterButton;
    private JButton afficherLesFacturesButton;
    private JButton rechercherButton;
	private JPanel MainPanel;

	public Menu() {
		System.out.println("Menu is built here " + this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(MainPanel);
		setVisible(true);
		pack();
		SetVariables();
	}

	private void SetVariables(){
		try {
			// Récupérer le registre
			Registry reg = LocateRegistry.getRegistry(null);
			System.out.println("Registre Récupéré");

			// Recherche dans le registre de l'objet distant
			RemoteInter stub = (RemoteInter) reg.lookup("Methodes.RemoteInter");
			System.out.println("Objet Distant Récupéré");

			// Appel de la méthode distante à l'aide de l'objet obtenu
			List<ClientBdd> liste = (List) stub.getClients();
			System.out.println("Liste Methodes.Client Récupérée");

			//Set les variables
			SetCompteClient(liste);

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public void SetCompteClient(List<ClientBdd> Comptes){
		comboBox1.setModel(new DefaultComboBoxModel());
		if (!Comptes.isEmpty()) {
			for (ClientBdd c : Comptes) {
				System.out.println("nom " + c.getNom());
				comboBox1.addItem(c);
				System.out.println(comboBox1.getItemCount());
			}
		}
		comboBox1.setVisible(true);

		comboBox1.setSelectedIndex(0);
		comboBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> comboComptes = (JComboBox)e.getSource();
				System.out.println((ClientBdd)comboComptes.getSelectedItem());
				clientSelect = (ClientBdd)comboComptes.getSelectedItem();
			}
		});
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
