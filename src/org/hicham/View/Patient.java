package org.hicham.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


public class Patient extends JPanel{
	
	

	JTabbedPane tabbedPane= new JTabbedPane();
	
	InfoPatient infoPatient= new InfoPatient();
	ActPatient actPatient= new ActPatient();
	OdfPatient odfPatient= new OdfPatient();
    Ordonance ordonance= new Ordonance();

	public Patient (InfoPatient infoPatient,ActPatient actPatient, OdfPatient odfPatient, Ordonance ordonance){
		
		this.infoPatient= infoPatient;
		this.actPatient= actPatient;
		this.odfPatient= odfPatient;
		this.ordonance= ordonance;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		
		
		
		this.add(tabbedPane);
		
		
		tabbedPane.addTab("Info       ", this.infoPatient);
		tabbedPane.addTab("Act      ", this.actPatient);
		tabbedPane.addTab("ODF       ", this.odfPatient);
		tabbedPane.addTab("Ordonance       ", this.ordonance);



		
		
		/*btn.setBounds(600,300 , 100,20 );
		b.setBounds(600, 600, 100, 100);
		tabbedPane.setBounds(0, 20, 700, 200);*/
		this.setVisible(true);

	}
	public void addPatientActionListener(ActionListener listener){
	}
	

}
