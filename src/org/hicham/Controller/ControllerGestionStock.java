package org.hicham.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.hicham.Model.Produit;
import org.hicham.Model.ProduitQueries;
import org.hicham.View.GestionStockView;

public class ControllerGestionStock {
	
	GestionStockView gestionStockView= new GestionStockView();
	ProduitQueries produitQueries =new ProduitQueries();
	
	int productId=0;
	
	public ControllerGestionStock(GestionStockView gestionStockView,ProduitQueries produitQueries){
		
		this.gestionStockView= gestionStockView;
		this.produitQueries=produitQueries;
		this.gestionStockView.addGestionStockActionListener(new GestionStockActionListener());
	}
	
	class GestionStockActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource()== gestionStockView.getAjoutProduit()) {
				produitQueries.addProduct(gestionStockView.getTextProduitAjout().getText()
						, Double.parseDouble(gestionStockView.getTextPrixAjout().getText())
						,0);
				setEmptyAjoutProduitComponents();
					refreshComboBox();
				
			}
			if (e.getSource()== gestionStockView.getModifieItem()) {
				
			}
            if (e.getSource()== gestionStockView.getSupItem()) {
				
			}
            if (e.getSource()== gestionStockView.getAjoutQte()) {
				//update query for product row
            	int qteOfSelectedProduct= Integer.parseInt(gestionStockView.getQteAjout().getText());
                changeQte(qteOfSelectedProduct);
                refreshComboBox();
                
			}
            if (e.getSource()== gestionStockView.getSousQte()) {
            	//check if global qte is bigger than quatity subtracted
            	int qteOfSelectedProduct= Integer.parseInt(gestionStockView.getQteAjout().getText());
            	//
            	Produit produit=produitQueries.getProduct(productId);
            	int qteGlobale= produit.getQte();
            	if (qteOfSelectedProduct>qteGlobale) {
            		//error message
            		gestionStockView.getQteErrorLab().setVisible(true);
            	}
            	else{
            		changeQte(-qteOfSelectedProduct);
            		refreshComboBox();
            		gestionStockView.getQteErrorLab().setVisible(false);

            	}
            }
            if (e.getSource()== gestionStockView.getProduitCombo()) {
            	int selectedItem= selectedComboItem(e);
            	showInfoProduct(selectedItem);
			}
            if (e.getSource()== gestionStockView.getChoixBtn()) {
            	        gestionStockView.getPopmenu().show(gestionStockView.getChoixBtn(), 
            			gestionStockView.getChoixBtn().getBounds().x-312,
            			gestionStockView.getChoixBtn().getBounds().y-65 + gestionStockView.getChoixBtn().getBounds().height);
	
			}
			
		}
		public void setEmptyAjoutProduitComponents(){
			gestionStockView.getTextPrixAjout().setText("");
			gestionStockView.getTextProduitAjout().setText("");

		}
		public void setEmptyAjoutQteComponenets(){
			gestionStockView.getQteAjout().setText("");
			gestionStockView.getProduitCombo().setSelectedIndex(0);
		}
		public void refreshComboBox(){
			try{
			DefaultComboBoxModel dftb=produitQueries.getComboModel();
			gestionStockView.getProduitCombo().setModel(dftb);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		public int selectedComboItem(ActionEvent e){
			//get action from product combobox
			JComboBox comboBox = (JComboBox) e.getSource();
			int selected = comboBox.getSelectedIndex();
			productId=selected;
			return selected;
		}
		public void showInfoProduct(int selectedItem){
			//get product
			Produit produit=produitQueries.getProduct(selectedItem);
			String ProductName= produit.getProduitNom();
			Double prixProduct=produit.getPrix();
			Integer qte= produit.getQte();
			//set labels for selected product
			gestionStockView.getProduitNomInfo().setText(ProductName);
			gestionStockView.getProduitPrixInfo().setText(prixProduct.toString());
			gestionStockView.getQteInfo().setText(qte.toString());
		    //calculate prix total:
			Double prixTotal= prixProduct*qte;
			gestionStockView.getPrixTotal().setText(prixTotal.toString());
		}
		public void changeQte(int qte){
			int selectedItem= productId;
        	produitQueries.addQteToProduct(selectedItem, qte);
        	setEmptyAjoutQteComponenets();
		}
		
		
	}
	
	
	
	//This is for getting buttons from menu
	//addingquantity.getPopmenu().show(addingquantity.getChoixBtn(), addingquantity.getChoixBtn().getBounds().x-312, addingquantity.getChoixBtn().getBounds().y-65 + addingquantity.getChoixBtn().getBounds().height);

}
