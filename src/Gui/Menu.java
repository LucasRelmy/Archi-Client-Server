package Gui;

import Methodes.RemoteInter;
import ObjetsBdd.ClientBdd;
import ObjetsBdd.ComposantBdd;
import ObjetsBdd.FactureBdd;
import ObjetsBdd.FamilleBdd;

import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
	private JSpinner spinner1;
    private JButton acheterButton;
    private JButton afficherLesFacturesButton;
    private JButton rechercherButton;
	private JPanel MainPanel;
	private JLabel adresseClient;
	private JLabel factureClient;
	private JComboBox<ComposantBdd> comboBox2;
	private JComboBox<FamilleBdd> comboBox3;
    private JLabel LabelP;
    private JLabel LabelQ;
    private JLabel Vente;

    // Récupérer le registre
	Registry reg = LocateRegistry.getRegistry(null);

	// Recherche dans le registre de l'objet distant
	RemoteInter stub = (RemoteInter) reg.lookup("Methodes.RemoteInter");

	public Menu() throws RemoteException, NotBoundException {
		Vente.setText("");
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
		//Recherche par REF
		rechercherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                comboBox2.removeAllItems();
                ComposantBdd c = null;
				try {
					c = stub.FindComposantByRef(textField1.getText());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				comboBox2.setModel(new DefaultComboBoxModel());

				comboBox2.addItem(c);

				comboBox2.setVisible(true);

			}
		});
		//Recherche par Famille
		comboBox3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    comboBox2.removeAllItems();
				try {
					List<ComposantBdd> liste = stub.FindComposantByFamille(comboBox3.getSelectedIndex()+1);
					if (!liste.isEmpty()) {
						for (ComposantBdd c : liste) {
							System.out.println(c.getRef());
							comboBox2.addItem(c);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				comboBox2.setVisible(true);
			}
		});

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
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComposantBdd c = (ComposantBdd)comboBox2.getSelectedItem();
                LabelP.setText(String.valueOf(c.getPrix()));
                LabelQ.setText(String.valueOf(c.getNbExemplaire()));
                spinner1.setValue(0);
            }
        });
        acheterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComposantBdd compo = (ComposantBdd)comboBox2.getSelectedItem();
                if((int)spinner1.getValue()>compo.getNbExemplaire()){
                    Vente.setText("Vente Impossible");
                }
                else {
                    ClientBdd client = (ClientBdd) comboBox1.getSelectedItem();
                    try {
                        stub.AchatComposant(client.getId(),(int)spinner1.getValue(),compo.getRef());
                        ComposantBdd c = (ComposantBdd)comboBox2.getSelectedItem();
                        LabelQ.setText(String.valueOf(c.getNbExemplaire()));
                        updateClient(clientSelect);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
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
			List<FamilleBdd> liste2 = (List) stub.getFamille();

			//Set les variables
			SetCompteClient(liste);
			SetFamilleComposant(liste2);

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public void SetCompteClient(List<ClientBdd> Comptes) {
		comboBox1.setModel(new DefaultComboBoxModel());
		if (!Comptes.isEmpty()) {
			for (ClientBdd c : Comptes) {
				System.out.println("nom " + c.getNom());
				comboBox1.addItem(c);
			}
		}
	}
	public void SetFamilleComposant(List<FamilleBdd> Familles) {
		comboBox3.setModel(new DefaultComboBoxModel());
		if (!Familles.isEmpty()) {
			for (FamilleBdd c : Familles) {
				System.out.println(c.getNom());
				comboBox3.addItem(c);
			}
		}
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
						System.out.println(factures);
						frame.SetFacture(factures);
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
