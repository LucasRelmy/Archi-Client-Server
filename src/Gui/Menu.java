package Gui;

import Methodes.RemoteInter;
import ObjetsBdd.ClientBdd;
import ObjetsBdd.ComposantBdd;
import ObjetsBdd.FactureBdd;

import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame {
    private JComboBox<ClientBdd> comboBox1;
    private ClientBdd clientSelect;
    private JTextField textField1;
    private JList<ComposantBdd> list1;
    private JSpinner spinner1;
    private JButton acheterButton;
    private JButton afficherLesFacturesButton;
    private JButton rechercherButton;
	private JPanel MainPanel;
	private JLabel adresseClient;
	private JLabel factureClient;

	public Menu() {
		System.out.println("Menu is built here " + this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(MainPanel);
		setVisible(true);
		pack();
		SetVariables();
		afficherLesFacturesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AfficherFacture(clientSelect.getId());
			}
		});
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
			ComposantBdd compo=stub.FindComposantByRef("'Turbo Resistance 2000Wut'");

			//JLabel componant = new JLabel();
			//componant.setText(compo.getRef());
			//System.out.println(compo.getRef());
			//list1.add( componant );
			//list1.setSelectedIndex(0);
			//System.out.println(list1.getFirstVisibleIndex());

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
				updateClient(clientSelect);
			}
		});
		clientSelect = (ClientBdd) comboBox1.getSelectedItem();
		updateClient(clientSelect);
	}

	private void AfficherFacture(int idCLient){
		try {
			Registry reg = LocateRegistry.getRegistry(null);
			RemoteInter stub = (RemoteInter) reg.lookup("Methodes.RemoteInter");
			List<FactureBdd> factures = (List) stub.getFactures(idCLient);
			//Ouvre une nouvelle fenetre
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Facture frame = new Facture();
						frame.SetFacure(factures);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	private void updateClient(ClientBdd client){
		adresseClient.setText(client.getAdresse());
		factureClient.setText(String.valueOf(client.getTotalFacture()+"€"));
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
