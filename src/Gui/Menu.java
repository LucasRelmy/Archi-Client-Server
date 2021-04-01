package Gui;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.*;

public class Menu extends JFrame {
    private JComboBox comboBox1;
    private JTextField textField1;
    private JList list1;
    private JSpinner spinner1;
    private JButton acheterButton;
    private JButton afficherLesFacturesButton;
    
    
    
    public Menu() {
    	
	}



	public void start() {
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
