package Gui;

import Methodes.RemoteInter;
import ObjetsBdd.ClientBdd;

import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.swing.*;

public class Menu extends JFrame {
    private JComboBox comboBox1;
    private JTextField textField1;
    private JList list1;
    private JSpinner spinner1;
    private JButton acheterButton;
    private JButton afficherLesFacturesButton;
	private JPanel MainPanel;

	public Menu() {
    	
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
		comboBox1 = new JComboBox<ClientBdd>();
		if (!Comptes.isEmpty()) {
			for (ClientBdd c : Comptes) {
				System.out.println(c.getNom());
				comboBox1.addItem(c);
			}
		}
		comboBox1.setSelectedIndex(0);
	}

	public void start() {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame("Menu");
					frame.setContentPane(new Menu().MainPanel);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
					SetVariables();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
